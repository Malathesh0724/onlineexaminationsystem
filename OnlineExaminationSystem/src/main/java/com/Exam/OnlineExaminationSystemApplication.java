package com.Exam;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableScheduling
public class OnlineExaminationSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(OnlineExaminationSystemApplication.class);


	public static void main(String[] args) {


	    logger.info("OnlineExaminationSystemApplication.main started");
		SpringApplication.run(OnlineExaminationSystemApplication.class, args);
	}

}
