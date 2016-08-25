package service;

import dao.ClickStatisticDAO;
import entity.Buyer;
import entity.ClickByLink;
import utils.DateFilter;
import models.ReportByDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import utils.PaginationFilter;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ClickStatisticService {

    @Autowired
    @Qualifier("ClickStatisticDao")
    private ClickStatisticDAO clickDao;
    
    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    SailService sailService;

    @Transactional
    public void update(ClickByLink click) {
        clickDao.update(click);
    }

    @Transactional
    public void add(ClickByLink click) {
        clickDao.add(click);
    }

    @Transactional
    public void setClickStatistic(String codePartner, Date date, String tracker) {
        Long id = serviceBuyer.getBuyerIdByRefCode(codePartner);
        if (id == null)
            return;
        ClickByLink click = clickDao.clickStatisticByDay(id, date, tracker);
        if (click != null) {
            click.setClickLinkAmount(click.getClickLinkAmount() + 1);
            update(click);
            return;
        }
        click = new ClickByLink(1, 0, 0, date, tracker,serviceBuyer.getBuyer(id));
        add(click);
    }

    @Transactional
    public void setSailStatistic(Buyer buyer) {
        ClickByLink click = clickDao.clickStatisticByDay(buyer.getId(), new Date(), buyer.getTracker());
        if (click != null) {
            click.setSailAmount(click.getSailAmount() + 1);
            update(click);
            return;
        }
        click = new ClickByLink(0, 0, 1, new Date(), buyer.getTracker(),buyer);
        add(click);
    }

    @Transactional
    public void setSailStatistic(Buyer buyer, Date date) {
        ClickByLink click = clickDao.clickStatisticByDay(buyer.getId(), date, buyer.getTracker());
        if (click != null) {
            click.setSailAmount(click.getSailAmount() + 1);
            update(click);
            return;
        }
        click = new ClickByLink(0, 0, 1, date, buyer.getTracker(),buyer);
        add(click);
    }

    @Transactional
    public void setEnterCode(String code, Date date) {
        Long id = serviceBuyer.getBuyerIdByRefCode(code);
        if (id == null)
            return;
        ClickByLink click = clickDao.clickStatisticByDay(id, date, null);
        if (click != null) {
            click.setEnterCodeAmount(click.getEnterCodeAmount() + 1);
            update(click);
            return;
        }
        click = new ClickByLink(0, 1, 0, date, null, serviceBuyer.getBuyer(id));
        add(click);
    }

    public List<ReportByDay> listClickStatisticByDate(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker) {
        if ("".equals(tracker))
            return clickDao.listClickStatisticByDate(buyerId, pagination, date);
        else
            return clickDao.listClickStatisticByDateWithTracker(buyerId, pagination, date, tracker);
    }

    public List<ReportByDay> listClickStatisticByDateOrder(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker, String sort) {
        if ("".equals(tracker))
            return clickDao.listClickStatisticByDateOrder(buyerId, pagination, date, sort);
        else
            return clickDao.listClickStatisticByDateWithTrackerOrder(buyerId, pagination, date, tracker, sort);
    }

    @Transactional
    public int countClickStatisticByDate(String nameBuyer, DateFilter date, String tracker) {
        Buyer b = serviceBuyer.getBuyer(nameBuyer);
        if ("".equals(tracker))
            return clickDao.countClickStatistic(b.getId(), date);
        else
            return clickDao.countClickStatistic(b.getId(), date, tracker);
    }

}
