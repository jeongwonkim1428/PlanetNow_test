package com.application.planetnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanetNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanetNowApplication.class, args);
    }

}
