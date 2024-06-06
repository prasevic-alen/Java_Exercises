package com.uni.lu.exercise3.service;

import com.uni.lu.exercise3.model.CovidStats;
import com.uni.lu.exercise3.repository.CovidStatsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

@Service
public class CovidDataService {

    private static final Logger logger = LoggerFactory.getLogger(CovidDataService.class);

    private final RestTemplate restTemplate;
    private final CovidStatsRepository covidStatsRepository;

    public CovidDataService(CovidStatsRepository covidStatsRepository) {
        this.restTemplate = new RestTemplate();
        this.covidStatsRepository = covidStatsRepository;
    }

    @PostConstruct
    public void initData() {
        List<CovidStats> statsList = fetchCovidStatsData();
        logger.info("Saving data to repository: {}", statsList);
        covidStatsRepository.saveAll(statsList);
    }

    public List<CovidStats> fetchCovidStatsData() {
        String url = "https://disease.sh/v3/covid-19/countries";
        CovidStats[] statsArray = restTemplate.getForObject(url, CovidStats[].class);
        logger.info("Fetched data: {}", Arrays.toString(statsArray));
        return Arrays.asList(statsArray);
    }

    public CovidStats getCovidStats(String country) {
        logger.info("Fetching data for country: {}", country);
        CovidStats stats = covidStatsRepository.findById(country).orElse(null);
        logger.info("Fetched stats: {}", stats);
        return stats;
    }

    public List<CovidStats> getCovidStatsForCountries(List<String> countries) {
        logger.info("Fetching data for countries: {}", countries);
        List<CovidStats> statsList = covidStatsRepository.findAllById(countries);
        logger.info("Fetched stats list: {}", statsList);
        return statsList;
    }
}