package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

public class Main {
    public static void main(String[] args) {

//        PooledByteBufAllocator.DEFAULT.directBuffer(10, 100);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        buffer.writeInt(1);
        System.out.println(buffer.readableBytes());
        System.out.println(buffer.readInt());

//        GrowableByteBuffer byteBuffer = new GrowableByteBuffer(8);
//        byteBuffer.put((byte) 1);
//        System.out.println(byteBuffer.position());
        ByteBuf buf = Unpooled.directBuffer();// 非池化堆内存

        Unpooled.buffer();

        buf.writeBytes("hello".getBytes());


        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());

        buf.readableBytes();

        System.out.println(new String(new byte[]{buf.readByte()}));

        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());

        buf.resetReaderIndex();

        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());

    }
}
