package com.netty;

import java.io.Serializable;
import java.util.Map;


/**
 * Response received via rpc.
 *
 * @author fishermen
 * @version V1.0 created at: 2013-5-16
 */
public class DefaultResponse implements Response, Serializable {
    private static final long serialVersionUID = 4281186647291615871L;

    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;

    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息

    public DefaultResponse() {
    }

    public DefaultResponse(long requestId) {
        this.requestId = requestId;
    }

    public DefaultResponse(Response response) {
        this.value = response.getValue();
        this.exception = response.getException();
        this.requestId = response.getRequestId();
        this.processTime = response.getProcessTime();
        this.timeout = response.getTimeout();
    }

    public DefaultResponse(Object value) {
        this.value = value;
    }

    public DefaultResponse(Object value, long requestId) {
        this.value = value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public long getRequestId() {
        return 0;
    }

    @Override
    public long getProcessTime() {
        return 0;
    }

    @Override
    public void setProcessTime(long time) {

    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public void setAttachment(String key, String value) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return 0;
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }
}
