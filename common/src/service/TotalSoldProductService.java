package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.TotalSoldProductDAO;
import entity.Product;
import entity.SoldProduct;
import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import utils.PaginationFilter;

@Service
public class TotalSoldProductService {

	@Autowired
	@Qualifier("totalProductDao")
	private TotalSoldProductDAO totalProductDao;

	@Autowired
	private ProductService serviceProduct;

	@Transactional
	public void add(Collection<SoldProduct> soldProducts) {
		for (SoldProduct sProduct : soldProducts) {
			Product prod = serviceProduct.get(sProduct.getProductId());
			TotalSoldProduct totalProd = get(prod.getId());
			if (totalProd == null) {
				totalProductDao.save(new TotalSoldProduct(sProduct, prod));
				return;
			}
			totalProd.addData(sProduct);
			totalProductDao.update(totalProd);
		}
	}

	@Transactional
	public List<TotalSoldProduct> list(PaginationFilter pagination, String sort, FilterTotalSoldProduct paramFilter) {
		List<TotalSoldProduct> list = totalProductDao.find(pagination,paramFilter,sort);
		for (TotalSoldProduct t : list) {
			Hibernate.initialize(t.getProduct());
		}
		return list;
	}

	@Transactional
	public Integer count(FilterTotalSoldProduct paramFilter) {
		return totalProductDao.countAll(paramFilter);
	}

	@Transactional
	public TotalSoldProduct get(Long productId) {
		return totalProductDao.get(productId);
	}

}
