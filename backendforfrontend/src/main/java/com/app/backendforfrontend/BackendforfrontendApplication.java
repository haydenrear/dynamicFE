package com.app.backendforfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class BackendforfrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendforfrontendApplication.class, args);
    }

}
