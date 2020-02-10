package com.http.client;

import com.http.server.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "9998");
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean() {
        HttpInvokerProxyFactoryBean bean = new HttpInvokerProxyFactoryBean();
        bean.setServiceInterface(HelloService.class);
        bean.setServiceUrl("http://localhost:9999/remoteservice");
        return bean;
    }
}
