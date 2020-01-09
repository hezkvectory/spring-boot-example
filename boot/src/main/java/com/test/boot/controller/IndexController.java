package com.test.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 17:00.
 */
//@RestController
public class IndexController {

    @Autowired
    Environment environment;

//    @Autowired
//    Bean1 bean1;


    @GetMapping("/index")
    public String index() {
//        System.out.println(bean1.getName());
        return environment.getProperty("hezk.k");
    }
}
