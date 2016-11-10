package dao;

import java.util.List;

import entity.Product;
import entity.SoldProduct;
import utils.PaginationFilter;

// TODO: 16.10.2016 в последний раз - без необходимости не надо в методы дао которые типизированы конкретным классом,::: исправил здесь и в других местах
// и в названии собержат этот класс, пихать название класса еще и в каждый метод
public interface SoldProductDAO extends GeneralDAO<SoldProduct> {
	
	List<SoldProduct> list(Product product, PaginationFilter pagination);
	
	Integer count(Product product);

}
