package com.dullon.democloudclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
public class DeomCloudController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hello")
    public String testHello(@RequestParam String name) {

        return "Hello," + name + ". Welcome to BeiJing! I am from port" + port;
    }

}
