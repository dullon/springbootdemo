package com.dullon.democloudclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class DemoCloudclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCloudclientApplication.class, args);
    }

}
