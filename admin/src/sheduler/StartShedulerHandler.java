package sheduler;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import service.SettingsService;

@Service
public class StartShedulerHandler {

	@Autowired
	private GeneralDiscount discount;

	@Autowired
	private PrivateDiscount disactiveDiscount;

	@Autowired
	private AutoCompletionDelivery autoCompletionDelivery;

	@Autowired
	private CashbackPercent cashbackPercent;

	@Autowired
	private AggregationProfitStatistics aggregationProfitStatistics;

	@Autowired
	private SettingsService setting;

	@Autowired
	@Qualifier("myScheduler")
	private TaskScheduler scheduler;

	@PostConstruct
	public void Start() {
		scheduler.scheduleAtFixedRate(discount, new Date(), setting.getTimeGeneralDiscount());
		scheduler.scheduleAtFixedRate(disactiveDiscount, new Date(), setting.getTimeDisactiveDiscount());
		scheduler.scheduleAtFixedRate(autoCompletionDelivery, new Date(), 120000);
		scheduler.scheduleAtFixedRate(aggregationProfitStatistics, new Date(), 120000);
		scheduler.schedule(cashbackPercent, new CronTrigger("0 0 0 1 * ?"));
	}

}
