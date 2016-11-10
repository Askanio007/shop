package dao;

import java.util.List;

import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import utils.PaginationFilter;

public interface TotalSoldProductDAO extends GeneralDAO<TotalSoldProduct>{
	
	TotalSoldProduct get(Long productId);
	
	int countAll(FilterTotalSoldProduct paramFilter);
	
	List<TotalSoldProduct> find(PaginationFilter filter, FilterTotalSoldProduct paramFilter, String sort);

}
