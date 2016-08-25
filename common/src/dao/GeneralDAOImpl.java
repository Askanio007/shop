package dao;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import utils.PaginationFilter;

public class GeneralDAOImpl<T> implements GeneralDAO<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GeneralDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public void add(T t) {
		session().save(t);
	}

	@Override
	public void update(T t) {
		session().merge(t);
	}

	@Override
	public void delete(Long id) {
		Object ob = session().load(entityClass, id);
		delete(ob);
	}

	@Override
	public void delete(Object ob) {
		session().delete(ob);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(Long objectid) {
		return (T) session().get(entityClass, objectid);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(PaginationFilter filter) {
		return createCriteria()
				.setFirstResult(filter.getOffset())
				.setMaxResults(filter.getLimit())
				.list();		
	}

	@SuppressWarnings("unchecked")
	public List<T> find() {
		return createCriteria().addOrder(Order.asc("id")).list();
	}

	@Override
	public int countAll() {
		return asInt(createCriteria()
				.setProjection(Projections.rowCount())
				.uniqueResult()
		);
	}

	protected int asInt(Object numberObject) {
		return ((Number) numberObject).intValue();
	}

	protected Query createQuery(String query) {
		return session().createQuery(query);
	}

	protected Query setPagination(Query q, PaginationFilter filter) {
		return q.setFirstResult(filter.getOffset()).setMaxResults(filter.getLimit());
	}

	protected Session session() {
		return sessionFactory.getCurrentSession();
	}

	 protected Criteria createCriteria() { return session().createCriteria(entityClass); }

	protected Date endDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}
}
