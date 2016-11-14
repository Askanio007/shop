package dao;

import entity.Buyer;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import utils.DateFilter;
import org.springframework.stereotype.Repository;

import utils.PaginationFilter;
import utils.StateSail;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Projections.groupProperty;
import static org.hibernate.criterion.Restrictions.*;

@Repository("referralDao")
public class ReferralDAOImpl extends GeneralDAOImpl<Buyer> implements ReferralDAO {

    @Override
    public Buyer find(Long referId, PaginationFilter pagination, DateFilter sailDate, String sort) {
        Criteria crit = createCriteria()
                    .add(eq("sail.state", StateSail.getState(StateSail.State.COMPLETE)))
                    .add(between("sail.dateChangeState",sailDate.getFrom(), sailDate.getTo()))
                    .add(eq("id", referId));
        getAssociatedObjectLeftJoin(crit, "this.sails", "sail");
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
                    .add(eq("sail.state", StateSail.getState(StateSail.State.COMPLETE)))
                    .add(between("sail.dateChangeState",sailDate.getFrom(), sailDate.getTo()))
                    .add(between("dateReg", date.getFrom(), date.getTo()));
        if (!tracker.equals(""))
            crit.add(eq("tracker", tracker));
        getAssociatedObjectLeftJoin(crit, "this.sails", "sail");
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
                                between("dateReg", day, endDay(day)),
                                conjunction(
                                        between("sail.dateChangeState", day, endDay(day)),
                                        eq("sail.state", StateSail.getState(StateSail.State.COMPLETE))
                                ))
                );
              /*  .setProjection(
                        Projections.projectionList()
                                .add(groupProperty("id"))
                );*/
        if (!"".equals(tracker))
            crit.add(eq("tracker", tracker));

        getAssociatedObjectLeftJoin(crit, "this.sails", "sail");
        getAssociatedObjectLeftJoin(crit, "sail.products", "product");
        addOrder(crit, sort);
        addPagination(crit, pagination);
        return crit.list();
    }

    // TODO: 16.10.2016 не проверял, но как-то так это должно работать ::: кое-что исправил, вроде работает правильно. С критерией немного разобрался, но с проекциями пока проблемы, изучаю
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
                                .add(Projections.countDistinct("id"))
                );
        if (tracker != null)
            criteria.add(eq("tracker", tracker));
        return criteria.uniqueResult() != null ? asInt(criteria.uniqueResult()) : 0;
    }




}
