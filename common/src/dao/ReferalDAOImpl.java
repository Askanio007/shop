package dao;

import entity.Buyer;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import utils.DateFilter;
import org.springframework.stereotype.Repository;

import utils.PaginationFilter;
import utils.SortParameterParser;
import utils.StateSail;

import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Projections.groupProperty;
import static org.hibernate.criterion.Restrictions.*;

@Repository("referalDao")
public class ReferalDAOImpl  extends GeneralDAOImpl<Buyer> implements ReferalDAO {

    private static final String COUNT_ACTIVE_REFERRALS_BY_DAY =
            "select count(buyer) " +
                    "from Buyer as buyer left join " +
                    "buyer.sails as sail" +
                    " where " +
                    "buyer.refId = :id and " +
                    "(buyer.dateReg between :day and :endDay or " +
                    "sail.dateChangeState between :day and :endDay and " +
                    "sail.state = :state) " +
                    "group by buyer.id";

    private static final String COUNT_ACTIVE_REFERRALS_BY_DAY_WITH_TRACKER =
            "select count(buyer) " +
                    "from Buyer as buyer left join " +
                    "buyer.sails as sail" +
                    " where " +
                    "buyer.refId = :id and " +
                    "buyer.tracker = :tracker and " +
                    "(buyer.dateReg between :day and :endDay or " +
                    "sail.dateChangeState between :day and :endDay and " +
                    "sail.state = :state) " +
                    "group by buyer.id";

    private static final String COUNT_REFERRALS_BY_DATE_REGISTATION =
            "select count(*) from Buyer buyer " +
                    "where " +
                    "buyer.refId = :id and " +
                    "buyer.dateReg between :from and :to";

    private static final String REFERRALS_BY_DATE_REGISTATION =
            "select buyer " +
                    "from Buyer as buyer inner join " +
                    "buyer.info as info " +
                    "where " +
                    "buyer.refId = :id and " +
                    "buyer.dateReg between :from and :to";

    private static final String ACTIVE_REFERRALS_BY_DATE =
            "select buyer " +
                    "from Buyer as buyer left join " +
                        "buyer.sails as sail" +
                    " where " +
                        "buyer.refId = :id and " +
                        "(buyer.dateReg between :day and :endDay or " +
                        "sail.dateChangeState between :day and :endDay and " +
                        "sail.state = :state) " +
                    "group by buyer.id";

    private static final String ACTIVE_REFERRALS_BY_DATE_WITH_TRACKER =
            "select buyer " +
                    "from Buyer as buyer left join " +
                        "buyer.sails as sail " +
                    "where " +
                        "buyer.refId = :id and " +
                        "buyer.tracker = :tracker and " +
                        "(buyer.dateReg between :day and :endDay or " +
                        "sail.dateChangeState between :day and :endDay and " +
                        "sail.state = :state) " +
                    "group by buyer.id";


    @Override
    public List<Buyer> getById(Long buyerId) {
        return createQuery("select buyer from Buyer as buyer where buyer.refId = :id")
                .setLong("id", buyerId)
                .list();
    }

    @Override
    public List<Buyer> getActiveByBuyer(Buyer buyer, Date date) {
        Criteria crit = createCriteria()
                .createAlias("this.sails", "sail")
                    .add(eq("refId", buyer.getId()))
                    .add(eq("sail.state", StateSail.getState(StateSail.State.COMPLETE)))
                    .add(between("sail.dateChangeState", getDateWithoutTime(date), endDay(date)));
        return crit.list();
    }

    @Override
    public int count(Buyer buyer, DateFilter date, String tracker) {
        Criteria crit = createCriteria()
                .add(eq("refId", buyer.getId()))
                .add(between("dateReg", date.getFrom(), date.getTo()));
        if (!tracker.equals(""))
            crit.add(eq("tracker", tracker));
        crit.setProjection(Projections.rowCount());
        Object count = crit.uniqueResult();
        return count != null ? asInt(count) : 0;
    }

    @Override
    public List<Buyer> findByDateRegistration(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker, String sort) {
        Criteria crit = createCriteria()
                .add(eq("refId", buyerId))
                .add(between("dateReg", date.getFrom(), date.getTo()));
        if (!tracker.equals(""))
            crit.add(eq("tracker", tracker));
        addOrder(crit, sort);
        addPagination(crit, pagination);
        return crit.list();
    }

    @Override
    public List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort) {
        Criteria crit = createCriteria().add(eq("refId", buyerId));
        if (!tracker.equals(""))
            crit.add(eq("tracker", tracker));
        crit.createAlias("this.sails", "sail")
                .add(
                        Restrictions.disjunction(
                                between("dateReg", day, endDay(day)),
                                conjunction(
                                        between("sail.dateChangeState", day, endDay(day)),
                                        eq("sail.state", StateSail.getState(StateSail.State.COMPLETE))
                                ))
                )
                .setProjection(
                        Projections.projectionList()
                                .add(groupProperty("id"))
                );
        addOrder(crit, sort);
        addPagination(crit, pagination);
        return crit.list();
    }

    // TODO: 16.10.2016 не проверял, но как-то так это должно работать
    @Override
    public int countActiveByDay(Long buyerId, Date date, String tracker) {
        Criteria criteria = createCriteria()
                .add(eq("refId", buyerId))
                .createAlias("this.sails", "sail")
                .add(
                        Restrictions.disjunction(
                                between("dateReg", date, endDay(date)),
                                conjunction(
                                        between("sail.dateChangeState", date, endDay(date)),
                                        eq("sail.state", StateSail.getState(StateSail.State.COMPLETE))
                                ))
                )
                .setProjection(
                        Projections.projectionList()//вникнуть в ситуацию и варианты rowCount() countDistinct и тд
                                .add(Projections.count("id"))
                                .add(groupProperty("id"))
                );
        if (tracker != null)
            criteria.add(eq("tracker", tracker));
        return criteria.uniqueResult() != null ? asInt(criteria.uniqueResult()) : 0;
    }




}
