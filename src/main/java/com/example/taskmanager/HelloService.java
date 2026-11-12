
package com.example.taskmanager;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getMessage() {
        return "Hello! Welcome to Spring Boot.";
    }
}
