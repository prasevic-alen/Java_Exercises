package com.uni.lu.exercise3_json.controller;

import com.uni.lu.exercise3_json.service.CovidDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefetchController {

    private final CovidDataService covidDataService;

    public RefetchController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @GetMapping("/api/refetch")
    public String refetchData() {
        covidDataService.refetchData();
        // return "Data refetched successfully!";
        return "refetchSuccess";
    }
}

