package sheduler;


import entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;
import service.BuyerService;
import service.SettingsService;

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
            Double profit = serviceBuyer.getProfitByLastMonth(buyer.getId());
            if (profit > settings.minMonthProfit() && profit < settings.maxMonthProfit())
                continue;
            if (profit < settings.minMonthProfit() && buyer.getPercentCashback() > settings.getBaseCashback())
                buyer.setPercentCashback(buyer.getPercentCashback() - 1);
            if (profit > settings.maxMonthProfit() && buyer.getPercentCashback() < 100)
                buyer.setPercentCashback(buyer.getPercentCashback() + 1);
            serviceBuyer.edit(buyer);
        }
    }
}
