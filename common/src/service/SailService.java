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
import utils.DateFilter;
import utils.PaginationFilter;
import utils.StateSail.*;
import view.SailView;
import static utils.StateSail.getState;

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


	public List<Sail> initialize(Collection<Sail> sails) {
		for (Sail s : sails) {
			initialize(s);
		}
		return (List<Sail>) sails;
	}

	// TODO: Kirill а тебе действительно нужно явно инициализировать коллекции, вроде бы во всех этих
	// случаях прокси что заменяют ленивые коллекции до первого вызова вполне должны справится
	public List<Sail> initializeProducts(Collection<Sail> sails) {
		for (Sail s : sails) {
			Hibernate.initialize(s.getProducts());
		}
		return (List<Sail>) sails;
	}

	public Sail initialize(Sail sail) {
		Hibernate.initialize(sail.getBuyers());
		Hibernate.initialize(sail.getProducts());
		return sail;
	}

	public Double getProfit(Collection<Sail> sails) {
		Double profit = 0.0;
		for (Sail s : sails) {
			// TODO: Kirill может как вариант их все туда сразу передать?
			profit += serviceBuyer.profitFromReferralBySail(s, s.getCashbackPercent());
		}
		return profit;
	}

	@Transactional
	public void save(Sail sail, Basket basket) {
		sail.setTotalsum(basket.cost());
		sail.setAmount(basket.countProducts());
		sail.setProducts(serviceSold.convertToSoldProduct(basket.getProducts()));
		sail.setStateWithDate(getState(State.SENT));
		save(sail);
	}

	@Transactional
	public void save(List<Buyer> buyers, Basket basket) {
		Sail sail = new Sail(buyers, serviceSold.convertToSoldProduct(basket.getProducts()), basket);
		save(sail);
	}

	@Transactional
	public void save(Buyer buyer, Basket basket) {
		buyer.setBalance(buyer.getBalance() - basket.cost());
		Sail sail = new Sail(buyer, serviceSold.convertToSoldProduct(basket.getProducts()), basket);
		try {
			serviceBuyer.edit(buyer);
			save(sail);
			// TODO: Kirill и что тут ловим? и что делаем если поймали? Делаем вид что ничего не было и... барабанная дробь...
			// GOTO "развязочка"
		} catch (Exception e) {
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
		s.setStateWithDate(getState(State.COMPLETE));
		try {
			for (Buyer buyer : s.getBuyers()) {
				if (buyer.getRefId() == null) continue;
				Buyer parent = serviceBuyer.get(buyer.getRefId());
				serviceBuyer.accrueRevenue(parent, s);
				serviceStatistic.saveSailStatistic(parent, new Date(), buyer.getTracker());
			}
			update(s);
			serviceTotalSold.add(s.getProducts());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void sailConflict(Long sailId) {
		Sail sail = find(sailId);
		sail.setStateWithDate(getState(State.CONFLICT));
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
		return sailDao.countByReferral(referalId, dateSail);
	}
}
