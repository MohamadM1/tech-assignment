package com.example.techassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TechAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechAssignmentApplication.class, args);
    }
}