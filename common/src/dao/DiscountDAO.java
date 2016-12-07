package dao;

import java.util.List;

import dto.ProductDto;
import entity.Discount;
import entity.Product;

public interface DiscountDAO extends GeneralDAO<Discount> {

	List<Discount> getActivePrivate();
	
	Discount getGeneral();
	
	List<Discount> getActivePrivateByBuyerId(Long id);
	
	Discount getPrivate(long productId, long buyerId);
}
