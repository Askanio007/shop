package dao;

import java.util.List;

import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import utils.PaginationFilter;

public interface TotalSoldProductDAO extends GeneralDAO<TotalSoldProduct>{
	
	TotalSoldProduct getProduct(Long productId);
	
	int countAll(FilterTotalSoldProduct paramFilter);
	
	List<TotalSoldProduct> findSortPrice(PaginationFilter filter, String orderBy);
	
	List<TotalSoldProduct> findSortAmount(PaginationFilter filter, String orderBy);
	
	List<TotalSoldProduct> findSortPrice(PaginationFilter filter, String orderBy, FilterTotalSoldProduct paramFilter);
	
	List<TotalSoldProduct> find(PaginationFilter filter, FilterTotalSoldProduct paramFilter);
	
	List<TotalSoldProduct> findSortAmount(PaginationFilter filter, String orderBy,FilterTotalSoldProduct paramFilter);
	

}
