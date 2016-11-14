package dao;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import utils.PaginationFilter;

import static org.hibernate.criterion.Restrictions.eq;

@Repository("totalProductDao")
public class TotalSoldProductDAOImpl extends GeneralDAOImpl<TotalSoldProduct>implements TotalSoldProductDAO {

	@Override
	public TotalSoldProduct get(Long productId) {
		Object ob = createQuery("from TotalSoldProduct as sold where sold.product.id = :productId")
				.setLong("productId", productId).uniqueResult();
		return (TotalSoldProduct) ob;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> find(PaginationFilter filter, FilterTotalSoldProduct paramFilter, String sort) {
		Criteria crit = createCriteria().createAlias("this.product", "product");
		addFilter(paramFilter.getParams(), crit);
		addOrder(crit, sort);
		addPagination(crit, filter);
		return crit.list();
	}

	@Override
	public int countAll(FilterTotalSoldProduct paramFilter) {
		if (!paramFilter.haveParam())
			return countAll();
		Criteria crit = createCriteria().createAlias("this.product", "product").setProjection(Projections.rowCount());
		addFilter(paramFilter.getParams(), crit);
		return asInt(crit.uniqueResult());
	}


	public void addFilter(Map<String, Object> params, Criteria crit) {
		if (params.isEmpty())
			return;

		for (Map.Entry<String, Object> entry : params.entrySet()){
			crit.add(eq(entry.getKey(),entry.getValue()));
		}
	}

}
