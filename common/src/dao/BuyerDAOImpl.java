package dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Buyer;
import entity.BuyerInfo;

@Repository("buyerDAO")
public class BuyerDAOImpl extends GeneralDAOImpl<Buyer>implements BuyerDAO {

	@Override
	public Buyer findByName(String name) {
		return (Buyer) createQuery("from Buyer where name = :buyerName")
				.setString("buyerName", name)
				.uniqueResult();
	}

	@Override
	public BuyerInfo findInfoById(Long buyerId) {
		return (BuyerInfo) createQuery("from BuyerInfo where id = :id")
						.setLong("id", buyerId)
						.uniqueResult();
	}

	@Override
	public String getRole(int id) {
		return (String) createQuery("select role from Role where id = :id")
				.setLong("id", id)
				.uniqueResult();
	}

	@Override
	public String getAvaPathById(Long buyerId) {
		return (String) createQuery("select ava from BuyerInfo where id = :buyerId")
				.setLong("buyerId", buyerId)
				.uniqueResult();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Buyer> getAllBySail(Long sailId) {
		return createCriteria().createAlias("sails", "sail").add(eq("sail.id", sailId)).list();
	}


	@Override
	public Buyer getBuyerByReferCode(String code) {
		return (Buyer) createQuery("select b from Buyer b where b.refCode = :code").setString("code", code).uniqueResult();
	}
}
