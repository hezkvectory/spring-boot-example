package com.netty;

import com.netty.utils.RequestIdGenerator;

import java.io.IOException;

/**
 * @author sunnights
 */
public class CodecUtil {

    public static byte[] encodeObjectToBytes(Codec codec, Object msg) {
        try {
            return encodeV2(codec, msg);
        } catch (IOException e) {
            throw new RuntimeException("encode error: isResponse=" + (msg instanceof Response), e);
        }
    }

    private static byte[] encodeV2(Codec codec, Object msg) throws IOException {
        return encodeMessage(codec, msg);
    }

    private static byte[] encodeMessage(Codec codec, Object msg) throws IOException {
        byte[] data;
        if (msg instanceof Response) {
            try {
                data = codec.encode(msg);
            } catch (Exception e) {
                long requestId = RequestIdGenerator.getRequestId();
                Response response = buildExceptionResponse(requestId, e);
                data = codec.encode(response);
            }
        } else {
            data = codec.encode(msg);
        }
        return data;
    }


    private static Response buildExceptionResponse(long requestId, Exception e) {
        DefaultResponse response = new DefaultResponse();
        response.setRequestId(requestId);
        response.setException(e);
        return response;
    }

}
