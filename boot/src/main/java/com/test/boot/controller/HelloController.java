package com.test.boot.controller;

import com.test.boot.vo.IndexForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 17:00.
 */
@RestController
public class HelloController {

    @PostMapping("/save")
    public String index(@RequestParam(name = "param") List<IndexForm> name) {
        System.out.println(name);
        return "index";
    }
}
