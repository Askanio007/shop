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

    @Override
    public void run() {
       serviceBuyer.aggregateProfitStatistic();
    }
}
