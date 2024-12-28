package com.example.glasses.services;


import com.example.glasses.entities.RevenueRecord;
import com.example.glasses.repositories.GlassRepository;
import com.example.glasses.repositories.RevenueRecordRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {
    private final GlassRepository glassRepository;
    private final RevenueRecordRepository revenueRecordRepository;

    public FinanceService(GlassRepository glassRepository, RevenueRecordRepository revenueRecordRepository) {
        this.glassRepository = glassRepository;
        this.revenueRecordRepository = revenueRecordRepository;
    }

    // 1. Чистий дохід (на основі проданих окулярів) хз як працює, думаю некоректно, але вроді відображає чистий дохід,
    // правда хз нащо я то написав, якщо чистий дохід вже вираховується на іншій сторінці, але по ідеї так треба (P.S. вони різні)
    public BigDecimal calculateNetIncome(LocalDate from, LocalDate to) {
        return glassRepository.findAll()
                .stream()
                .map(glass -> {
                    BigDecimal revenue = glass.getSalePrice().multiply(new BigDecimal(glass.getSoldQuantity())); // Дохід від продажу
                    BigDecimal expenses = glass.getPurchasePrice().multiply(new BigDecimal(glass.getSoldQuantity())); // Витрати на закупівлю
                    return revenue.subtract(expenses); // Чистий дохід для кожної моделі
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Сумуємо чистий дохід для всіх моделей
    }

    // 2. Витрати на закупівлю (actually вже є на сторінці overall,
    // але цей метод відрізняється тим, що збирає витрати протягом визначеного періоду чи шо)
    public BigDecimal calculatePurchaseExpenses() {
        return glassRepository.findAll()
                .stream()
                .filter(glass -> glass.getSoldQuantity() > 0)
                .map(glass -> glass.getPurchasePrice().multiply(new BigDecimal(glass.getSoldQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 3. перевірка запису на наявність і збереження запису
    // передача метода на контролер -> front

    @Transactional
    public void saveDailyRevenue() {
        BigDecimal totalRevenue = glassRepository.findAll().stream()
                .filter(glass -> glass.getDateOfSale() != null && glass.getDateOfSale().equals(LocalDate.now())) // Фільтруємо за сьогоднішньою датою записи
                .map(glass -> glass.getSalePrice()
                        .multiply(new BigDecimal(glass.getSoldQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RevenueRecord todayRevenue = revenueRecordRepository.findByDate(LocalDate.now());
        if (todayRevenue == null) {
            todayRevenue = new RevenueRecord(LocalDate.now(), BigDecimal.ZERO);
            System.out.println("No record found for today's date.");
        } else {
            System.out.println("Record found: " + todayRevenue.getAmount());
        }


        todayRevenue.setAmount(totalRevenue);
        revenueRecordRepository.save(todayRevenue);
    }



    // 4. Отримати всі записи виручки за період
    public List<RevenueRecord> getRevenueRecords(LocalDate from, LocalDate to) {
        return revenueRecordRepository.findByDateBetween(from, to);
    }

    // 5. Порахувати загальну виручку за період (поки не юзаєм, але лишу це тут, вдруг пригодиться колись )
    public BigDecimal calculateTotalRevenue(LocalDate from, LocalDate to) {
        return getRevenueRecords(from, to)
                .stream()
                .map(RevenueRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
