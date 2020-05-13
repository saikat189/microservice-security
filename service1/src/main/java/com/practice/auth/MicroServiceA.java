package com.practice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/greetings")
public class MicroServiceA {
    @GetMapping
    public String fetchGreeting() {
        return "Hello from MicroServiceA";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceA.class, args);
    }
}