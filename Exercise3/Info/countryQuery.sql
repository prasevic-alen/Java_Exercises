query {
    covidStats(country: "serbia") {
        country
        cases
        todayCases
        deaths
        todayDeaths
        recovered
        active
        critical
        casesPerOneMillion
        deathsPerOneMillion
        tests
        testsPerOneMillion
        population
    }
}