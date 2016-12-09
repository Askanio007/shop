package dao;

import java.util.List;

import dto.BuyerDto;
import entity.Message;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import entity.Buyer;
import utils.PaginationFilter;

import static org.hibernate.criterion.Restrictions.eq;

@Repository("messageDao")
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getChat(PaginationFilter filter, BuyerDto buyer) {
		Criteria crit = messagesByBuyer(buyer);
		addPagination(crit, filter);
		return crit.list();
	}

	@Override
	public int count(BuyerDto buyer) {
		Criteria crit = messagesByBuyer(buyer);
		return asInt(crit.setProjection(Projections.rowCount()).uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> notReadMessages(BuyerDto buyer) {
		return createCriteria().createAlias("this.buyer", "buyer").createAlias("this.user", "user")
				.add(Restrictions.eq("buyer.id", buyer.getId()))
				.add(Restrictions.isNull("user"))
				.list();
	}


	// Не было идей как назвать его
	private Criteria messagesByBuyer(BuyerDto buyer) {
		return 	createCriteria().createAlias("this.buyer", "buyer")
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("buyer.id", buyer.getId()))
						.add(Restrictions.isNull("buyer"))
				)
				.add(Restrictions.le("buyer.dateReg", buyer.getDateReg()));
	}


}