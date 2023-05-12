package com.hanghae.be_h010gram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BeH010GramApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeH010GramApplication.class, args);
    }

}
