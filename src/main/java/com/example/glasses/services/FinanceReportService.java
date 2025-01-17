package com.example.glasses.services;

import com.example.glasses.entities.RevenueRecord;
import com.example.glasses.repositories.RevenueRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FinanceReportService {
    private final RevenueRecordRepository revenueRecordRepository;
    private final FinanceService financeService;
    public FinanceReportService(FinanceService financeService, RevenueRecordRepository revenueRecordRepository) {
        this.financeService = financeService;
        this.revenueRecordRepository = revenueRecordRepository;
    }

    public List<Integer> getMonthlyRevenueData() {
        List<RevenueRecord> revenueRecords = financeService.getRevenueRecords(
                LocalDate.now().minusMonths(12), LocalDate.now());
        Map<String, BigDecimal> monthlyRevenue = revenueRecords.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getDate().getMonth().toString(), // Групування за місяцем
                        Collectors.reducing(BigDecimal.ZERO, RevenueRecord::getAmount, BigDecimal::add)
                ));

        return monthlyRevenue.values().stream()
                .map(BigDecimal::intValue)
                .collect(Collectors.toList());
    }
    public List<String> getMonthlyRevenueLabels() {
        return List.of("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December");
    }
    public List<Integer> getSalesTrendsData() {
        List<RevenueRecord> monthlyRecords = financeService.getRevenueRecords(
                LocalDate.now().minusDays(29), LocalDate.now());

        Map<LocalDate, BigDecimal> revenueByDate = monthlyRecords.stream()
                .collect(Collectors.groupingBy(
                        RevenueRecord::getDate,
                        Collectors.reducing(BigDecimal.ZERO, RevenueRecord::getAmount, BigDecimal::add)
                ));

        return revenueByDate.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)  // Пропускаємо дати з 0
                .sorted(Map.Entry.comparingByKey())  // Сортуємо за датою
                .map(entry -> entry.getValue().intValue())
                .collect(Collectors.toList());
    }

    public List<String> getSalesTrendsLabels() {
        List<RevenueRecord> monthlyRecords = financeService.getRevenueRecords(
                LocalDate.now().minusDays(29), LocalDate.now());

        Map<LocalDate, BigDecimal> revenueByDate = monthlyRecords.stream()
                .collect(Collectors.groupingBy(
                        RevenueRecord::getDate,
                        Collectors.reducing(BigDecimal.ZERO, RevenueRecord::getAmount, BigDecimal::add)
                ));

        return revenueByDate.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)  // skip days where amount = 0;
                .sorted(Map.Entry.comparingByKey())  // sort by date
                .map(entry -> entry.getKey().toString())
                .collect(Collectors.toList());
    }
    public List<BigDecimal> getExpenseData() {
        BigDecimal expenses = financeService.calculatePurchaseExpenses();
        return List.of(expenses);
    }
    public List<String> getExpenseCategories() {
        return List.of("Total Purchase Expenses");
    }
}
