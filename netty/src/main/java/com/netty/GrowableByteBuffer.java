package com.netty;

import java.nio.ByteBuffer;

/**
 * Created by zhanglei28 on 2017/6/27.
 */
public class GrowableByteBuffer {
    private ByteBuffer buf;

    public GrowableByteBuffer(int initSize) {
        this.buf = ByteBuffer.allocate(initSize);
    }

    public void put(byte b) {
        if (buf.remaining() < 1) {
            buf = grow(buf.capacity() * 2);
        }
        buf.put(b);
    }


    public void put(int index, byte b) {
        buf.put(index, b);
    }

    public void put(byte[] b) {
        if (buf.remaining() < b.length) {
            int size = buf.capacity() * 2;
            while (size < (b.length + buf.position())) {
                size = size * 2;
            }
            buf = grow(size);
        }
        buf.put(b);
    }


    public void putInt(int value) {
        if (buf.remaining() < 4) {
            buf = grow(buf.capacity() * 2);
        }
        buf.putInt(value);
    }


    public void putInt(int index, int value) {
        buf.putInt(index, value);
    }

    public void putLong(long value) {
        if (buf.remaining() < 8) {
            buf = grow(buf.capacity() * 2);
        }
        buf.putLong(value);
    }

    public void putLong(int index, long value) {
        buf.putLong(index, value);
    }

    public void flip() {
        buf.flip();
    }

    public int position() {
        return buf.position();
    }

    public void position(int newPosition) {
        buf.position(newPosition);
    }

    public int remaining() {
        return buf.remaining();
    }

    public void get(byte[] dst) {
        buf.get(dst);
    }

    private ByteBuffer grow(int size) {
        ByteBuffer newbuf = ByteBuffer.allocate(size);
        newbuf.put(buf.array());
        newbuf.position(buf.position());
        return newbuf;
    }
}
