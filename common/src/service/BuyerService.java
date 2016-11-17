package service;

import dao.BuyerDAO;
import entity.Buyer;
import entity.BuyerInfo;
import entity.Sail;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.DateBuilder;
import view.DateConverter;
import utils.DateFilter;
import utils.EncryptionString;
import utils.PaginationFilter;

import java.util.*;

@Service
public class BuyerService {

	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDao;

	@Autowired
	private SettingsService settings;

	@Autowired
	private SailService sailService;

	@Autowired
	private StatisticReferralsService statisticService;


	@Transactional
	public void edit(Buyer buyer) {
		buyerDao.update(buyer);
	}

	@Transactional
	public void remove(Long id) {
		buyerDao.delete(id);
	}

	@Transactional
	public void save(Buyer buyer) {
		buyerDao.save(buyer);
	}

	@Transactional
	public int countAll() {
		return buyerDao.countAll();
	}

	@Transactional
	public Buyer get(String buyerName) {
		return buyerDao.findByName(buyerName);
	}

	@Transactional
	public Buyer getFullInfo(String buyerName) {
		Buyer buyer = buyerDao.findByName(buyerName);
		Hibernate.initialize(buyer.getInfo());
		return buyer;
	}

	@Transactional
	public String getPathAva(Long buyerId) {
		return buyerDao.getAvaPathById(buyerId);
	}


	@Transactional
	public Buyer getByRefCode(String code) {
		return buyerDao.getBuyerByReferCode(code);
	}



	public void initialize(List<Buyer> buyers) {
		for (Buyer buyer : buyers) {
			initialize(buyer);
		}
	}

	public void initialize(Buyer buyer) {
		Hibernate.initialize(buyer.getSails());
		Hibernate.initialize(buyer.getInfo());
	}

	public boolean checkEqualsOldPasswords(String newPassword, String oldPassword) {
		return newPassword.equals(EncryptionString.toMD5(oldPassword));
	}

	protected String randomReferCode() {
		return UUID.randomUUID().toString();
		// TODO: Kirill чтобы получить строку без дефисов, то можешь например
		// return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Transactional
	public void accrueRevenue(Buyer parent, Sail sail) {
		Double profit = profitFromReferralBySail(sail, parent.getPercentCashback());
		parent.setBalance(parent.getBalance() + profit);
		edit(parent);
	}

	@Transactional
	public void registration(String name, String password, String referCode, String tracker) {
		Long referId = null;
		if (referCode != null && !"".equals(referCode)) {
			referId = getByRefCode(referCode).getId();
			statisticService.saveRegistrationStatistic(get(referId), tracker);
		}
		String code = randomReferCode();
		Buyer buyer = new Buyer
				.Builder(name, EncryptionString.toMD5(password), code)
				.percentCashback(settings.getBaseCashback())
				.refId(referId)
				.tracker(tracker)
				.build();
		save(buyer);
	}

	@Transactional
	public List<Buyer> list(PaginationFilter dbPagination) {
		List<Buyer> buyers = buyerDao.find(dbPagination);
		initialize(buyers);
		return buyers;
	}

	@Transactional
	public List<Buyer> list() {
		List<Buyer> buyers = buyerDao.find();
		// TODO: 16.10.2016 да? ::: Если инициализировать, то получается, все покупатели подтягивают свои продажи. И это плохо, я праивльно понял?)
		//initialize(buyers);
		// TODO: Kirill ну раз ты закомментил и все продолжило работать и ты избавился от выборки из таблицы секстилионнов продаж и прочего хлама, то да.
		return buyers;
	}

	@Transactional
	public Buyer get(Long buyerId) {
		Buyer buyer = buyerDao.find(buyerId);
		initialize(buyer);
		sailService.initialize(buyer.getSails());
		return buyer;
	}

	@Transactional
	public void edit(Long buyerId, BuyerInfo buyerInfo, boolean active) {
		Buyer buyer = get(buyerId);
		buyer.setEnable(active);
		edit(buyer, buyerInfo);
	}

	@Transactional
	public void editPassword(Buyer buyer, String newPass) {
		buyer.setPassword(EncryptionString.toMD5(newPass));
		edit(buyer);
	}

	@Transactional
	public void edit(Buyer buyer, BuyerInfo info) {
		buyer.setInfo(info);
		edit(buyer);
	}

	// TODO: Kirill мне тут никакого реферала не видно  
	public Double profitFromReferralBySail(Sail sail, int cashBack){
		return sail.getTotalsum() * (cashBack * 1.0 / 100);
	}

	@Transactional
	public boolean isCorrectData(String login, String password) {
		Buyer buyer = get(login);
		if (buyer == null)
			return false;
		if (!buyer.getPassword().equals(password))
			return false;
		return true;
	}

	@Transactional
	public Double getProfitByLastMonth(Long buyerId) {
		List<Sail> sails = sailService.listCompletedByDate(buyerId, DateBuilder.getLastMonth());
		return sailService.getProfit(sails);
	}

	@Transactional
	public void saveAvatar(String nameBuyer, String avatarName) {
		Buyer user = get(nameBuyer);
		user.getInfo().setAva(settings.getPathUploadAva() + "\\" + avatarName);
		edit(user);
	}

	@Transactional
	public List<Buyer> getActiveByDay(Date date) {
		return buyerDao.getActiveByDate(date);
	}

	@Transactional
	public void aggregateProfitStatistic() {
		Date date = new Date();
		List<Buyer> buyers = getActiveByDay(date);
		for (Buyer buyer : buyers) {
			if (buyer.getRefId() == null) continue;
			Buyer parent = get(buyer.getRefId());
			List<Sail> todaySails = sailService.listCompletedByDate(buyer.getId(), new DateFilter(date));
			Double profit = 0.0;
			for (Sail sail : todaySails) {
				profit =+ profitFromReferralBySail(sail, parent.getPercentCashback());
			}
			statisticService.saveProfit(parent, buyer.getTracker(), profit);
		}
	}

}
