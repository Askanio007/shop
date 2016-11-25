package dao;

import entity.Buyer;
import entity.StatisticReferral;
import dto.ReportByDay;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.*;
import org.springframework.stereotype.Repository;
import utils.DateBuilder;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Projections.*;
import static org.hibernate.criterion.Restrictions.*;

@Repository("StatisticDao")
public class StatisticReferralDAOImpl extends GeneralDAOImpl<StatisticReferral> implements StatisticReferralDAO {

    @Override
    public StatisticReferral byDay(Buyer buyer, Date date, String tracker) {
        Date start = DateBuilder.startDay(date);
        Criteria crit = createCriteria()
                    .add(between("date", start, DateBuilder.endDay(date)))
                    .add(eq("buyer", buyer));

        if (tracker == null)
            crit.add(isNull("tracker"));
        else
            crit.add(eq("tracker", tracker));

        Object stat = crit.uniqueResult();
        if (stat != null)
            return (StatisticReferral) stat;
        return new StatisticReferral.Builder(start,buyer,tracker).build();
    }

    @Override
    public List<ReportByDay> listByDate(Buyer buyer, PaginationFilter dbFilter, DateFilter date, String tracker, String sort) {
        Criteria crit = createCriteria()
                    .add(eq("buyer", buyer))
                    .add(between("date",date.getFrom(), date.getTo()))
                .setProjection(Projections.projectionList()
                                .add(Projections.property("date"), "date")
                                .add(Projections.sum("regAmount"), "registrationAmount")
                                .add(Projections.sum("clickLinkAmount"), "clickLinkAmount")
                                .add(Projections.sum("enterCodeAmount"), "enterCodeAmount")
                                .add(Projections.sum("sailAmount"), "sailAmount")
                                .add(Projections.sum("profit"), "profit")
                                .add(groupProperty("date")))
                        .setResultTransformer(
                                        Transformers.aliasToBean(ReportByDay.class));
        if (!"".equals(tracker))
            crit.add(eq("tracker",tracker));
        addOrder(crit,sort);
        addPagination(crit, dbFilter);
        return crit.list();
    }


    @Override
    public int count(Buyer buyer, DateFilter date, String tracker) {
        Criteria crit = createCriteria()
                    .add(between("date", date.getFrom(), date.getTo()))
                    .add(eq("buyer", buyer))
            .setProjection(projectionList()
                .add(Projections.countDistinct("date")));

        if (!"".equals(tracker))
            crit.add(eq("tracker", tracker));

        Object count = crit.uniqueResult();
        return count != null ? asInt(count) : 0;
    }
}
