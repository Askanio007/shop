package sheduler;


import entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;
import service.BuyerService;
import service.SettingsService;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CashbackPercent implements Runnable {

    @Autowired
    private SettingsService settings;

    @Autowired
    private BuyerService serviceBuyer;

    public void run() {
        List<Buyer> buyers = serviceBuyer.list();
        for (Buyer buyer : buyers) {
            BigDecimal profit = serviceBuyer.getProfitByLastMonth(buyer.getId());
            if (profit.doubleValue() > settings.minMonthProfit() && profit.doubleValue() < settings.maxMonthProfit())
                continue;
            if (profit.doubleValue() < settings.minMonthProfit() && buyer.getPercentCashback() > settings.getBaseCashback())
                buyer.setPercentCashback(buyer.getPercentCashback() - 1);
            if (profit.doubleValue() > settings.maxMonthProfit() && buyer.getPercentCashback() < 100)
                buyer.setPercentCashback(buyer.getPercentCashback() + 1);
            serviceBuyer.edit(buyer);
        }
    }
}
