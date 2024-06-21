package com.uni.lu.exercise3_json.service;

import com.uni.lu.exercise3_json.model.CovidStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CovidDataService {

    private static final Logger logger = LoggerFactory.getLogger(CovidDataService.class);

    private final RestTemplate restTemplate;
    private final JsonParser jsonParser;
    private List<CovidStats> cachedStats;

    public CovidDataService() {
        this.restTemplate = new RestTemplate();
        this.jsonParser = JsonParserFactory.getJsonParser();
        this.cachedStats = new ArrayList<>();
        refetchData(); // Initial fetch
    }

    @SuppressWarnings("unchecked")
    public void refetchData() {
        String url = "https://disease.sh/v3/covid-19/countries";
        List<CovidStats> statsList = new ArrayList<>();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        List<Object> list = jsonParser.parseList(jsonResponse);

        for (Object obj : list) {
            if (obj instanceof Map) {
                Map<String, Object> jsonObject = (Map<String, Object>) obj;
                CovidStats stats = parseCovidStats(jsonObject);
                statsList.add(stats);
            }
        }
        cachedStats = statsList;
        logger.info("Data refetched successfully");
    }

    private CovidStats parseCovidStats(Map<String, Object> jsonObject) {
        CovidStats stats = new CovidStats();
        stats.setCountry((String) jsonObject.get("country"));
        stats.setCases(((Number) jsonObject.get("cases")).intValue());
        stats.setTodayCases(((Number) jsonObject.get("todayCases")).intValue());
        stats.setDeaths(((Number) jsonObject.get("deaths")).intValue());
        stats.setTodayDeaths(((Number) jsonObject.get("todayDeaths")).intValue());
        stats.setRecovered(((Number) jsonObject.get("recovered")).intValue());
        stats.setActive(((Number) jsonObject.get("active")).intValue());
        stats.setCritical(((Number) jsonObject.get("critical")).intValue());
        stats.setCasesPerOneMillion(((Number) jsonObject.get("casesPerOneMillion")).intValue());
        stats.setDeathsPerOneMillion(((Number) jsonObject.get("deathsPerOneMillion")).intValue());
        stats.setTests(((Number) jsonObject.get("tests")).intValue());
        stats.setTestsPerOneMillion(((Number) jsonObject.get("testsPerOneMillion")).intValue());
        stats.setPopulation(((Number) jsonObject.get("population")).intValue());
        return stats;
    }

    public List<CovidStats> getCachedStats() {
        return cachedStats;
    }

    public CovidStats getCovidStats(String country) {
        return cachedStats.stream()
                .filter(stats -> stats.getCountry().equalsIgnoreCase(country))
                .findFirst()
                .orElse(null);
    }

    public List<CovidStats> getCovidStatsForCountries(List<String> countries) {
        return cachedStats.stream()
                .filter(stats -> countries.contains(stats.getCountry()))
                .toList();
    }
}
