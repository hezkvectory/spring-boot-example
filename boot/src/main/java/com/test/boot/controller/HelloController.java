package com.test.boot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 17:00.
 */
@RestController
public class HelloController {

    @PostMapping("/save")
    public String index(@RequestParam(name = "name") String name,
                        @RequestParam(name = "age") Integer age) {
        System.out.println(name + ":" + age);
        return "index";
    }
}
