package com.zerock.jpaboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaboardApplication.class, args);
    }

}
