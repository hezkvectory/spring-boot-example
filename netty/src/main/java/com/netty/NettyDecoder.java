package com.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author sunnights
 */
public class NettyDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyDecoder.class);
    private Codec codec;
    private int maxContentLength = 0;

    public NettyDecoder(Codec codec, int maxContentLength) {
        this.codec = codec;
        this.maxContentLength = maxContentLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() <= Constants.NETTY_HEADER) {
            return;
        }

        in.markReaderIndex();
        short type = in.readShort();
        if (type != Constants.NETTY_MAGIC_TYPE) {
            in.resetReaderIndex();
            throw new Exception("NettyDecoder transport header not support, type: " + type);
        }
        in.skipBytes(1);
        decodeV2(ctx, in, out);
    }

    private void decodeV2(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.resetReaderIndex();
        if (in.readableBytes() < 21) {
            return;
        }
        in.skipBytes(2);
        boolean isRequest = isV2Request(in.readByte());
        in.skipBytes(2);
        long requestId = in.readLong();
        int size = 13;
        int metaSize = in.readInt();
        size += 4;
        if (metaSize > 0) {
            size += metaSize;
            if (in.readableBytes() < metaSize) {
                in.resetReaderIndex();
                return;
            }
            in.skipBytes(metaSize);
        }
        if (in.readableBytes() < 4) {
            in.resetReaderIndex();
            return;
        }
        int bodySize = in.readInt();
        checkMaxContext(bodySize, ctx, isRequest, requestId);
        size += 4;
        if (bodySize > 0) {
            size += bodySize;
            if (in.readableBytes() < bodySize) {
                in.resetReaderIndex();
                return;
            }
        }
        byte[] data = new byte[size];
        in.resetReaderIndex();
        in.readBytes(data);
        decode(data, out, isRequest, requestId);
    }

    private boolean isV2Request(byte b) {
        return (b & 0x01) == 0x00;
    }

    private void checkMaxContext(int dataLength, ChannelHandlerContext ctx, boolean isRequest, long requestId) throws Exception {
        if (maxContentLength > 0 && dataLength > maxContentLength) {
            LOGGER.warn("NettyDecoder transport data content length over of limit, size: {}  > {}. remote={} local={}",
                    dataLength, maxContentLength, ctx.channel().remoteAddress(), ctx.channel().localAddress());
            Exception e = new RuntimeException("NettyDecoder transport data content length over of limit, size: " + dataLength + " > " + maxContentLength);
            if (isRequest) {
                Response response = buildExceptionResponse(requestId, e);
                byte[] msg = CodecUtil.encodeObjectToBytes(codec, response);
                ctx.channel().writeAndFlush(msg);
                throw e;
            } else {
                throw e;
            }
        }
    }

    private void decode(byte[] data, List<Object> out, boolean isRequest, long requestId) {
        out.add(new NettyMessage(isRequest, requestId, data));
    }

    private Response buildExceptionResponse(long requestId, Exception e) {
        DefaultResponse response = new DefaultResponse();
//        response.setRequestId(requestId);
//        response.setException(e);
        return response;
    }

}
