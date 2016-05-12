package com.codeaim.urlcheck;

import com.codeaim.urlcheck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application
{
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
