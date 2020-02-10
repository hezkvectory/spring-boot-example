package com.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class NettyChannelHandler extends ChannelDuplexHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyChannelHandler.class);
    private ThreadPoolExecutor threadPoolExecutor;
    private MessageHandler messageHandler;
    private Codec codec;

    public NettyChannelHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        codec = new MotanV2Codec();
    }

    public NettyChannelHandler(MessageHandler messageHandler,
                               ThreadPoolExecutor threadPoolExecutor) {
        this.messageHandler = messageHandler;
        this.threadPoolExecutor = threadPoolExecutor;
        codec = new MotanV2Codec();
    }


    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        if (msg instanceof NettyMessage) {
            if (threadPoolExecutor != null) {
                try {
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            processMessage(ctx, ((NettyMessage) msg));
                        }
                    });
                } catch (RejectedExecutionException rejectException) {
                    if (((NettyMessage) msg).isRequest()) {
                        rejectMessage(ctx, (NettyMessage) msg);
                    } else {
                        LOGGER.warn("process thread pool is full, run in io thread, active={} poolSize={} corePoolSize={} maxPoolSize={} taskCount={} requestId={}",
                                threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize(), threadPoolExecutor.getCorePoolSize(),
                                threadPoolExecutor.getMaximumPoolSize(), threadPoolExecutor.getTaskCount(), ((NettyMessage) msg).getRequestId());
                        processMessage(ctx, (NettyMessage) msg);
                    }
                }
            } else {
                processMessage(ctx, (NettyMessage) msg);
            }
        } else {
            LOGGER.error("NettyChannelHandler messageReceived type not support: class=" + msg.getClass());
            throw new RuntimeException("NettyChannelHandler messageReceived type not support: class=" + msg.getClass());
        }
    }

    private void rejectMessage(ChannelHandlerContext ctx, NettyMessage msg) {
        if (msg.isRequest()) {
            DefaultResponse response = new DefaultResponse();
//            response.setRequestId(msg.getRequestId());
//            response.setException(new MotanServiceException("process thread pool is full, reject by server: " + ctx.channel().localAddress(), MotanErrorMsgConstant.SERVICE_REJECT));
            sendResponse(ctx, response);

            LOGGER.error("process thread pool is full, reject, active={} poolSize={} corePoolSize={} maxPoolSize={} taskCount={} requestId={}",
                    threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize(), threadPoolExecutor.getCorePoolSize(),
                    threadPoolExecutor.getMaximumPoolSize(), threadPoolExecutor.getTaskCount(), msg.getRequestId());
        }
    }

    private void processMessage(ChannelHandlerContext ctx, NettyMessage msg) {
//        String remoteIp = getRemoteIp(ctx);
        Object result;
        try {
            result = codec.decode(msg.getData());
        } catch (Exception e) {
            LOGGER.error("NettyDecoder decode fail! requestid" + msg.getRequestId() + ", size:" + msg.getData().length, e);
            if (msg.isRequest()) {
                Response response = buildExceptionResponse(msg.getRequestId(), e);
                sendResponse(ctx, response);
            } else {
                Response response = buildExceptionResponse(msg.getRequestId(), e);
                processResponse(response);
            }
            return;
        }
        if (result instanceof Request) {
            processRequest(ctx, (Request) result);
        } else if (result instanceof Response) {
            processResponse(result);
        }
    }

    private Response buildExceptionResponse(long requestId, Exception e) {
        DefaultResponse response = new DefaultResponse();
//        response.setRequestId(requestId);
//        response.setException(e);
        return response;
    }

    private void processRequest(final ChannelHandlerContext ctx, Request request) {
//        request.setAttachment(URLParamType.host.getName(), NetUtils.getHostName(ctx.channel().remoteAddress()));
        final long processStartTime = System.currentTimeMillis();
        try {
//            RpcContext.init(request);
            Object result = null;
            try {
                result = messageHandler.handle(request);
            } catch (Exception e) {
//                LoggerUtil.error("NettyChannelHandler processRequest fail! request:" + MotanFrameworkUtil.toString(request), e);
//                result = MotanFrameworkUtil.buildErrorResponse(request.getRequestId(), new MotanServiceException("process request fail. errmsg:" + e.getMessage()));
            }
            DefaultResponse response;
            if (result instanceof DefaultResponse) {
                response = (DefaultResponse) result;
            } else {
                response = new DefaultResponse(result);
            }
//            response.setRequestId(request.getRequestId());
            response.setProcessTime(System.currentTimeMillis() - processStartTime);

            sendResponse(ctx, response);
        } finally {
//            RpcContext.destroy();
        }
    }

    private void sendResponse(ChannelHandlerContext ctx, Response response) {
        byte[] msg = CodecUtil.encodeObjectToBytes(codec, response);
        if (ctx.channel().isActive()) {
            ctx.channel().writeAndFlush(msg);
        }
    }

    private void processResponse(Object msg) {
        messageHandler.handle(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("NettyChannelHandler channelActive: remote={} local={}", ctx.channel().remoteAddress(), ctx.channel().localAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("NettyChannelHandler channelInactive: remote={} local={}", ctx.channel().remoteAddress(), ctx.channel().localAddress());
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("NettyChannelHandler exceptionCaught: remote={} local={} event={}", ctx.channel().remoteAddress(), ctx.channel().localAddress(), cause.getMessage(), cause);
        ctx.channel().close();
    }
}
