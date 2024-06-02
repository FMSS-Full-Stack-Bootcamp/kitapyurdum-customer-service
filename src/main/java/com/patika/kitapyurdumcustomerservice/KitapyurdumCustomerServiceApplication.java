package com.patika.kitapyurdumcustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KitapyurdumCustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitapyurdumCustomerServiceApplication.class, args);
    }

}
