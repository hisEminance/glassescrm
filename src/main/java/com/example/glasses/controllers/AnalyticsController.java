package com.example.glasses.controllers;

import com.example.glasses.services.FinanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AnalyticsController {
    private final FinanceReportService financeReportService;

    @Autowired
    public AnalyticsController(FinanceReportService financeReportService) {
        this.financeReportService = financeReportService;
    }

    @GetMapping("/analytics")
    public String getAnalyticsPage(Model model) {

        List<Integer> monthlyRevenueData = financeReportService.getMonthlyRevenueData();
        List<String> monthlyRevenueLabels = financeReportService.getMonthlyRevenueLabels();
        model.addAttribute("monthlyRevenueData", monthlyRevenueData);
        model.addAttribute("monthlyRevenueLabels", monthlyRevenueLabels);


        List<Integer> salesTrendsData = financeReportService.getSalesTrendsData();
        List<String> salesTrendsLabels = financeReportService.getSalesTrendsLabels();
        model.addAttribute("salesTrendsData", salesTrendsData);
        model.addAttribute("salesTrendsLabels", salesTrendsLabels);


        List<BigDecimal> expenseData = financeReportService.getExpenseData();
        List<String> expenseCategories = financeReportService.getExpenseCategories();
        model.addAttribute("expenseData", expenseData);
        model.addAttribute("expenseCategories", expenseCategories);

        return "analytics";
    }
}