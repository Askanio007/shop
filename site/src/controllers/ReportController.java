package controllers;

import entity.Buyer;
import models.Referral;
import service.*;
import utils.DateFilter;
import models.SailProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import utils.CurrentUser;
import view.DateConverter;
import view.ViewPagination;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ReportController {

    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    private ReferralService serviceReferal;

    @Autowired
    private SailService serviceSail;
    
    @Autowired
    private ReportService serviceReport;

    @Autowired
    private StatisticReferralsService serviceClickStatistic;

    public boolean dateIsNull(Date from, Date to) {
        return from == null && to == null ? true : false;
    }

    @RequestMapping(value = "/user/reports/referrals", method = RequestMethod.GET)
    public String referrals(Model model, HttpServletRequest request)  {
        BuyController.setCountProductBasketInModel(request, model);
        ViewPagination viewPagination = new ViewPagination("1", serviceReferal.count(CurrentUser.getName(), new DateFilter() ,""));
        model.addAttribute("pagination", viewPagination);
        List<Referral> referrals = serviceReferal.find(CurrentUser.getName(), viewPagination.getDBPagination(), new DateFilter() , new DateFilter()  ,"", "");
        model.addAttribute("referrals", referrals);
        return "user/reports/referrals";
    }

    @RequestMapping(value = "/user/reports/referrals", method = RequestMethod.POST)
    public String referrals(@RequestParam(required = false, value = "regFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date regFrom,
                               @RequestParam(required = false, value = "regTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date regTo,
                               @RequestParam(required = false, value = "sailFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailFrom,
                               @RequestParam(required = false, value = "sailTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailTo,
                               @RequestParam(required = false, value = "tracker") String tracker,
                               @RequestParam(required = false, value = "sort") String sort,
                               @RequestParam(required = false, value = "page", defaultValue = "1") String page,
                               Model model, HttpServletRequest request)  {
        BuyController.setCountProductBasketInModel(request, model);
        DateFilter registrationDate = new DateFilter(regFrom,regTo);
        DateFilter sailDate = new DateFilter(sailFrom,sailTo);
        ViewPagination viewPagination = new ViewPagination(page, serviceReferal.count(CurrentUser.getName(), registrationDate ,tracker));
        List<Referral> referrals = serviceReferal.find(CurrentUser.getName(),viewPagination.getDBPagination(), registrationDate, sailDate ,tracker, sort);
        model.addAttribute("referrals", referrals);
        model.addAttribute("tracker", tracker);
        model.addAttribute("pagination", viewPagination);
        if (!dateIsNull(regFrom, regTo)) model.addAttribute("dateRegistration", registrationDate);
        if (!dateIsNull(sailFrom, sailTo)) model.addAttribute("sailDate", sailDate);
        model.addAttribute("sort", sort);
        return "user/reports/referrals";
    }

    @RequestMapping(value = "/user/reports/referralStatisticDetail", method = RequestMethod.GET)
    public String referralStatisticDetail(@RequestParam("id") Long referId, Model model,HttpServletRequest request) {
        BuyController.setCountProductBasketInModel(request, model);
        DateFilter date = new DateFilter();
        ViewPagination viewPagination = new ViewPagination("1", serviceSail.countByReferral(referId,  date));
        List<SailProfit> sailProfit = serviceReport.getProfitBySails(referId, viewPagination.getDBPagination(), date, "");
        model.addAttribute("referral", serviceReferal.find(referId));
        model.addAttribute("sails", sailProfit);
        model.addAttribute("pagination", viewPagination);
        request.setAttribute("id", referId);
        return "user/reports/referralsStatisticDetail";
    }

    @RequestMapping(value = "/user/reports/referralStatisticDetail", method = RequestMethod.POST)
    public String referralStatisticDetail(@RequestParam("id") Long referId,
                                         @RequestParam(required = false, value = "sailFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailFrom,
                                         @RequestParam(required = false, value = "sailTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sailTo,
                                         @RequestParam(required = false, value = "sort") String sort,
                                         @RequestParam(required = false, value = "page", defaultValue = "1") String page,
                                         Model model,HttpServletRequest request) {
        BuyController.setCountProductBasketInModel(request, model);
        DateFilter filter = new DateFilter(sailFrom,sailTo);
        ViewPagination viewPagination = new ViewPagination(page, serviceSail.countByReferral(referId, filter));
        List<SailProfit> sailProfit = serviceReport.getProfitBySails(referId, viewPagination.getDBPagination(), filter, sort);
        model.addAttribute("referral", serviceReferal.find(referId));
        model.addAttribute("sails", sailProfit);
        model.addAttribute("sort", sort);
        model.addAttribute("pagination", viewPagination);
        if (!dateIsNull(sailFrom, sailTo)) model.addAttribute("sailDate", filter);
        request.setAttribute("id", referId);
        return "user/reports/referralsStatisticDetail";
    }

    @RequestMapping(value = "/user/reports/referralsByDay", method = RequestMethod.GET)
    public String referralsByDay(Model model, HttpServletRequest request) {
        DateFilter dateRegistration = new DateFilter();
        BuyController.setCountProductBasketInModel(request, model);
        ViewPagination viewPagination = new ViewPagination("1", serviceClickStatistic.countByDate(CurrentUser.getName(), dateRegistration, ""), 50);
        model.addAttribute("days",serviceReport.getReportByDay(CurrentUser.getName(), viewPagination.getDBPagination(), dateRegistration, "", ""));
        model.addAttribute("pagination", viewPagination);
        return "user/reports/referralsByDay";
    }

    @RequestMapping(value = "/user/reports/referralsByDay", method = RequestMethod.POST)
    public String referralsByDay(@RequestParam(required = false, value = "regFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                   @RequestParam(required = false, value = "regTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                   @RequestParam(required = false, value = "tracker") String tracker,
                                   @RequestParam(required = false, value = "sort") String sort,
                                   @RequestParam(required = false, value = "page", defaultValue = "1") String page,
                                   Model model, HttpServletRequest request) {
        DateFilter dateRegistration = new DateFilter(from,to);
        BuyController.setCountProductBasketInModel(request, model);
        ViewPagination viewPagination = new ViewPagination(page, serviceClickStatistic.countByDate(CurrentUser.getName(), dateRegistration, ""), 50);
        model.addAttribute("days",serviceReport.getReportByDay(CurrentUser.getName(), viewPagination.getDBPagination(), dateRegistration, tracker, sort));
        model.addAttribute("tracker", tracker);
        model.addAttribute("sort", sort);
        model.addAttribute("pagination", viewPagination);
        if (!dateIsNull(from, to)) model.addAttribute("dateRegistration", dateRegistration);
        return "user/reports/referralsByDay";
    }

    @RequestMapping(value = "/user/reports/referralsByDayDetail", method = RequestMethod.GET)
    public String referralsByDayDetail(@RequestParam("date") @DateTimeFormat(pattern="yyyyMMdd") Date date,
                                      @RequestParam(required = false, value = "tracker") String tracker,
                                      Model model, HttpServletRequest request)  {
        BuyController.setCountProductBasketInModel(request, model);
        Buyer buyer = serviceBuyer.get(CurrentUser.getName());
        ViewPagination viewPagination = new ViewPagination("1", serviceReferal.countActiveByDay(buyer.getId(), date, tracker));
        model.addAttribute("referrals",serviceReferal.findDailyActive(buyer.getId(), viewPagination.getDBPagination(), date, tracker, ""));
        model.addAttribute("tracker", tracker);
        model.addAttribute("day", DateConverter.getFormatView().format(date));
       model.addAttribute("pagination", viewPagination);
        return "user/reports/referralsByDayDetail";
    }

    @RequestMapping(value = "/user/reports/referralsByDayDetail", method = RequestMethod.POST)
    public String referralsByDayDetail(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                      @RequestParam(required = false, value = "tracker") String tracker,
                                      @RequestParam(required = false, value = "sort") String sort,
                                      @RequestParam(required = false, value = "page", defaultValue = "1") String page,
                                      Model model, HttpServletRequest request)  {
        BuyController.setCountProductBasketInModel(request, model);
        Buyer buyer = serviceBuyer.get(CurrentUser.getName());
        ViewPagination viewPagination = new ViewPagination(page,serviceReferal.countActiveByDay(buyer.getId(), date, tracker));
        model.addAttribute("referrals",serviceReferal.findDailyActive(buyer.getId(), viewPagination.getDBPagination(), date, tracker, sort));
        model.addAttribute("sort", sort);
        model.addAttribute("tracker", tracker);
        model.addAttribute("day", DateConverter.getFormatView().format(date));
        model.addAttribute("pagination", viewPagination);
        return "user/reports/referralsByDayDetail";
    }
}
