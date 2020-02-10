package com.netty;

import com.netty.utils.RequestIdGenerator;

/**
 * @author maijunsheng
 * @version 创建时间：2013-6-14
 */
public class DefaultRpcHeartbeatFactory implements HeartbeatFactory {

    public static Request getDefaultHeartbeatRequest(long requestId) {
        HeartbeatRequest request = new HeartbeatRequest();

        request.setRequestId(requestId);
        request.setInterfaceName(Constants.HEARTBEAT_INTERFACE_NAME);
        request.setMethodName(Constants.HEARTBEAT_METHOD_NAME);
        request.setParamtersDesc(Constants.HHEARTBEAT_PARAM);

        return request;
    }

    public static boolean isHeartbeatRequest(Object message) {
        if (!(message instanceof Request)) {
            return false;
        }
        if (message instanceof HeartbeatRequest) {
            return true;
        }

        Request request = (Request) message;

        return Constants.HEARTBEAT_INTERFACE_NAME.equals(request.getInterfaceName())
                && Constants.HEARTBEAT_METHOD_NAME.equals(request.getMethodName())
                && Constants.HHEARTBEAT_PARAM.endsWith(request.getParamtersDesc());
    }

    public static Response getDefaultHeartbeatResponse(long requestId) {
        HeartbeatResponse response = new HeartbeatResponse();
        response.setRequestId(requestId);
        response.setValue("heartbeat");
        return response;
    }

    public static boolean isHeartbeatResponse(Object message) {
        if (message instanceof HeartbeatResponse) {
            return true;
        }
        return false;
    }

    @Override
    public Request createRequest() {
        return getDefaultHeartbeatRequest(RequestIdGenerator.getRequestId());
    }

    @Override
    public MessageHandler wrapMessageHandler(MessageHandler handler) {
        return new HeartMessageHandleWrapper(handler);
    }

    public static class HeartbeatResponse extends DefaultResponse {
    }

    public static class HeartbeatRequest extends DefaultRequest {
    }

    private class HeartMessageHandleWrapper implements MessageHandler {
        private MessageHandler messageHandler;

        public HeartMessageHandleWrapper(MessageHandler messageHandler) {
            this.messageHandler = messageHandler;
        }

        @Override
        public Object handle(Object message) {
            if (isHeartbeatRequest(message)) {
                return getDefaultHeartbeatResponse(((Request) message).getRequestId());
            }
            return messageHandler.handle(message);
        }


    }
}
