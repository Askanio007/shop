package service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import models.ProductBasket;
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

	@Transactional
	public List<SoldProduct> list(Product product, PaginationFilter dbPagination) {
		List<SoldProduct> list = soldProduct.list(product,dbPagination);
		return list;
	}

	@Transactional
	public Integer count(Product product) {
		return soldProduct.count(product);
	}


	public List<SoldProduct> convertToSoldProduct(List<ProductBasket> products) {
		List<SoldProduct> soldProducts = new ArrayList<>();
		for (ProductBasket prod : products) {
			soldProducts.add(new SoldProduct(prod));
		}
		return soldProducts;
	}

}
