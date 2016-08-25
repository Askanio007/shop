package dao;

import java.util.List;

import entity.Discount;
import entity.Product;

public interface DiscountDAO extends GeneralDAO<Discount> {

	List<Discount> getActivePrivateDisc();
	
	Discount getGeneralDisc();
	
	List<Discount> getActivePrivateDiscByBuyerId(Long id);
	
	Discount getPrivateDiscount(Product product,Long id);
}
