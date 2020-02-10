package com.http.server;

import com.http.server.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "9999");
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean(name = "/remoteservice")
    public HttpInvokerServiceExporter httpInvokerServiceExporter(HelloService helloService) {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(helloService);
        exporter.setServiceInterface(HelloService.class);
        return exporter;
    }
}
