package br.com.lojadomecanico.urltag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.lojadomecanico.urltag"})
@EntityScan(basePackages = {"br.com.lojadomecanico.urltag.entity"})
@EnableJpaRepositories(basePackages = {"br.com.lojadomecanico.urltag.repository"})
@Configuration
public class UrlTagApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlTagApplication.class, args);
    }
}
