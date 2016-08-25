package dao;

import java.util.List;

import entity.Product;
import entity.SoldProduct;
import utils.PaginationFilter;

public interface SoldProductDAO extends GeneralDAO<SoldProduct> {
	
	List<SoldProduct> listSoldProduct(Product product, PaginationFilter pagination);
	
	Integer countSoldProduct(Product product);

}
