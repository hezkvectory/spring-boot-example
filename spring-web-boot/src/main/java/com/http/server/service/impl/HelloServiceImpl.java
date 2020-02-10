package com.http.server.service.impl;

import com.http.server.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello " + name;
    }
}
