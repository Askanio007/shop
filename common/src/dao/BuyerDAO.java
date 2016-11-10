package dao;

import java.util.List;

import entity.Buyer;
import entity.BuyerInfo;

public interface BuyerDAO extends GeneralDAO<Buyer>{
	
	Buyer findByName(String name);
	
	BuyerInfo findInfoById(Long buyerId);
	
	String getRole(int id);
	
	String getAvaPathById(Long buyerId);
	
	List<Buyer> getAllBySail(Long sailId);

	Buyer getBuyerByReferCode(String code);

}
