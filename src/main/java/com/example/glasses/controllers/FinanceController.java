package com.example.glasses.controllers;

import com.example.glasses.repositories.RevenueRecordRepository;
import com.example.glasses.services.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;


@Controller
public class FinanceController {
    private final FinanceService financeService;
    private final RevenueRecordRepository revenueRecordRepository;
    @Autowired
    public FinanceController(FinanceService financeService, RevenueRecordRepository revenueRecordRepository) {
        this.financeService = financeService;
        this.revenueRecordRepository = revenueRecordRepository;
    }

    @GetMapping("/finances")
    public String showFinances(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfYear = today.withDayOfYear(1);

        BigDecimal netIncome = financeService.calculateNetIncome(today.withDayOfMonth(1), today);
        BigDecimal purchaseExpenses = financeService.calculatePurchaseExpenses();
        BigDecimal dailyRevenue = revenueRecordRepository.sumRevenueBetweenDates(today, today);
        BigDecimal monthlyRevenue = revenueRecordRepository.sumRevenueBetweenDates(startOfMonth, today);
        BigDecimal yearlyRevenue = revenueRecordRepository.sumRevenueBetweenDates(startOfYear, today);

        model.addAttribute("netIncome", netIncome);
        model.addAttribute("purchaseExpenses", purchaseExpenses);
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("yearlyRevenue", yearlyRevenue);

        return "finances";
    }

}
