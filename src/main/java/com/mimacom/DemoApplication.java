package com.mimacom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static final String INDEX_NAME = "demo";
    public static final String MAPPING_TYPE = "doc";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }



}
