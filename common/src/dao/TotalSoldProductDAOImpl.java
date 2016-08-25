package dao;

import java.util.*;

import org.springframework.stereotype.Repository;

import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import utils.PaginationFilter;

@Repository("totalProductDao")
public class TotalSoldProductDAOImpl extends GeneralDAOImpl<TotalSoldProduct>implements TotalSoldProductDAO {

	@Override
	public TotalSoldProduct getProduct(Long productId) {
		Object ob = createQuery("from TotalSoldProduct as sold where sold.product.id = :productId")
				.setLong("productId", productId).uniqueResult();
		return (TotalSoldProduct) ob;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> findSortPrice(PaginationFilter filter, String orderby) {
		return setPagination(createQuery("from TotalSoldProduct as total order by total.totalCost " + orderby), filter)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> findSortAmount(PaginationFilter filter, String orderby) {
		return setPagination(createQuery("from TotalSoldProduct as total order by total.totalAmount " + orderby),
				filter).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> findSortPrice(PaginationFilter filter, String orderBy,
			FilterTotalSoldProduct paramFilter) {
		String q = "from TotalSoldProduct as total ";
		if (paramFilter.haveParam()) {
			q = "from TotalSoldProduct where " + createHqlQuery(paramFilter.getParams()) + " order by totalCost "
					+ orderBy;
			return setPagination(createQuery(q), filter).list();
		}
		return findSortPrice(filter, orderBy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> find(PaginationFilter filter, FilterTotalSoldProduct paramFilter) {
		if (paramFilter.haveParam())
			return setPagination(createQuery("from TotalSoldProduct where " + createHqlQuery(paramFilter.getParams())),
					filter).list();
		return find(filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TotalSoldProduct> findSortAmount(PaginationFilter filter, String orderBy,
			FilterTotalSoldProduct paramFilter) {
		String q = "";
		if (paramFilter.haveParam()) {
			q = "from TotalSoldProduct where " + createHqlQuery(paramFilter.getParams()) + " order by totalAmount "
					+ orderBy;
			return setPagination(createQuery(q), filter).list();
		}
		return findSortAmount(filter, orderBy);
	}

	@Override
	public int countAll(FilterTotalSoldProduct paramFilter) {
		if (paramFilter.haveParam()) {
			return asInt(
					createQuery("select count(*) from TotalSoldProduct where " + createHqlQuery(paramFilter.getParams()))
							.uniqueResult());
		}
		return countAll();
	}

	public String createHqlQuery(Map<String, Object> params) {
		String q = "";
		if (params.isEmpty())
			return q;
		int i = 1;
		for (Map.Entry<String, Object> entry : params.entrySet()){
			if (i == 1)
				q = entry.getKey() + " = " + "'" + entry.getValue().toString() + "'";
			else
				q = q + " and " + entry.getKey() + " = " + "'" + entry.getValue().toString() + "'";
			i++;
		}
		return q;
	}

}
