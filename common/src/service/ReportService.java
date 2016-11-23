package service;

import java.util.*;

import dto.ReportByDay;
import models.*;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.SortParameterParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {
	
	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private ReferralService serviceReferral;
	
	@Autowired
	private StatisticReferralsService serviceReferralStatistic;
	
	public List<SailProfit> sortBySailProfit (List<SailProfit> list, final String type){
		Collections.sort(list, new Comparator<SailProfit>() {
            @Override
            public int compare(SailProfit sail1, SailProfit sail2) {
                if (sail2.getProfit() == sail1.getProfit()) return 0;
                if (type.equals("desc")) return sail2.getProfit().compareTo(sail1.getProfit()) ;
                return sail1.getProfit().compareTo(sail2.getProfit());
            }
        });
		return list;
	}

    public List<ReportByDay> sortByDailyProfit (List<ReportByDay> list, final String type){
        Collections.sort(list, new Comparator<ReportByDay>() {
            @Override
            public int compare(ReportByDay report1, ReportByDay report2) {
                if (report2.getProfit() == report1.getProfit()) return 0;
                if (type.equals("desc")) return report2.getProfit().compareTo(report1.getProfit());
                return report1.getProfit().compareTo(report2.getProfit());
            }
        });
        return list;
    }

    @Transactional
    public List<SailProfit> getProfitBySails(Long referralId, PaginationFilter pagination, DateFilter dateSail, String sort) {
        if ("profit".equals(SortParameterParser.getColumnName(sort)))
            return sortBySailProfit(SailProfit.convertToSailProfit(serviceReferral.findBySailDate(referralId, pagination, dateSail, null).getSails()), sort);
        return SailProfit.convertToSailProfit(serviceReferral.findBySailDate(referralId, pagination, dateSail, sort).getSails());
    }

    @Transactional
    private List<ReportByDay> getStatisticByDay(String nameBuyer, PaginationFilter pagination, DateFilter dateRegistrationFilter, String tracker, String sort) {
        return serviceReferralStatistic.byDate(serviceBuyer.get(nameBuyer), pagination, dateRegistrationFilter, tracker, sort);
    }
    
    @Transactional
    public List<ReportByDay> getReportByDay(String nameBuyer, PaginationFilter pagination, DateFilter dateRegistrationFilter, String tracker, String sort) {
        if ("profit".equals(SortParameterParser.getColumnName(sort)))
            return sortByDailyProfit(getStatisticByDay(nameBuyer, pagination, dateRegistrationFilter, tracker, null), SortParameterParser.getTypeOrder(sort));
        return getStatisticByDay(nameBuyer, pagination, dateRegistrationFilter, tracker, sort);
    }
}
