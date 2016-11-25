package dao;

import entity.Buyer;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import utils.DateBuilder;
import utils.StateSail;
import utils.DateFilter;
import org.springframework.stereotype.Repository;

import utils.PaginationFilter;

import java.util.Date;
import java.util.List;
import static org.hibernate.criterion.Restrictions.*;

@Repository("referralDao")
public class ReferralDAOImpl extends GeneralDAOImpl<Buyer> implements ReferralDAO {

    @Override
    public Buyer find(Long referId, PaginationFilter pagination, DateFilter sailDate, String sort) {
        Criteria crit = createCriteria()
                    .add(eq("sail.state", StateSail.COMPLETE))
                    .add(between("sail.dateChangeState",sailDate.getFrom(), sailDate.getTo()))
                    .add(eq("id", referId));
        associatedLeftJoin(crit, "this.sails", "sail");
        addPagination(crit, pagination);
        addOrder(crit, sort);
        return (Buyer)crit.uniqueResult();
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
    public List<Buyer> findByDateRegistration(Buyer buyer, PaginationFilter pagination, DateFilter sailDate, DateFilter date, String tracker, String sort) {
        Criteria crit = createCriteria()
                    .add(eq("refId", buyer.getId()))
                    .add(eq("sail.state", StateSail.COMPLETE))
                    .add(between("sail.dateChangeState",sailDate.getFrom(), sailDate.getTo()))
                    .add(between("dateReg", date.getFrom(), date.getTo()));
        if (!tracker.equals(""))
            crit.add(eq("tracker", tracker));
        associatedLeftJoin(crit, "this.sails", "sail");
        addOrder(crit, sort);
        addPagination(crit, pagination);
        return crit.list();
    }

    @Override
    public List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort) {
        Criteria crit = createCriteria()
                .add(eq("refId", buyerId))
                .add(
                        Restrictions.disjunction(
                                between("dateReg", day, DateBuilder.endDay(day)),
                                conjunction(
                                        between("sail.dateChangeState", day, DateBuilder.endDay(day)),
                                        eq("sail.state",StateSail.COMPLETE)
                                ))
                );
        if (!"".equals(tracker))
            crit.add(eq("tracker", tracker));
        associatedLeftJoin(crit, "this.sails", "sail");
        addOrder(crit, sort);
        addPagination(crit, pagination);
        return crit.list();
    }

    @Override
    public int countActiveByDay(Long buyerId, Date date, String tracker) {
        Criteria criteria = createCriteria()
                .add(eq("refId", buyerId))
                .createAlias("this.sails", "sail")
                .add(
                        Restrictions.disjunction(
                                between("dateReg", date, DateBuilder.endDay(date)),
                                conjunction(
                                        between("sail.dateChangeState", date, DateBuilder.endDay(date)),
                                        eq("sail.state", StateSail.COMPLETE)
                                ))
                )
                .setProjection(
                        Projections.projectionList()//вникнуть в ситуацию и варианты rowCount() countDistinct и тд
                                .add(Projections.countDistinct("id"))
                );
        if (tracker != null)
            criteria.add(eq("tracker", tracker));
        return criteria.uniqueResult() != null ? asInt(criteria.uniqueResult()) : 0;
    }




}
