package service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import dto.ProductDto;
import dto.SoldProductDto;
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
	public List<SoldProduct> list(ProductDto product, PaginationFilter dbPagination) {
		return soldProduct.list(product.getName(),dbPagination);
	}

	@Transactional
	public List<SoldProductDto> listDto(ProductDto product, PaginationFilter dbPagination) {
		return SoldProductDto.convertToDto(list(product,dbPagination));
	}

	@Transactional
	public Integer count(ProductDto product) {
		return soldProduct.count(product.getName());
	}


	public List<SoldProduct> convertToSoldProduct(List<ProductBasket> products) {
		List<SoldProduct> soldProducts = new ArrayList<>();
		products.stream().forEach((product) -> soldProducts.add(new SoldProduct((product))));
		return soldProducts;
	}

}
