package dao;

import entity.Buyer;
import utils.DateFilter;
import org.springframework.stereotype.Repository;

import utils.PaginationFilter;
import utils.SortParameterParser;
import utils.StateSail;

import java.util.Date;
import java.util.List;

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
    public List<Buyer> getReferalsById(Long buyerId) {
        return createQuery("select buyer from Buyer as buyer where buyer.refId = :id")
                .setLong("id", buyerId)
                .list();
    }

    @Override
    public List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day) {
        return setPagination(createQuery(ACTIVE_REFERRALS_BY_DATE), pagination)
                .setDate("day", day)
                .setTimestamp("endDay", endDay(day))
                .setLong("id", buyerId)
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .list();

    }

    @Override
    public List<Buyer> findActiveByDayWithTracker(Long buyerId, PaginationFilter pagination, Date day, String tracker) {
        return setPagination(createQuery(ACTIVE_REFERRALS_BY_DATE_WITH_TRACKER), pagination)
                .setDate("day", day)
                .setString("tracker", tracker)
                .setTimestamp("endDay", endDay(day))
                .setLong("id", buyerId)
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .list();

    }

    @Override
    public List<Buyer> findActiveByDayOrder(Long buyerId, PaginationFilter pagination, Date day, String sort) {
        return setPagination(createQuery(ACTIVE_REFERRALS_BY_DATE +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), pagination)
                .setDate("day", day)
                .setTimestamp("endDay", endDay(day))
                .setLong("id", buyerId)
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .list();

    }

    @Override
    public List<Buyer> findActiveByDayWithTrackerOrder(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort) {
        return setPagination(createQuery(ACTIVE_REFERRALS_BY_DATE_WITH_TRACKER +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), pagination)
                .setDate("day", day)
                .setString("tracker", tracker)
                .setTimestamp("endDay", endDay(day))
                .setLong("id", buyerId)
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .list();

    }

    @Override
    public List<Buyer> findByDateRegistration(Long buyerId, PaginationFilter pagination, DateFilter date) {
        return setPagination(createQuery(REFERRALS_BY_DATE_REGISTATION), pagination)
                .setLong("id", buyerId)
                .setTimestamp("from",date.getFrom())
                .setTimestamp("to", date.getTo())
                .list();
    }

    @Override
    public List<Buyer> findByDateRegistrationWithTracker(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker) {
        return  setPagination(createQuery(REFERRALS_BY_DATE_REGISTATION +
                " and tracker = :tracker"), pagination)
                .setLong("id", buyerId)
                .setString("tracker", tracker)
                .setTimestamp("from",date.getFrom())
                .setTimestamp("to", date.getTo())
                .list();
    }

    @Override
    public List<Buyer> findByDateRegistrationOrder(Long buyerId, PaginationFilter pagination, DateFilter date, String sort) {
        return setPagination(createQuery(REFERRALS_BY_DATE_REGISTATION +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), pagination)
                .setLong("id", buyerId)
                .setTimestamp("from",date.getFrom())
                .setTimestamp("to", date.getTo())
                .list();
    }

    @Override
    public List<Buyer> findByDateRegistrationWithTrackerOrder(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker, String sort) {
        return setPagination(createQuery(REFERRALS_BY_DATE_REGISTATION +
                " and buyer.tracker = :tracker" +
                " order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), pagination)
                .setLong("id", buyerId)
                .setString("tracker", tracker)
                .setTimestamp("from",date.getFrom())
                .setTimestamp("to", date.getTo())
                .list();
    }

    @Override
    public int countReferrals(Buyer buyer, DateFilter date) {
        Object count = createQuery(COUNT_REFERRALS_BY_DATE_REGISTATION)
                .setLong("id", buyer.getId())
                .setTimestamp("from", date.getFrom())
                .setTimestamp("to", date.getTo())
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }

    @Override
    public int countReferrals(Buyer buyer, DateFilter date, String tracker) {
        Object count = createQuery(COUNT_REFERRALS_BY_DATE_REGISTATION +
                " and buyer.tracker = :tracker")
                .setString("tracker", tracker)
                .setLong("id", buyer.getId())
                .setTimestamp("from", date.getFrom())
                .setTimestamp("to", date.getTo())
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }

    @Override
    public int countActiveReferralByDay(Long buyerId, Date date) {
        Object count = createQuery(COUNT_ACTIVE_REFERRALS_BY_DAY )
                .setLong("id", buyerId)
                .setDate("day", date)
                .setTimestamp("endDay", endDay(date))
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }

    @Override
    public int countActiveReferralByDay(Long buyerId, Date date, String tracker) {
        Object count = createQuery(COUNT_ACTIVE_REFERRALS_BY_DAY_WITH_TRACKER)
                .setString("tracker", tracker)
                .setLong("id", buyerId)
                .setDate("day", date)
                .setTimestamp("endDay", endDay(date))
                .setString("state", StateSail.getState(StateSail.State.COMPLETE))
                .uniqueResult();
        return count != null ? asInt(count) : 0;
    }
}
