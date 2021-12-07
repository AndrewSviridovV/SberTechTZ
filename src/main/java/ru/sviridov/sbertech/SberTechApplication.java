package ru.sviridov.sbertech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.sviridov.sbertech.model.Product;

import java.util.HashMap;

@SpringBootApplication
public class SberTechApplication {

    public static HashMap cash=new HashMap<Integer, Product>();
    public static void main(String[] args) {
        SpringApplication.run(SberTechApplication.class, args);
    }

}
