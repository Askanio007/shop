package service;

import java.util.*;

import javax.transaction.Transactional;

import models.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.SailDaoInterface;
import entity.Buyer;
import entity.Sail;
import entity.SoldProduct;
import utils.DateFilter;
import utils.PaginationFilter;
import utils.StateSail;
import view.SailView;

@Service
public class SailService {

	@Autowired
	@Qualifier("sailDaoHQL")
	private SailDaoInterface sailDao;

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private ClickStatisticService serviceClick;

	@Autowired
	private TotalSoldProductService serviceTotalSold;

	public void initialize(Collection<Sail> sails) {
		for (Sail s : sails) {
			initialize(s);
		}
	}

	public void initialize(Sail sail) {
		Hibernate.initialize(sail.getBuyers());
		Hibernate.initialize(sail.getProducts());
	}

	public Double getProfit(Collection <Sail> sails) {
		Double profit = 0.0;
		for (Sail s : sails) {
			profit += s.getTotalsum() * (s.getCashbackPercent() * 1.0 / 100);
		}
		return profit;
	}

	public List<SoldProduct> convertToSoldProduct(List<ProductBasket> products) {
		List<SoldProduct> soldProducts = new ArrayList<>();
		for (ProductBasket prod : products) {
			SoldProduct sold = new SoldProduct(prod);
			soldProducts.add(sold);
		}
		return soldProducts;
	}

	@Transactional
	public void addSail(Sail sail, Basket basket) {
		sail.setTotalsum(basket.cost());
		sail.setAmount(basket.countProducts());
		sail.setProducts(convertToSoldProduct(basket.getProducts()));
		sail.setStateWithDate(StateSail.getState(StateSail.State.SENT));
		addSail(sail);
	}

	@Transactional
	public void addUserSail(List<Buyer> buyers, Basket basket) {
		Sail sail = new Sail(buyers, convertToSoldProduct(basket.getProducts()), basket);
		for (Buyer buyer : buyers) {
			if (buyer.getRefId() != null)
				serviceClick.setSailStatistic(serviceBuyer.getBuyer(buyer.getRefId()));
		}
		addSail(sail);
	}

	@Transactional
	public void addGenericUserSail(List<Buyer> buyers, Basket basket, Date date) {
		Sail sail = new Sail(buyers, convertToSoldProduct(basket.getProducts()), basket);
		sail.setState(StateSail.getState(StateSail.State.COMPLETE));
		sail.setDate(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
		sail.setDateChangeState(c.getTime());
		for (Buyer buyer : buyers) {
			if (buyer.getRefId() != null)
				serviceClick.setSailStatistic(serviceBuyer.getBuyer(buyer.getRefId()), c.getTime());
		}
		addSail(sail);
	}

	@Transactional
	public void addUserSail(String nameBuyer, Basket basket) {
		Sail sail = new Sail(serviceBuyer.getBuyer(nameBuyer), convertToSoldProduct(basket.getProducts()), basket);
		addSail(sail);
	}

	public void addSail(Sail sail) {
		sailDao.add(sail);
	}

	@Transactional
	public List<Sail> listSail(PaginationFilter dbFilter) {
		List<Sail> sails = sailDao.find(dbFilter);
		initialize(sails);
		return sails;
	}

	@Transactional
	public List<SailView> listViewSail(PaginationFilter dbFilter) {
		List<Sail> sails = listSail(dbFilter);
		return SailView.convertSail(sails);
	}

	@Transactional
	public List<Sail> allSailByBuyer(PaginationFilter dbFilter, Long buyerid) {
		List<Sail> sails = sailDao.getAllSailByBuyer(dbFilter, buyerid);
		initialize(sails);
		return sails;
	}

	@Transactional
	public List<SailView> allViewSailByBuyer(PaginationFilter dbFilter, Long buyerid) {
		List<Sail> sails = sailDao.getAllSailByBuyer(dbFilter, buyerid);
		initialize(sails);
		return SailView.convertSail(sails);
	}

	@Transactional
	public Sail find(Long sailId) {
		Sail sail = sailDao.find(sailId);
		initialize(sail);
		return sail;
	}

	@Transactional
	public void sailComplete(Long sailId) {
		Sail s = find(sailId);
		s.setStateWithDate(StateSail.getState(StateSail.State.COMPLETE));
		serviceBuyer.calculateProfit(s);
		update(s);
		serviceTotalSold.addProduct(s.getProducts());
	}

	@Transactional
	public void sailConflict(Long sailId) {
		Sail sail = find(sailId);
		sail.setStateWithDate(StateSail.getState(StateSail.State.CONFLICT));
		update(sail);
	}

	@Transactional
	public void generateSail() {
		Random r = new Random();
		int countProduct = serviceProduct.countAllProducts();
		int countBuyer = serviceBuyer.getCountAllBuyers();
		List<ProductBasket> list = new ArrayList<>();
		for(int j = 0; j<500;j++)
		{
			int amountProduct = r.nextInt(5);
			Random c = new Random();
			for (int i = 1; i < amountProduct+1; i++) {
				list.add(new ProductBasket(serviceProduct.getProductByNumber(r.nextInt(countProduct)), c.nextInt(10)+1, (byte) 0));
			}
			Basket basket = new Basket(list);
			PaginationFilter filter = new PaginationFilter(r.nextInt(countBuyer), 1);
			List<Buyer> buyer = serviceBuyer.listBuyer(filter);
			Calendar calendar = new GregorianCalendar(2015, c.nextInt(11), c.nextInt(28));
			addGenericUserSail(buyer, basket, calendar.getTime());
			list.clear();
		}
	}

	@Transactional
	public List<Sail> listCompleteSailByDay(Long buyerId, Date date) {
		return sailDao.completeSailByDay(buyerId, date);
	}

	@Transactional
	public List<Sail> listCompleteSailByDate(Long buyerId,PaginationFilter pagination, DateFilter sailDate) {
		return sailDao.completeSailByDate(buyerId, pagination, sailDate);
	}

	@Transactional
	public List<Sail> listCompleteSailByDate(Long buyerId, DateFilter sailDate) {
		return sailDao.completeSailByDate(buyerId, sailDate);
	}
	
	@Transactional
	public List<Sail> listCompleteSailByDateOrder(Long buyerId, PaginationFilter pagination, DateFilter sailDate, String sort) {
		return sailDao.completeSailByDateOrder(buyerId, pagination, sailDate, sort);
	}

	@Transactional
	public void removeSail(Long id) {
		sailDao.delete(id);
	}

	@Transactional
	public int countAllSails() {
		return sailDao.countAll();
	}

	@Transactional
	public int countSailsByBuyer(Long buyerid) {
		return sailDao.countSailsByBuyer(buyerid);
	}

	@Transactional
	public void update(Sail sail) {
		sailDao.update(sail);
	}

	@Transactional
	public List<Sail> getOverDueSails(Long time) {
		return sailDao.getOverDueSail(time);
	}

	@Transactional
	public int countReferralSail(Long referalId, DateFilter dateSail) {
		return sailDao.countSailsByReferral(referalId,dateSail);
	}
}
