-- Create table
CREATE TABLE COVID_STATS (
    country VARCHAR(255) PRIMARY KEY,
    cases INT,
    todayCases INT,
    deaths INT,
    todayDeaths INT,
    recovered INT,
    active INT,
    critical INT,
    casesPerOneMillion INT,
    deathsPerOneMillion INT,
    tests INT,
    testsPerOneMillion INT,
    population INT
);
