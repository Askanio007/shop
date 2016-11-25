package dao;

import java.util.List;

import entity.Message;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import entity.Buyer;
import utils.PaginationFilter;

@Repository("messageDao")
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {

	@SuppressWarnings("unchecked")
	public List<Message> getChat(PaginationFilter filter, Buyer buyer) {
		Criteria crit = messagesByBuyer(buyer);
		addPagination(crit, filter);
		return crit.list();
	}

	public int count(Buyer buyer) {
		Criteria crit = messagesByBuyer(buyer);
		return asInt(crit.setProjection(Projections.rowCount()).uniqueResult());
	}

	// Не было идей как назвать его
	private Criteria messagesByBuyer(Buyer buyer) {
		return 	createCriteria().createAlias("this.user", "user").createAlias("this.buyer", "buyer")
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("buyer.id", buyer.getId()))
						.add(Restrictions.isNull("buyer"))
				)
				.add(Restrictions.gt("buyer.dateReg", buyer.getDateReg()));
	}
}