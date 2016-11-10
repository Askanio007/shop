package service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.SoldProductDAO;
import entity.Product;
import entity.SoldProduct;
import utils.PaginationFilter;

@Service
public class SoldProductService {

	@Autowired
	@Qualifier("soldProductDao")
	private SoldProductDAO soldProduct;


	public void initialize(SoldProduct sProduct){
			Hibernate.initialize(sProduct.getSail());
			Hibernate.initialize(sProduct.getSail().getBuyers());
	}
	
	public void initialize(List<SoldProduct> list){
		for (SoldProduct product : list) {
			initialize(product);
		}
	}

	@Transactional
	public List<SoldProduct> list(Product product, PaginationFilter dbPagination) {
		List<SoldProduct> list = soldProduct.list(product,dbPagination);
		initialize(list);
		return list;
	}

	@Transactional
	public Integer count(Product product) {
		return soldProduct.count(product);
	}

}
