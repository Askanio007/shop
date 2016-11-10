package dao;

import java.util.List;

import entity.Discount;
import entity.Product;

public interface DiscountDAO extends GeneralDAO<Discount> {

	List<Discount> getActivePrivate();
	
	Discount getGeneral();
	
	List<Discount> getActivePrivateByBuyerId(Long id);
	
	Discount getPrivate(Product product,Long id);
}
