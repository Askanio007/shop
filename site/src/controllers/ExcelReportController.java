package controllers;

import entity.Buyer;
import service.*;
import utils.DateFilter;
import models.Referral;
import models.ReportByDay;
import models.SailProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import utils.CurrentUser;
import view.ViewPagination;

import java.util.Date;
import java.util.List;

@Controller
public class ExcelReportController {

    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    private ReferralService serviceReferal;

    @Autowired
    private ReportService serviceReport;

    @Autowired
    private SailService serviceSail;

    @Autowired
    private ClickStatisticService serviceClickStatistic;

    @RequestMapping(value = "/user/reports/byDayExcel", method = RequestMethod.POST)
    public ModelAndView export(@RequestParam(required = false, value = "regFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                               @RequestParam(required = false, value = "regTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                               @RequestParam(required = false, value = "tracker", defaultValue = "") String tracker,
                               @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
                               @RequestParam(required = false, value = "page", defaultValue = "1") String page)  {
        DateFilter dateRegistration = new DateFilter(from,to);
        ViewPagination viewPagination = new ViewPagination(page, serviceClickStatistic.countClickStatisticByDate(CurrentUser.getName(), dateRegistration, ""), 50);
        List<ReportByDay> reports = serviceReport.getReportByDay(CurrentUser.getName(), viewPagination.getDBPagination(), dateRegistration, tracker, sort);
        return new ModelAndView("reportByDayExcel","statisticList",reports);
    }


    @RequestMapping(value = "/user/reports/referralsExcel", method = RequestMethod.POST)
    public ModelAndView referalsExcel(@RequestParam(required = false, value = "regFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date regFrom,
                                      @RequestParam(required = false, value = "regTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date regTo,
                                      @RequestParam(required = false, value = "sailFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailFrom,
                                      @RequestParam(required = false, value = "sailTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailTo,
                                      @RequestParam(required = false, value = "tracker", defaultValue = "") String tracker,
                                      @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
                                      @RequestParam(required = false, value = "page", defaultValue = "1") String page)  {
        DateFilter registrationDate = new DateFilter(regFrom,regTo);
        DateFilter sailDate = new DateFilter(sailFrom,sailTo);
        ViewPagination viewPagination = new ViewPagination(page, serviceReferal.countReferrals(CurrentUser.getName(), registrationDate ,tracker));
        List<Referral> referrals = serviceReferal.find(CurrentUser.getName(),viewPagination.getDBPagination(), registrationDate, sailDate ,tracker, sort);
        return new ModelAndView("referralsListExcel","referralsList", referrals);
    }

    @RequestMapping(value = "/user/reports/byDayDetailExcel", method = RequestMethod.POST)
    public ModelAndView referalsByDayDetail(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                            @RequestParam(required = false, value = "tracker", defaultValue = "") String tracker,
                                            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
                                            @RequestParam(required = false, value = "page", defaultValue = "1") String page)  {
        Buyer buyer = serviceBuyer.getBuyer(CurrentUser.getName());
        ViewPagination viewPagination = new ViewPagination("1", serviceReferal.countActiveReferralByDay(buyer.getId(), date, tracker));
        List<Referral> referrals = serviceReferal.findDailyActive(buyer.getId(), viewPagination.getDBPagination(), date, tracker);
        return new ModelAndView("byDayDetailExcel","ByDayDetail", referrals);
    }


    @RequestMapping(value = "/user/reports/referralDetailExcel", method = RequestMethod.POST)
    public ModelAndView referalStatisticDetail(@RequestParam("id") Long referId,
                                         @RequestParam(required = false, value = "sailFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailFrom,
                                         @RequestParam(required = false, value = "sailTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailTo,
                                         @RequestParam(required = false, value = "page", defaultValue = "1") String page,
                                         @RequestParam(required = false, value = "sort") String sort) {
        DateFilter filter = new DateFilter(sailFrom,sailTo);
        ViewPagination viewPagination = new ViewPagination(page, serviceSail.countReferralSail(referId, filter));
        List<SailProfit> sailProfit = serviceReport.getProfitBySails(referId, viewPagination.getDBPagination(), filter, sort);
        return new ModelAndView("referralSailExcel","referralSail",sailProfit);
    }
}
