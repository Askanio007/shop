package dao;

import entity.ClickByLink;
import models.ReportByDay;
import org.hibernate.Hibernate;
import org.hibernate.transform.*;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Repository;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.SortParameterParser;

import java.util.Date;
import java.util.List;

@Repository("ClickStatisticDao")
public class ClickStatisticDAOImpl extends GeneralDAOImpl<ClickByLink> implements ClickStatisticDAO {

    private static final String COUNT_CLICK_STATISTIC =
            "select count(click) " +
                    "from " +
                            "ClickByLink click inner join " +
                            "click.buyer buyer " +
                    "where " +
                            "buyer.id = :id and " +
                            "click.date between :dateFrom and :dateTo";


    private static final String LIST_CLICK_STATISTIC_BY_DATE =
            "select click.date as date, " +
                    "count(buyer) as registrationAmount, " +
                    "sum(click.clickLinkAmount) as clickLinkAmount, " +
                    "sum(click.enterCodeAmount) as enterCodeAmount, " +
                    "sum(click.sailAmount) as sailAmount " +
            "from Buyer b," +
                    "ClickByLink click inner join " +
                    "click.buyer buyer " +
            "where " +
                    "b.refId = buyer.id and " +
                    "buyer.id = :id and " +
                    "click.date between :dateFrom and :dateTo and " +
                    "year(b.dateReg) = year(click.date) and " +
                    "month(b.dateReg) = month(click.date) and " +
                    "day(b.dateReg) = day(click.date) " +
            "group by click.date";

    private static final String LIST_CLICK_STATISTIC_BY_DATE_WITH_TRACKER =
            "select distinct click.date as date, " +
                    "count(b) as registrationAmount, " +
                    "sum(click.clickLinkAmount) as clickLinkAmount, " +
                    "sum(click.enterCodeAmount) as enterCodeAmount, " +
                    "sum(click.sailAmount) as sailAmount " +
            "from Buyer b, " +
                    "ClickByLink click inner join " +
                    "click.buyer buyer " +
            "where " +
                    "buyer.id = :id and " +
                    "b.refId = buyer.id and " +
                    "b.tracker = :tracker and " +
                    "click.tracker = :tracker and " +
                    "year(b.dateReg) = year(click.date) and " +
                    "month(b.dateReg) = month(click.date) and " +
                    "day(b.dateReg) = day(click.date) and  " +
                    "click.date between :dateFrom and :dateTo " +
            "group by click.date, b.id";


    private static final String CLICK_STATISTIC_BY_DAY =
            "select click from ClickByLink click inner join click.buyer buyer where " +
                    "buyer.id = :id and " +
                    "click.date = :date";



    @Override
    public ClickByLink clickStatisticByDay(Long buyerId, Date date, String tracker) {

            if (tracker == null)
                return (ClickByLink) createQuery(CLICK_STATISTIC_BY_DAY +
                        " and click.tracker is null")
                        .setDate("date", date)
                        .setLong("id", buyerId)
                        .uniqueResult();
            return (ClickByLink) createQuery(CLICK_STATISTIC_BY_DAY +
                    " and click.tracker = :tracker")
                    .setDate("date", date)
                    .setString("tracker", tracker)
                    .setLong("id", buyerId)
                    .uniqueResult();
    }


    @Override
    public List<ReportByDay> listClickStatisticByDate(Long buyerId, PaginationFilter dbFilter, DateFilter date) {
        List<ReportByDay> reports = setPagination(createQuery(LIST_CLICK_STATISTIC_BY_DATE), dbFilter)
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .setResultTransformer(Transformers.aliasToBean(ReportByDay.class))
                .list();
        return reports;
    }

    @Override
    public List<ReportByDay> listClickStatisticByDateOrder(Long buyerId, PaginationFilter dbFilter, DateFilter date, String sort) {
        List<ReportByDay> reports =    setPagination(createQuery(LIST_CLICK_STATISTIC_BY_DATE +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), dbFilter)
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .setResultTransformer(Transformers.aliasToBean(ReportByDay.class))
                .list();
        return reports;
    }

    @Override
    public List<ReportByDay> listClickStatisticByDateWithTracker(Long buyerId, PaginationFilter dbFilter, DateFilter date, String tracker) {
        List<ReportByDay> reports =   setPagination(createQuery(LIST_CLICK_STATISTIC_BY_DATE_WITH_TRACKER), dbFilter)
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .setString("tracker", tracker)
                .setResultTransformer(Transformers.aliasToBean(ReportByDay.class))
                .list();
        return reports;
    }

    @Override
    public List<ReportByDay> listClickStatisticByDateWithTrackerOrder(Long buyerId, PaginationFilter dbFilter, DateFilter date, String tracker, String sort) {
        List<ReportByDay> reports =    setPagination(createQuery(LIST_CLICK_STATISTIC_BY_DATE_WITH_TRACKER +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), dbFilter)
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .setString("tracker", tracker)
                .setResultTransformer(Transformers.aliasToBean(ReportByDay.class))
                .list();
        return reports;
    }

    @Override
    public int countClickStatistic(Long buyerId, DateFilter date) {
        Object count = createQuery(COUNT_CLICK_STATISTIC)
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }

    @Override
    public int countClickStatistic(Long buyerId, DateFilter date, String tracker) {
        Object count = createQuery(COUNT_CLICK_STATISTIC +
                " and click.tracker = :tracker")
                .setDate("dateFrom", date.getFrom())
                .setDate("dateTo", date.getTo())
                .setLong("id", buyerId)
                .setString("tracker", tracker)
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }
}
