package com.netty;

import com.netty.utils.ByteUtil;
import com.netty.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.netty.Constants.*;

public class MotanV2Codec implements Codec {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotanV2Codec.class);

    private static final byte MASK = 0x07;
    private static final int HEADER_SIZE = 13;

    @Override
    public byte[] encode(Object message) {
        try {
            if (DefaultRpcHeartbeatFactory.isHeartbeatRequest(message)) {
                return encodeHeartbeat(((Request) message).getRequestId(), true);
            }
            if (DefaultRpcHeartbeatFactory.isHeartbeatResponse(message)) {
                return encodeHeartbeat(((Response) message).getRequestId(), false);
            }
            MotanV2Header header = new MotanV2Header();
            byte[] body = null;
            Serialization serialization = new Hessian2Serialization();

            header.setSerialize(1);

            GrowableByteBuffer buf = new GrowableByteBuffer(4096);
            //meta
            int index = HEADER_SIZE;
            buf.position(index);
            buf.putInt(0);//metasize

            if (message instanceof Request) {
                Request request = (Request) message;
                putString(buf, M2_PATH);
                putString(buf, request.getInterfaceName());
                putString(buf, M2_METHOD);
                putString(buf, request.getMethodName());
                if (request.getParamtersDesc() != null) {
                    putString(buf, M2_METHOD_DESC);
                    putString(buf, request.getParamtersDesc());
                }

                putMap(buf, request.getAttachments());

                header.setRequestId(request.getRequestId());
                if (request.getArguments() != null) {
                    body = serialization.serializeMulti(request.getArguments());
                }

            } else if (message instanceof Response) {
                Response response = (Response) message;
                putString(buf, M2_PROCESS_TIME);
                putString(buf, String.valueOf(response.getProcessTime()));
                if (response.getException() != null) {
                    putString(buf, M2_ERROR);
                    putString(buf, ExceptionUtil.toMessage(response.getException()));
                    header.setStatus(MotanV2Header.MessageStatus.EXCEPTION.getStatus());
                }
                putMap(buf, response.getAttachments());

                header.setRequestId(response.getRequestId());
                header.setRequest(false);
                if (response.getException() == null) {
                    body = serialization.serialize(response.getValue());
                }
            }

            buf.position(buf.position() - 1);
            int metalength = buf.position() - index - 4;
            buf.putInt(index, metalength);

            //body
            if (body != null && body.length > 0) {
                body = ByteUtil.gzip(body);
                buf.putInt(body.length);
                buf.put(body);
            } else {
                buf.putInt(0);
            }

            //header
            int position = buf.position();
            buf.position(0);
            buf.put(header.toBytes());
            buf.position(position);
            buf.flip();
            byte[] result = new byte[buf.remaining()];
            buf.get(result);
            return result;
        } catch (Exception e) {
            String errmsg = "";
            if (message != null) {
                if (message instanceof Request) {
                    errmsg = "type:request, " + message.toString();
                } else {
                    errmsg = "type:response, " + message.toString();
                }
            }
            LOGGER.warn("motan2 encode error." + errmsg, e);
            throw new RuntimeException(e);
        }
    }


    private void putString(GrowableByteBuffer buf, String content) throws UnsupportedEncodingException {
        buf.put(content.getBytes("UTF-8"));
        buf.put("\n".getBytes("UTF-8"));
    }

    private void putMap(GrowableByteBuffer buf, Map<String, String> map) throws UnsupportedEncodingException {
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                putString(buf, entry.getKey());
                putString(buf, entry.getValue());
            }
        }
    }

    private byte[] encodeHeartbeat(long requestId, boolean isRequest) {
        MotanV2Header header = new MotanV2Header();
        header.setHeartbeat(true);
        header.setRequestId(requestId);
        if (!isRequest) {
            header.setRequest(false);
        }
        GrowableByteBuffer buf = new GrowableByteBuffer(32);
        buf.put(header.toBytes());
        buf.putInt(0);//metasize
        buf.putInt(0);//bodysize
        buf.flip();
        byte[] result = new byte[buf.remaining()];
        buf.get(result);
        return result;
    }


    /**
     * decode data
     *
     * @return
     * @throws IOException
     */
    @Override
    public Object decode(byte[] data) throws IOException {
        MotanV2Header header = MotanV2Header.buildHeader(data);
        Map<String, String> metaMap = new HashMap<String, String>();
        ByteBuffer buf = ByteBuffer.wrap(data);
        int metaSize = buf.getInt(HEADER_SIZE);
        int index = HEADER_SIZE + 4;
        if (metaSize > 0) {
            byte[] meta = new byte[metaSize];
            buf.position(index);
            buf.get(meta);
            metaMap = decodeMeta(meta);
            index += metaSize;
        }
        int bodySize = buf.getInt(index);
        index += 4;
        Object obj = null;
        if (bodySize > 0) {
            byte[] body = new byte[bodySize];
            buf.position(index);
            buf.get(body);
            if (header.isGzip()) {
                body = ByteUtil.unGzip(body);
            }
            //默认自适应序列化
            Serialization serialization = new Hessian2Serialization();
            obj = new DeserializableObject(serialization, body);
        }
        if (header.isRequest()) {
            if (header.isHeartbeat()) {
                return DefaultRpcHeartbeatFactory.getDefaultHeartbeatRequest(header.getRequestId());
            } else {
                DefaultRequest request = new DefaultRequest();
                request.setRequestId(header.getRequestId());
                request.setInterfaceName(metaMap.remove(M2_PATH));
                request.setMethodName(metaMap.remove(M2_METHOD));
                request.setParamtersDesc(metaMap.remove(M2_METHOD_DESC));
                request.setAttachments(metaMap);
                if (obj != null) {
                    request.setArguments(new Object[]{obj});
                }

                return request;
            }

        } else {
            if (header.isHeartbeat()) {
                return DefaultRpcHeartbeatFactory.getDefaultHeartbeatResponse(header.getRequestId());
            }
            DefaultResponse response = new DefaultResponse();
            return response;
        }

    }

    private Map<String, String> decodeMeta(byte[] meta) {
        Map<String, String> map = new HashMap<String, String>();
        if (meta != null && meta.length > 0) {
            String[] s = new String(meta).split("\n");
            for (int i = 0; i < s.length - 1; i++) {
                map.put(s[i++], s[i]);
            }
        }
        return map;
    }
}
