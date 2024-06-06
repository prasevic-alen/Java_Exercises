package com.uni.lu.exercise3.repository;

import com.uni.lu.exercise3.model.CovidStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CovidStatsRepository extends JpaRepository<CovidStats, String> {
}