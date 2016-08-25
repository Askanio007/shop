package dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import entity.Buyer;
import entity.Chat;
import utils.PaginationFilter;

@Repository("chatDAO")
public class ChatDAOImpl extends GeneralDAOImpl<Chat> implements ChatDAO {

	@SuppressWarnings("unchecked")
	public List<Chat> getChat(PaginationFilter filter, Buyer buyer) {
		Query q = createQuery("from Chat where ("
				+ "sender = :senderId or "
				+ "recipient = :recipientId or "
				+ "recipient is null ) and "
				+ "date > :dateReg order by date desc")
				.setLong("senderId", buyer.getId())
				.setLong("recipientId", buyer.getId())
				.setDate("dateReg", buyer.getDateReg());
		return setPagination(q, filter).list();
	}

	public int count(Buyer buyer) {
		Query q = createQuery("select count(*) from Chat where ("
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