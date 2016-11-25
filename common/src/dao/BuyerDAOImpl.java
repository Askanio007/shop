package dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Buyer;
import utils.DateBuilder;
import utils.StateSail;
import view.DateConverter;

import static utils.StateSail.*;

@Repository("buyerDAO")
public class BuyerDAOImpl extends GeneralDAOImpl<Buyer>implements BuyerDAO {

	@Override
	public Buyer findByName(String name) {
		return (Buyer) createQuery("from Buyer where name = :buyerName")
				.setString("buyerName", name)
				.uniqueResult();
	}

	@Override
	public String getAvaPathById(Long buyerId) {
		return (String) createQuery("select ava from BuyerInfo where id = :buyerId")
				.setLong("buyerId", buyerId)
				.uniqueResult();
	}

	@Override
	public Buyer getBuyerByReferCode(String code) {
		return (Buyer) createQuery("select b from Buyer b where b.refCode = :code").setString("code", code).uniqueResult();
	}

	@Override
	public List<Buyer> getActiveByDate(Date date) {
		return createQuery("from Buyer as buyer left join fetch buyer.sails as sail where sail.dateChangeState between :dateFrom and :dateTo and sail.state = :state")
				.setTimestamp("dateFrom", DateBuilder.startDay(date))
				.setTimestamp("dateTo", DateBuilder.endDay(date))
				.setParameter("state", StateSail.COMPLETE)
				.list();
	}
}
