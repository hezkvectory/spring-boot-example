package com.netty;

/**
 * @author sunnights
 */
public class NettyMessage {
    private boolean isRequest;
    private long requestId;
    private byte[] data;

    public NettyMessage(boolean isRequest, long requestId, byte[] data) {
        this.isRequest = isRequest;
        this.requestId = requestId;
        this.data = data;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
