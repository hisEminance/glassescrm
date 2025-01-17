package com.example.glasses.repositories;

import com.example.glasses.entities.Glass;
import com.example.glasses.entities.RevenueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRecordRepository extends JpaRepository<RevenueRecord, Long> {
    List<RevenueRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
    RevenueRecord findByDate(LocalDate date);
    RevenueRecord findByGlassAndDate(Glass glass, LocalDate date);
    @Query("SELECT SUM(r.amount) FROM RevenueRecord r WHERE r.date BETWEEN :startDate AND :endDate")
    BigDecimal sumRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}