package com.uni.lu.exercise3_json;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.uni.lu.exercise3_json.model.CovidStats;
import com.uni.lu.exercise3_json.repository.CovidStatsRepository;

@SpringBootApplication
public class CovidGraphqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(CovidGraphqlApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CovidStatsRepository repository) {
        return (args) -> {
            List<CovidStats> statsList = repository.findAll();
            statsList.forEach(stat -> System.out.println(stat.getCountry() + ": " + stat.getCases() + " cases"));
        };
    }
}