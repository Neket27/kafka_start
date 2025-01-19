package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ProducerKafka {

    public static void main(String[] args) {
        SpringApplication.run(ProducerKafka.class, args);
    }

}
