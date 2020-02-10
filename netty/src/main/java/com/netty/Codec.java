package com.netty;

import java.io.IOException;

public interface Codec {

    byte[] encode(Object message) throws IOException;

    /**
     * @param buffer
     * @return
     * @throws IOException
     */
    Object decode(byte[] buffer) throws IOException;

}