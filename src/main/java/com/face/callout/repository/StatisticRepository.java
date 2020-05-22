package com.face.callout.repository;

import com.face.callout.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Statistic findByStartId(Long start);
}
