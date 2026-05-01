package org.example.demo.repository;

import org.example.demo.entity.FullStats;
import org.example.demo.entity.Stats;

import java.util.List;
import java.util.UUID;

public interface StatsRepository {

    FullStats get3hStats(UUID cityId);

    List<Stats> getLastMonthTop10NOUsage();
}
