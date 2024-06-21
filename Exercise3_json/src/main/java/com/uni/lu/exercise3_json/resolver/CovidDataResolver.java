package com.uni.lu.exercise3_json.resolver;

import com.uni.lu.exercise3_json.model.CovidStats;
import com.uni.lu.exercise3_json.service.CovidDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CovidDataResolver {

    private static final Logger logger = LoggerFactory.getLogger(CovidDataResolver.class);

    private final CovidDataService covidDataService;

    public CovidDataResolver(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @QueryMapping
    public CovidStats covidStats(@Argument String country) {
        logger.info("Querying for country: {}", country);
        CovidStats stats = covidDataService.getCovidStats(country);
        logger.info("Query result: {}", stats);
        return stats;
    }

    @QueryMapping
    public List<CovidStats> covidStatsForCountries(@Argument List<String> countries) {
        logger.info("Querying for countries: {}", countries);
        List<CovidStats> statsList = covidDataService.getCovidStatsForCountries(countries);
        logger.info("Query result: {}", statsList);
        return statsList;
    }
}