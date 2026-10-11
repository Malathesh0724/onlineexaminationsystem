package com.Exam.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    private static final Logger logger = LoggerFactory.getLogger(PasswordConfig.class);


    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("PasswordConfig.passwordEncoder started");

        return new BCryptPasswordEncoder();
    }
}