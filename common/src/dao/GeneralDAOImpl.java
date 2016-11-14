package dao;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;

import utils.PaginationFilter;
import utils.SortParameterParser;

import static org.hibernate.criterion.Restrictions.eq;

public class GeneralDAOImpl<T> implements GeneralDAO<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GeneralDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	// TODO: 16.10.2016 add плохой вариант названия ::: исправил на save
	//вообще вот на это погляди - session().saveOrUpdate() ----------------
	@Override
	public void save(T t) {
		session().saveOrUpdate(t);
	}

	@Override
	public void update(T t) {
		session().merge(t);
	}
	//----------------------------------------------------------------------

	@Override
	public void delete(Long id) {
		delete(session().load(entityClass, id));
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

	protected Query addPagination(Query q, PaginationFilter filter) {
		return q.setFirstResult(filter.getOffset()).setMaxResults(filter.getLimit());
	}

	protected Session session() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria createCriteria() { return session().createCriteria(entityClass); }


	protected void addOrder(Criteria crit, String sort) {
		if(sort != null && !"".equals(sort)) {
			if (SortParameterParser.getTypeOrder(sort).equals("desc"))
				crit.addOrder(Order.desc(SortParameterParser.getColumnName(sort)));
			else
				crit.addOrder(Order.asc(SortParameterParser.getColumnName(sort)));
		}
	}

	protected void addPagination(Criteria crit, PaginationFilter filter) {
		crit.setFirstResult(filter.getOffset()).setMaxResults(filter.getLimit());
	}

	protected void associatedLeftJoin(Criteria crit, String associationPath, String alias) {
			crit
				.setFetchMode(associationPath, FetchMode.JOIN)
				.createAlias(associationPath, alias, JoinType.LEFT_OUTER_JOIN);
	}

	public static Date endDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
