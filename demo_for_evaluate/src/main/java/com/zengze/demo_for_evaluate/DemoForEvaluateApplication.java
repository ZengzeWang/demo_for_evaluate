package com.zengze.demo_for_evaluate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemoForEvaluateApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoForEvaluateApplication.class, args);
    }

}

