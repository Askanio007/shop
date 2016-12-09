package service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<SailProfit> sails = list.stream();
        return "desc".equals(type) ?
                sails.sorted((sail1, sail2) -> sail2.getProfit().compareTo(sail1.getProfit())).collect(Collectors.toList()) :
                sails.sorted((sail1, sail2) -> sail1.getProfit().compareTo(sail2.getProfit())).collect(Collectors.toList());
	}

    public List<ReportByDay> sortByDailyProfit (List<ReportByDay> list, final String type){
        // TODO: Artyom Тут нет дублирования Collectors.toList(), но мне нравится верхний вариант
        Stream<ReportByDay> reports = list.stream();
        if("desc".equals(type))
            reports.sorted((rep1, rep2) -> rep2.getProfit().compareTo(rep1.getProfit()));
        else
            reports.sorted((rep1, rep2) -> rep1.getProfit().compareTo(rep2.getProfit()));
        return reports.collect(Collectors.toList());
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
