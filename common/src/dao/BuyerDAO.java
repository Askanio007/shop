package dao;

import java.util.List;

import entity.Buyer;
import entity.BuyerInfo;

public interface BuyerDAO extends GeneralDAO<Buyer>{
	
	Buyer findByName(String name);
	
	BuyerInfo findInfoById(Long buyerId);
	
	String getAvaPathById(Long buyerId);

	Buyer getBuyerByReferCode(String code);

}
