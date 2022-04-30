package com.ripple.provision.vm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.ripple.provision.vm.repository")
public class Provision {
    public static void main(String[] args) {
        SpringApplication.run(Provision.class, args);
    }
}
