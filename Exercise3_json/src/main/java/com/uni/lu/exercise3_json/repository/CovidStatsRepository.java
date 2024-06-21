package com.uni.lu.exercise3_json.repository;

import com.uni.lu.exercise3_json.model.CovidStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CovidStatsRepository extends JpaRepository<CovidStats, String> {
}