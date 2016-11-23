package service;

import dao.StatisticReferralDAO;
import entity.Buyer;
import entity.StatisticReferral;
import utils.DateFilter;
import dto.ReportByDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import utils.PaginationFilter;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class StatisticReferralsService {

    @Autowired
    @Qualifier("StatisticDao")
    private StatisticReferralDAO statDao;
    
    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    SailService sailService;

    @Transactional
    public void update(StatisticReferral click) {
        statDao.update(click);
    }

    @Transactional
    public void save(StatisticReferral click) {
        statDao.save(click);
    }

    @Transactional
    public void saveClickByLink(String codePartner, Date date, String tracker) {
        Buyer buyer = serviceBuyer.getByRefCode(codePartner);
        if (buyer == null)
            return;
        StatisticReferral statistic = getStatistic(buyer, date, tracker);
        statistic.setClickLinkAmount(statistic.getClickLinkAmount() + 1);
        save(statistic);
    }

    @Transactional
    public void saveEnterCode(String code, Date date) {
        Buyer buyer = serviceBuyer.getByRefCode(code);
        if (buyer == null)
            return;
        StatisticReferral statistic = statDao.byDay(buyer, date, null);
        statistic.setEnterCodeAmount(statistic.getEnterCodeAmount() + 1);
        save(statistic);
    }

    @Transactional
    public void saveSailStatistic(Buyer buyer, Date date, String tracker) {
        StatisticReferral statistic = statDao.byDay(buyer, date, tracker);
        statistic.setSailAmount(statistic.getSailAmount() + 1);
        save(statistic);
    }

    @Transactional
    public void saveRegistrationStatistic(Buyer buyer,String tracker) {
        saveRegistrationStatistic(buyer, tracker, new Date());
    }

    @Transactional
    public void saveRegistrationStatistic(Buyer buyer,String tracker, Date dateReg) {
        StatisticReferral statistic = statDao.byDay(buyer, dateReg, tracker);
        statistic.setRegAmount(statistic.getRegAmount() + 1);
        save(statistic);
    }

    public List<ReportByDay> byDate(Buyer buyer, PaginationFilter pagination, DateFilter date, String tracker, String sort) {
            return statDao.listByDate(buyer, pagination, date, tracker, sort);
    }

    public void saveProfit(Buyer buyer, String tracker, Double profit) {
        StatisticReferral stat = getStatistic(buyer, new Date(), tracker);
        stat.setProfit(stat.getProfit() + profit);
        save(stat);
    }

    private StatisticReferral getStatistic(Buyer buyer, Date date, String tracker) {
        return statDao.byDay(buyer, date, tracker);
    }

    @Transactional
    public int countByDate(String nameBuyer, DateFilter date, String tracker) {
        Buyer b = serviceBuyer.get(nameBuyer);
            return statDao.count(b, date, tracker);
    }

}
