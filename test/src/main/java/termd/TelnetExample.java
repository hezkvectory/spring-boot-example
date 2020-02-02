/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package termd;

import io.termd.core.readline.Keymap;
import io.termd.core.readline.Readline;
import io.termd.core.telnet.Option;
import io.termd.core.telnet.TelnetConnection;
import io.termd.core.telnet.TelnetHandler;
import io.termd.core.telnet.netty.NettyTelnetBootstrap;
import io.termd.core.tty.TtyConnection;
import io.termd.core.util.Helper;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * This examples shows a simple telnet server that negociates a couple of options.
 */
public class TelnetExample {

    public synchronized static void main(String[] args) throws Exception {
        NettyTelnetBootstrap bootstrap = new NettyTelnetBootstrap().setHost("localhost").setPort(4000);
        bootstrap.start(() -> new TelnetHandler() {
            @Override
            protected void onOpen(TelnetConnection conn) {
                System.out.println("Client connected");

                // Negociate window size and terminal type
                conn.writeDoOption(Option.TERMINAL_TYPE);
                conn.writeDoOption(Option.NAWS);

                checkAccept(conn);
            }

            public void checkAccept(TelnetConnection conn) {
                InputStream inputrc = Keymap.class.getResourceAsStream("inputrc");
                Keymap keymap = new Keymap(inputrc);
                Readline readline = new Readline(keymap);
                for (io.termd.core.readline.Function function : Helper.loadServices(Thread.currentThread().getContextClassLoader(), io.termd.core.readline.Function.class)) {
                    readline.addFunction(function);
                }
                conn.write("Welcome to Term.d shell example\n\n".getBytes());
                read(conn, readline);
            }

            public void read(final TelnetConnection conn, final Readline readline) {

                // Just call readline and get a callback when line is read
                readline.readline((TtyConnection) conn, "%$$$ ", line -> {

                    // Ctrl-D
                    if (line == null) {
                        conn.write("logout\n".getBytes());
                        conn.close();
                        return;
                    }

                    conn.write((line + ":" + System.currentTimeMillis()).getBytes());

                    read(conn, readline);
                });
            }

            @Override
            protected void onNAWS(boolean naws) {
                if (naws) {
                    System.out.println("Client will send window size changes");
                } else {
                    System.out.println("Client won't send window size changes");
                }
            }

            @Override
            protected void onData(byte[] data) {
                System.out.println("Client sent " + new String(data));
            }

            @Override
            protected void onSize(int width, int height) {
                System.out.println("Window resized " + width + height);
            }

            @Override
            protected void onTerminalType(String terminalType) {
                System.out.println("Client declared its terminal as " + terminalType);
            }

            @Override
            protected void onClose() {
                System.out.println("Disconnected");
            }
        }).get(10, TimeUnit.SECONDS);
        System.out.println("Telnet server started on localhost:4000");
        TelnetExample.class.wait();
    }
}
