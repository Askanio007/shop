package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Product;
import entity.SoldProduct;
import utils.PaginationFilter;

@Repository("soldProductDao")
public class SoldProductDAOImpl extends GeneralDAOImpl<SoldProduct> implements SoldProductDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<SoldProduct> listSoldProduct(Product product, PaginationFilter pagination) {
		return setPagination(createQuery("from SoldProduct where name = :name").setString("name", product.getName()) ,pagination).list(); 
	}

	@Override
	public Integer countSoldProduct(Product product) {
		return asInt(createQuery("select count(*) from SoldProduct where name = :name").setString("name", product.getName()).uniqueResult());
	}
	
	

}
