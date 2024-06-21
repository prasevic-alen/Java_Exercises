package com.uni.lu.exercise3_json.controller;

import com.uni.lu.exercise3_json.service.CovidDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RefetchController {

    private final CovidDataService covidDataService;

    public RefetchController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @RequestMapping("/api/refetch")
    @ResponseBody
    public ModelAndView refetchData() {
        covidDataService.refetchData();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("refetch.html");
        // return "Data refetched successfully!";
        return modelAndView;
    }
}

