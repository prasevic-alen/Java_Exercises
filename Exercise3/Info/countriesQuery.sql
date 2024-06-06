query {
    covidStatsForCountries(countries: ["serbia", "usa", "canada"]) {
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