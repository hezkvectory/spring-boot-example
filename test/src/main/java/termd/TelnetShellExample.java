package termd;

import io.termd.core.telnet.netty.NettyTelnetTtyBootstrap;

import java.util.concurrent.TimeUnit;

public class TelnetShellExample {

    public synchronized static void main(String[] args) throws Exception {
        NettyTelnetTtyBootstrap bootstrap = new NettyTelnetTtyBootstrap().
                setHost("localhost").
                setPort(4000);
        bootstrap.start(new Shell()).get(10, TimeUnit.SECONDS);
        System.out.println("Telnet server started on localhost:4000");
        TelnetShellExample.class.wait();
    }
}
