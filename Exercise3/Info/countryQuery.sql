query {
    covidStats(country: "Luxembourg") {
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