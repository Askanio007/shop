package dao;

import java.util.Date;
import java.util.List;

import entity.Buyer;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import entity.Sail;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.SortParameterParser;

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

	private static final String COMPLETE_SAIL_BY_DATE =
			"select sail " +
					"from Sail sail inner join " +
						"sail.buyers buyer " +
					"where " +
						"buyer.id = :id and " +
						"sail.state = 'COMPLETE' and " +
						"sail.dateChangeState between :date and :endDate";

	@Override
	public int countSailsByBuyer(Long buyerId) {
		return asInt(createQuery("select count(*) from Sail sail inner join sail.buyers as buy where buy.id = :buyerid")
					.setLong("buyerid", buyerId)
					.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sail> getAllSailByBuyer(PaginationFilter filter, Long buyerId) {
		Query q = createQuery("from Sail as sail inner join fetch sail.buyers as buy where buy.id = :buyerid")
				.setLong("buyerid", buyerId);
		return setPagination(q, filter).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sail> getAllSailByBuyer(Long buyerid) {
		return createQuery("from Sail as sail inner join fetch sail.buyers as buy where buy.id = :buyerid")
				.setLong("buyerid", buyerid)
				.list();
	}

	@Override
	public List<Sail> getOverDueSail(Long time) {
		Long newDate = new Date().getTime() - time*1000;
		return createQuery("from Sail where state = 'SENT' and dateChangeState < :date")
				.setTimestamp("date", new Date(newDate)).list();
	}

	@Override
	public List<Sail> completeSailByDay(Long buyerId, Date date) {
		return createQuery(COMPLETE_SAIL_BY_DATE)
				.setDate("date", date)
				.setTimestamp("endDate", endDay(date))
				.setLong("id", buyerId)
				.list();
	}

	@Override
	public List<Sail> completeSailByDate(Long buyerId, DateFilter dateSail) {
		return createQuery(COMPLETE_SAIL_BY_DATE)
				.setTimestamp("date", dateSail.getFrom())
				.setTimestamp("endDate", dateSail.getTo())
				.setLong("id", buyerId)
				.list();
	}

	@Override
	public List<Sail> completeSailByDateOrder(Long buyerId, PaginationFilter filter, DateFilter sailDate, String sort) {
		return setPagination(createQuery(COMPLETE_SAIL_BY_DATE +
				" order by " + SortParameterParser.getColumnName(sort) + " " + SortParameterParser.getTypeOrder(sort)), filter)
				.setTimestamp("date", sailDate.getFrom())
				.setTimestamp("endDate", sailDate.getTo())
				.setLong("id", buyerId)
				.list();
	}

	@Override
	public List<Sail> completeSailByDate(Long buyerId, PaginationFilter dbFilter, DateFilter dateSail) {
		return setPagination(createQuery(COMPLETE_SAIL_BY_DATE), dbFilter)
				.setTimestamp("date", dateSail.getFrom())
				.setTimestamp("endDate", dateSail.getTo())
				.setLong("id", buyerId)
				.list();
	}

	@Override
	public int countSailsByReferral(Long referId, DateFilter dateSail) {
		return asInt(createQuery(COUNT_COMPLETE_SAIL_BY_DATE)
				.setLong("id", referId)
				.setTimestamp("date", dateSail.getFrom())
				.setTimestamp("endDate", dateSail.getTo())
				.uniqueResult()
		);
	}
}
