package dao;

import java.util.List;

import entity.Message;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import entity.Buyer;
import utils.PaginationFilter;

@Repository("chatDAO")
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {

	@SuppressWarnings("unchecked")
	public List<Message> getChat(PaginationFilter filter, Buyer buyer) {
		Query q = createQuery("from Message where ("
				+ "sender = :senderId or "
				+ "recipient = :recipientId or "
				+ "recipient is null ) and "
				+ "date > :dateReg order by date desc")
				.setLong("senderId", buyer.getId())
				.setLong("recipientId", buyer.getId())
				.setDate("dateReg", buyer.getDateReg());
		return addPagination(q, filter).list();
	}

	public int count(Buyer buyer) {
		Query q = createQuery("select count(*) from Message where ("
				+ "sender = :senderId or "
				+ "recipient = :recipientId or "
				+ "recipient is null"
				+ ") and date > :dateReg")
				.setLong("senderId", buyer.getId())
				.setLong("recipientId", buyer.getId())
				.setDate("dateReg", buyer.getDateReg());
		return asInt(q.uniqueResult());
		
	}
}