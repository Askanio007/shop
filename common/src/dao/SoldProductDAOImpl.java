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
	public List<SoldProduct> list(String productName, PaginationFilter pagination) {
		return addPagination(createQuery("from SoldProduct where name = :name").setString("name", productName) ,pagination).list();
	}

	@Override
	public Integer count(String productName) {
		return asInt(createQuery("select count(*) from SoldProduct where name = :name").setString("name", productName).uniqueResult());
	}
	
	

}
