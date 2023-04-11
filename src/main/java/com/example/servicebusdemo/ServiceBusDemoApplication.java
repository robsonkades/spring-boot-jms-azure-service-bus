package com.example.servicebusdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.util.UUID;

@SpringBootApplication
@EnableJms
public class ServiceBusDemoApplication {

    public static void main(String[] args) {
        System.setProperty("APP_UUID", UUID.randomUUID().toString());
        SpringApplication.run(ServiceBusDemoApplication.class, args);
    }

}
