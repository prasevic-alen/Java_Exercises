query {
    covidStatsForCountries(countries: ["Serbia", "Luxembourg", "Germany"]) {
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