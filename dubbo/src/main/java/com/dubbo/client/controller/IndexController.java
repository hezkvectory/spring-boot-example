package com.dubbo.client.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Reference(version = "1.0.0")
    private DemoService demoService;

    @RequestMapping("/")
    public String index() {
        return demoService.sayHello("hezk");
    }
}
