package dao;

import java.util.Date;
import java.util.List;

import entity.Buyer;
import entity.BuyerInfo;

public interface BuyerDAO extends GeneralDAO<Buyer>{
	
	Buyer findByName(String name);
	
	String getAvaPathById(Long buyerId);

	Buyer getBuyerByReferCode(String code);

	List<Buyer> getActiveByDate(Date date);


}
