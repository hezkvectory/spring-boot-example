package com.dubbo.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo(scanBasePackages = {"com.dubbo"})
public class DubboServer {
    public static void main(String[] args) {
        SpringApplication.run(DubboServer.class, args);
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setName("hezk-provider");
        return config;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setId("registry");
        config.setAddress("zookeeper://192.168.2.122:2181");
        return config;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName("http");
        config.setPort(8080);
        config.setServer("tomcat");
        return config;
    }

  /*  @Bean("rest")
    public ProtocolConfig protocolConfig1() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName("rest");
        config.setPort(8081);
        config.setServer("tomcat");
        return config;
    }*/
}
