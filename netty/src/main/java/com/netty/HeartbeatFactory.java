package com.netty;

public interface HeartbeatFactory {

    /**
     * 创建心跳包
     *
     * @return
     */
    Request createRequest();

    MessageHandler wrapMessageHandler(MessageHandler handler);
}
