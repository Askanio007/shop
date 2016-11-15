package sheduler;

import entity.Buyer;
import entity.Sail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.BuyerService;
import service.SailService;
import service.StatisticReferralsService;
import utils.DateFilter;

import java.util.Date;
import java.util.List;

@Component
public class AggregationProfitStatistics implements Runnable {

    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    private SailService serviceSail;

    @Autowired
    private StatisticReferralsService serviceStatistic;


    @Override
    public void run() {
        Date date = new Date();
        List<Buyer> buyers = serviceBuyer.getActiveByDay(date);
        for (Buyer buyer : buyers) {
            if (buyer.getRefId() == null) continue;
            Buyer parent = serviceBuyer.get(buyer.getRefId());
            List<Sail> todaySails = serviceSail.listCompletedByDate(buyer.getId(), new DateFilter(date));
            Double profit = 0.0;
            for (Sail sail : todaySails) {
                profit =+ serviceBuyer.profitFromReferralBySail(sail, parent.getPercentCashback());
            }
            serviceStatistic.saveProfit(parent, buyer.getTracker(), profit);
        }
    }
}
