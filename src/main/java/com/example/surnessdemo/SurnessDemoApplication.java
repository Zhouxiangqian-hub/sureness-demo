package com.example.surnessdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.example.surnessdemo.dao")
public class SurnessDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurnessDemoApplication.class, args);
    }

}
