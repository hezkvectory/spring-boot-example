package com.netty.client;

import com.netty.*;
import com.netty.NettyDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 最大响应包限制
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new NettyDecoder(new MotanV2Codec(), 10 * 1024 * 1024));
                        pipeline.addLast("encoder", new NettyEncoder());
                        pipeline.addLast("handler", new NettyChannelHandler(new MessageHandler() {
                            @Override
                            public Object handle(Object message) {
                                Response response = (Response) message;
                                System.out.println("----");
                                return null;
                            }
                        }));
                    }
                });
        Channel channel = bootstrap.connect("localhost", 4321).sync().channel();
        byte[] msg = CodecUtil.encodeObjectToBytes(new MotanV2Codec(), new DefaultRpcHeartbeatFactory.HeartbeatRequest());
        ChannelFuture writeFuture = channel.writeAndFlush(msg);
        boolean result = writeFuture.awaitUninterruptibly(1000, TimeUnit.MILLISECONDS);
        System.out.println(result + ":" + writeFuture.isSuccess());
    }
}
