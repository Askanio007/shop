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
	public void addProduct(Collection<SoldProduct> soldProducts) {
		for (SoldProduct sProduct : soldProducts) {
			Product prod = serviceProduct.getProduct(sProduct.getProductId());
			TotalSoldProduct totalProd = getProduct(prod.getId());
			if (totalProd == null) {
				totalProductDao.add(new TotalSoldProduct(sProduct, prod));
				return;
			}
			totalProd.addData(sProduct);
			totalProductDao.update(totalProd);
		}
	}

	@Transactional
	public List<TotalSoldProduct> listTotalSoldProduct(PaginationFilter pagination, String sort, FilterTotalSoldProduct paramFilter) {
		List<TotalSoldProduct> list = new ArrayList<>();
		if (sort != null) {
			switch (sort) {
			case "priceUp":
				list = totalProductDao.findSortPrice(pagination, "desc",paramFilter);
				break;
			case "amountUp":
				list = totalProductDao.findSortAmount(pagination, "desc",paramFilter);
				break;
			case "priceDown":
				list = totalProductDao.findSortPrice(pagination, "asc",paramFilter);
				break;
			case "amountDown":
				list = totalProductDao.findSortAmount(pagination, "asc",paramFilter);
				break;
			case "":
				list = totalProductDao.find(pagination,paramFilter);
				break;
			}
		}
		else
			list = totalProductDao.find(pagination,paramFilter);
		
		for (TotalSoldProduct t : list) {
			Hibernate.initialize(t.getProduct());
		}
		return list;
	}

	@Transactional
	public Integer countTotalSoldProduct(FilterTotalSoldProduct paramFilter) {
		return totalProductDao.countAll(paramFilter);
	}

	@Transactional
	public TotalSoldProduct getProduct(Long productId) {
		return totalProductDao.getProduct(productId);
	}

}
