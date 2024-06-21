package com.uni.lu.exercise3_json.controller;

import com.uni.lu.exercise3_json.service.CovidDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomePage {

    private final CovidDataService covidDataService;

    public HomePage(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView refetchData() {
        covidDataService.refetchData();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        return modelAndView;
    }
}

