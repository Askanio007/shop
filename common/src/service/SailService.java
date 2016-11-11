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

// TODO: 16.10.2016 не Complete - Completed. Complete - завершать ::: исправил, принял к сведению
// listCompleteSailByDate - завершить список продаж по дате
// listCompletedSailByDate - список завершенных продаж по дате
@Service
public class SailService {

	@Autowired
	@Qualifier("sailDaoHQL")
	private SailDaoInterface sailDao;

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private StatisticReferralsService serviceStatistic;

	@Autowired
	private TotalSoldProductService serviceTotalSold;

	@Autowired
	private SoldProductService serviceSold;


	public List<Sail> initialize(List<Sail> sails) {
		for (Sail s : sails) {
			initialize(s);
		}
		return sails;
	}

	public Sail initialize(Sail sail) {
		Hibernate.initialize(sail.getBuyers());
		Hibernate.initialize(sail.getProducts());
		return sail;
	}

	public Double getProfit(Collection <Sail> sails) {
		Double profit = 0.0;
		for (Sail s : sails) {
			profit += serviceBuyer.profitFromReferralBySail(s, s.getCashbackPercent());
		}
		return profit;
	}

	@Transactional
	public void save(Sail sail, Basket basket) {
		sail.setTotalsum(basket.cost());
		sail.setAmount(basket.countProducts());
		sail.setProducts(serviceSold.convertToSoldProduct(basket.getProducts()));
		sail.setStateWithDate(StateSail.getState(StateSail.State.SENT));
		save(sail);
	}

	@Transactional
	public void save(List<Buyer> buyers, Basket basket) {
		Sail sail = new Sail(buyers, serviceSold.convertToSoldProduct(basket.getProducts()), basket);
		for (Buyer buyer : buyers) {
			if (buyer.getRefId() != null)
				serviceStatistic.saveSailStatistic(serviceBuyer.get(buyer.getRefId()), new Date());
		}
		save(sail);
	}

	@Transactional
	public void save(Buyer buyer, Basket basket) {
		buyer.setBalance(buyer.getBalance() - basket.cost());
		Sail sail = new Sail(buyer, serviceSold.convertToSoldProduct(basket.getProducts()), basket);
		try {
			serviceStatistic.saveSailStatistic(serviceBuyer.get(buyer.getRefId()), new Date());
			serviceBuyer.edit(buyer);
			save(sail);} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(Sail sail) {
		sailDao.save(sail);
	}

	@Transactional
	public List<Sail> list(PaginationFilter dbFilter) {
		return initialize(sailDao.find(dbFilter));
	}

	@Transactional
	public List<SailView> listViewSail(PaginationFilter dbFilter) {
		return SailView.convertSail(list(dbFilter));
	}

	@Transactional
	public List<Sail> allByBuyer(PaginationFilter dbFilter, Long buyerid) {
		return initialize(sailDao.getByBuyer(dbFilter, buyerid));
	}

	@Transactional
	public List<SailView> allViewSailByBuyer(PaginationFilter dbFilter, Long buyerid) {
		return SailView.convertSail(allByBuyer(dbFilter, buyerid));
	}

	@Transactional
	public Sail find(Long sailId) {
		return initialize(sailDao.find(sailId));
	}

	@Transactional
	public void sailComplete(Long sailId) {
		Sail s = find(sailId);
		s.setStateWithDate(StateSail.getState(StateSail.State.COMPLETE));
		serviceBuyer.calculateProfit(s);
		update(s);
		serviceTotalSold.add(s.getProducts());
	}

	@Transactional
	public void sailConflict(Long sailId) {
		Sail sail = find(sailId);
		sail.setStateWithDate(StateSail.getState(StateSail.State.CONFLICT));
		update(sail);
	}

	@Transactional
	public List<Sail> listCompletedByDate(Long buyerId, DateFilter sailDate) {
		return sailDao.completedByDate(buyerId, null, sailDate, null);
	}

	@Transactional
	public void remove(Long id) {
		sailDao.delete(id);
	}

	@Transactional
	public int countAll() {
		return sailDao.countAll();
	}

	@Transactional
	public int countByBuyer(Long buyerid) {
		return sailDao.countByBuyer(buyerid);
	}

	@Transactional
	public void update(Sail sail) {
		sailDao.update(sail);
	}

	@Transactional
	public List<Sail> getOverDueSails(Long time) {
		return sailDao.getOverDue(time);
	}

	@Transactional
	public int countByReferral(Long referalId, DateFilter dateSail) {
		return sailDao.countByReferral(referalId,dateSail);
	}
}
