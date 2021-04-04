package com.sparta.backend;

import com.sparta.backend.selenium.Selenium;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class    BackendApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args)throws IOException {
        Selenium selenium = new Selenium();
        selenium.setURL("https://www.stussy.co.kr/collections/tees");
        selenium.crawling();
        SpringApplication.run(BackendApplication.class, args);
    }

}
