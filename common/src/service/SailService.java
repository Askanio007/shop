package service;

import java.math.BigDecimal;
import java.util.*;

import javax.transaction.Transactional;

import dto.BuyerDto;
import dto.SailDto;
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
import utils.StateSail;
import view.SailView;

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

	// TODO: Kirill а тебе действительно нужно явно инициализировать коллекции, вроде бы во всех этих :: убрал все подобные инициализации
	// случаях прокси что заменяют ленивые коллекции до первого вызова вполне должны справится
	public List<Sail> initializeProducts(Collection<Sail> sails) {
		for (Sail s : sails) {
			Hibernate.initialize(s.getProducts());
		}
		return (List<Sail>) sails;
	}

	// TODO: Kirill мне тут никакого реферала не видно  ::: исправил название
	public BigDecimal profitFromSail(Sail sail, int cashBack){
		return sail.getTotalsum().multiply(BigDecimal.valueOf(cashBack * 1.0 / 100));
	}

	public BigDecimal profitFromSail(Collection<Sail> sails){ // TODO: Kirill может как вариант их все туда сразу передать? ::: создал дополнительный метод.
		// Тот используется в других местах, где не передаётся коллекция
		BigDecimal profit = BigDecimal.ZERO;
		for (Sail s : sails){
			profit.add(profitFromSail(s, s.getCashbackPercent()));
		}
		return profit;
	}

	@Transactional
	public void save(SailDto sailDto, Basket basket) {
		Sail sail = convertToEntity(sailDto);
		sail.setTotalsum(basket.cost());
		sail.setAmount(basket.countProducts());
		sail.setProducts(serviceSold.convertToSoldProduct(basket.getProducts()));
		sail.setStateWithDate(StateSail.SENT);
		save(sail);
	}

	@Transactional
	public Sail convertToEntity(SailDto sailDto) {
		Sail sail = new Sail();
		sail.setDate(sailDto.getDate());
		sail.setDateChangeState(sailDto.getDateChangeState());
		sail.setBuyer(serviceBuyer.get(sailDto.getBuyerName()));
		sail.setAmount(sailDto.getAmount());
		sail.setCashbackPercent(sailDto.getCashbackPercent());
		sail.setStateWithDate(sailDto.getState());
		sail.setTotalsum(sailDto.getTotalsum());
		return sail;
	}


	@Transactional
	public void save(BuyerDto buyerDto, Basket basket) {
		Buyer buyer = serviceBuyer.get(buyerDto.getId());
		buyer.setBalance(buyerDto.getBalance().subtract(basket.cost()));
		Sail sail = new Sail(buyer, serviceSold.convertToSoldProduct(basket.getProducts()), basket);
		serviceBuyer.edit(buyer);
		save(sail);
			// TODO: Kirill и что тут ловим? и что делаем если поймали? Делаем вид что ничего не было и... барабанная дробь...
			// GOTO "развязочка"
	}

	public void save(Sail sail) {
		sailDao.save(sail);
	}

	@Transactional
	private List<Sail> list(PaginationFilter dbFilter) {
		return sailDao.find(dbFilter);
	}

	@Transactional
	public List<SailDto> listDto(PaginationFilter dbFilter) {
		return SailDto.convertToDto(list(dbFilter));
	}

	@Transactional
	public List<Sail> allByBuyer(PaginationFilter dbFilter, Long buyerid) {
		return sailDao.getByBuyer(dbFilter, buyerid);
	}

	@Transactional
	public List<SailDto> allDtoByBuyer(PaginationFilter dbFilter, Long buyerid) {
		return SailDto.convertToDto(allByBuyer(dbFilter, buyerid));
	}

	@Transactional
	public Sail find(Long sailId) {
		Sail sail = sailDao.find(sailId);
		Hibernate.initialize(sail.getBuyer());
		return sail;
	}

	@Transactional
	public void sailComplete(Long sailId) {
		Sail s = find(sailId);
		s.setStateWithDate(StateSail.COMPLETE);
		if (s.getBuyer().getRefId() == null)
			return;
		try {
			Buyer parent = serviceBuyer.get(s.getBuyer().getRefId());
			serviceBuyer.accrueRevenue(parent, s);
			serviceStatistic.saveSailStatistic(parent, new Date(), s.getBuyer().getTracker());
			update(s);
			serviceTotalSold.add(s.getProducts());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void sailConflict(Long sailId) {
		Sail sail = find(sailId);
		sail.setStateWithDate(StateSail.CONFLICT);
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
