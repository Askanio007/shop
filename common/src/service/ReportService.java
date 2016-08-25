package service;

import java.util.*;

import models.*;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.SortParameterParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Buyer;
import entity.Sail;
import entity.SoldProduct;

@Service
public class ReportService {
	
	@Autowired
	private BuyerService serviceBuyer;
	
	@Autowired
	private ReferralService serviceReferral;
	
	@Autowired
	private ClickStatisticService serviceClickStatistic;
	
	public List<SailProfit> sortBySailProfit (List<SailProfit> list, final String type){
		Collections.sort(list, new Comparator<SailProfit>() {
            @Override
            public int compare(SailProfit sail1, SailProfit sail2) {
                if (sail2.getProfit() == sail1.getProfit()) return 0;
                if (type.equals("desc")) return sail2.getProfit() > sail1.getProfit() ? 1 : -1;
                return sail1.getProfit() > sail2.getProfit() ? 1 : -1;
            }
        });
		return list;
	}

    public List<ReportByDay> sortByDailyProfit (List<ReportByDay> list, final String type){
        Collections.sort(list, new Comparator<ReportByDay>() {
            @Override
            public int compare(ReportByDay report1, ReportByDay report2) {
                if (report2.getProfit() == report1.getProfit()) return 0;
                if (type.equals("desc")) return report2.getProfit() > report1.getProfit() ? 1 : -1;
                return report1.getProfit() > report2.getProfit() ? 1 : -1;
            }
        });
        return list;
    }

    public void setProfit(List<ReportByDay> days, Buyer buyer) {
        for (ReportByDay day : days) {
            day.setProfit(serviceReferral.calculateProfitByDay(buyer, day.getDate()));
        }
    }

    @Transactional
    public List<SailProfit> getProfitBySails(Long referalId, PaginationFilter pagination, DateFilter dateSail, String sort) {
        Referral referral;
        if ("".equals(sort) || sort == null) {
            referral = serviceReferral.findBySailDate(referalId, pagination, dateSail);
            return convertToSailProfit(referral.getSails());
        }

        if (SortParameterParser.getColumnName(sort).equals("profit")) {
            referral = serviceReferral.findBySailDate(referalId, pagination, dateSail);
            return sortBySailProfit(convertToSailProfit(referral.getSails()), sort);
        }
        referral = serviceReferral.findBySailDate(referalId, pagination, dateSail, sort);
        return convertToSailProfit(referral.getSails());
    }

    public List<SailProfit> convertToSailProfit(Collection<Sail> sails) {
        List<SailProfit> sailProfit = new ArrayList<>();
        for (Sail sail : sails) {
            List<ProductProfit> productProfit = new ArrayList<>();
            for (SoldProduct product : sail.getProducts()) {
                productProfit.add(new ProductProfit(product, sail.getCashbackPercent()));
            }
            sailProfit.add(new SailProfit(sail, productProfit));
        }
        return sailProfit;
    }

    @Transactional
    public List<ReportByDay> getReportByDay(String nameBuyer, PaginationFilter pagination, DateFilter dateRegistrationFilter, String tracker) {
        Buyer buyer = serviceBuyer.getBuyer(nameBuyer);
        List<ReportByDay> days = serviceClickStatistic.listClickStatisticByDate(buyer.getId(), pagination, dateRegistrationFilter, tracker);
        setProfit(days, buyer);
        return days;
    }
    
    @Transactional
    public List<ReportByDay> getReportByDay(String nameBuyer, PaginationFilter pagination, DateFilter dateRegistrationFilter, String tracker, String sort) {
        Buyer buyer = serviceBuyer.getBuyer(nameBuyer);
        List<ReportByDay> days;
        if ("".equals(sort) || sort == null)
            return getReportByDay(nameBuyer, pagination, dateRegistrationFilter, tracker);
        if (!SortParameterParser.getColumnName(sort).equals("profit")) {
            days = serviceClickStatistic.listClickStatisticByDateOrder(buyer.getId(), pagination, dateRegistrationFilter, tracker, sort);
            setProfit(days, buyer);
            return days;
        } else {
            return sortByDailyProfit(getReportByDay(nameBuyer, pagination, dateRegistrationFilter, tracker), SortParameterParser.getTypeOrder(sort));
        }
    }
}
