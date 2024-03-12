package com.mzwise.flyway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class FlywayApplication {

    @Autowired
    DataSource dataSource;


    public static void main(String[] args) {
        SpringApplication.run(FlywayApplication.class, args);
        System.out.println("==================================flyway成功==================================");
    }
}
