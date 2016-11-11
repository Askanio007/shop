package dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import entity.Sail;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.StateSail;

import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.sqlRestriction;

@Repository("sailDaoHQL")
public class SailDaoHQLImpl extends GeneralDAOImpl<Sail>implements SailDaoInterface {

	private static final String COUNT_COMPLETE_SAIL_BY_DATE =
			"select count(sail) " +
					"from Sail sail inner join " +
					"sail.buyers buyer " +
					"where " +
					"buyer.id = :id and " +
					"sail.state = 'COMPLETE' and " +
					"sail.dateChangeState between :date and :endDate";

	@Override
	public int countByBuyer(Long buyerId) {
		return asInt(createQuery("select count(*) from Sail sail inner join sail.buyers as buy where buy.id = :buyerid")
					.setLong("buyerid", buyerId)
					.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sail> getByBuyer(PaginationFilter filter, Long buyerId) {
		Query q = createQuery("from Sail as sail inner join fetch sail.buyers as buy where buy.id = :buyerid")
				.setLong("buyerid", buyerId);
		return addPagination(q, filter).list();
	}

	@Override
	public List<Sail> getOverDue(Long time) {
		Long newDate = new Date().getTime() - time*1000;
		return createQuery("from Sail where state = 'SENT' and dateChangeState < :date")
				.setTimestamp("date", new Date(newDate)).list();
	}

	@Override
	public List<Sail> completedByDate(Long buyerId, PaginationFilter filter, DateFilter sailDate, String sort) {
		Criteria crit = createCriteria()
				.createAlias("this.buyers", "buyer")
				.add(eq("buyer.id", buyerId))
				.add(eq("state", StateSail.getState(StateSail.State.COMPLETE)))
				.add(between("dateChangeState",sailDate.getFrom(), sailDate.getTo()));
		if (filter != null)
			addPagination(crit, filter);
		if (sort != null)
			addOrder(crit, sort);
		return crit.list();
	}

	@Override
	public int countByReferral(Long referId, DateFilter dateSail) {
		return asInt(createQuery(COUNT_COMPLETE_SAIL_BY_DATE)
				.setLong("id", referId)
				.setTimestamp("date", dateSail.getFrom())
				.setTimestamp("endDate", dateSail.getTo())
				.uniqueResult()
		);
	}
}
