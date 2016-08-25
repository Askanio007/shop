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
import utils.DateFilter;
import utils.PaginationFilter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class BuyerService {

	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDao;

	@Autowired
	SettingsService settings;

	@Autowired
	private SailService sailService;

	public void initialize(List<Buyer> buyers) {
		for (Buyer buyer : buyers) {
			initialize(buyer);
		}
	}

	public void initialize(Buyer buyer) {
		Hibernate.initialize(buyer.getSails());
		Hibernate.initialize(buyer.getInfo());
	}

	public void initializeSail(Buyer buyer) {
		Hibernate.initialize(buyer.getSails());
	}

	@Transactional
	public int getCountAllBuyers() {
		return buyerDao.countAll();
	}

	public boolean checkEqualsOldPasswords(String newPassword, String oldPassword) {
		return newPassword.equals(getHashPassword(oldPassword)) ? true : false;
	}

	public String getHashPassword(String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(), 0, password.length());
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return hashedPass;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public void addBuyer(Buyer buyer) {
		buyerDao.add(buyer);
	}

	@Transactional
	public void regUser(String name, String password, String referCode, String tracker) {
		Long referId = null;
		if (referCode != null) referId = getBuyerIdByRefCode(referCode);
		Random r = new Random();
		String code = r.nextInt(999999999) + "" + r.nextInt(999999999);
		Buyer buyer = new Buyer
				.Builder(name, getHashPassword(password), code)
				.percentCashback(settings.getBaseCashback())
				.refId(referId)
				.tracker(tracker)
				.build();
		addBuyer(buyer);
	}

	@Transactional
	public void regGenerateUser(String name, String password, String referCode, String tracker, Date dateReg) {
		Long referId = null;
		if (referCode != null) referId = getBuyerIdByRefCode(referCode);
		Random r = new Random();
		String code = r.nextInt(999999999) + "" + r.nextInt(999999999);
		Buyer buyer = new Buyer
				.Builder(name, getHashPassword(password), code, dateReg)
				.percentCashback(settings.getBaseCashback())
				.refId(referId)
				.tracker(tracker)
				.build();
		addBuyer(buyer);
	}

	@Transactional
	public Long getBuyerIdByRefCode(String code) {
		return buyerDao.getBuyerIdByReferCode(code);
	}

	@Transactional
	public List<Buyer> listBuyer(PaginationFilter dbPagination) {
		List<Buyer> buyers = buyerDao.find(dbPagination);
		initialize(buyers);
		return buyers;
	}

	@Transactional
	public List<Buyer> listBuyer() {
		List<Buyer> buyers = buyerDao.find();
		initialize(buyers);
		return buyers;
	}

	@Transactional
	public void removeBuyer(Long id) {
		buyerDao.delete(id);
	}

	@Transactional
	public Buyer getBuyer(Long buyerId) {
		Buyer buyer = buyerDao.find(buyerId);
		initialize(buyer);
		sailService.initialize(buyer.getSails());
		return buyer;
	}

	@Transactional
	public void editBuyer(Buyer buyer) {
		buyerDao.update(buyer);
	}

	@Transactional
	public void editBuyerByAdmin(Long buyerId, BuyerInfo buyerInfo, boolean active) {
		Buyer buyer = getBuyer(buyerId);
		if (!active) {
			buyer.setEnable(false);
		}
		editBuyer(buyer, buyerInfo);
	}

	@Transactional
	public void editPasswordBuyer(Buyer buyer, String newPass) {
		buyer.setPassword(getHashPassword(newPass));
		editBuyer(buyer);
	}

	@Transactional
	public void editBuyer(Buyer buyer, BuyerInfo info) {
		buyer.getInfo().editBuyerInfo(info);
		editBuyer(buyer);
	}

	@Transactional
	public Buyer getBuyer(String buyerName) {
		return buyerDao.findByName(buyerName);
	}

	@Transactional
	public BuyerInfo getInfo(Long buyerId) {
		return buyerDao.findInfoByBuyerId(buyerId);
	}

	@Transactional
	public String getPathAva(Long buyerId) {
		return buyerDao.getAvaPathById(buyerId);
	}
	
    @Transactional
    public void calculateProfit(Sail sail) {
        Buyer buyer;
        for (Buyer refer : sail.getBuyers()) {
            if (refer.getRefId() != null) {
            	buyer = getBuyer(refer.getRefId());
            	buyer.setBalance(buyer.getBalance() + sail.getTotalsum() * (buyer.getPercentCashback() / 100));
                editBuyer(buyer);
            }
        }
    }

	@Transactional
	public Double getProfitByLastMonth(Long buyerId) {
		Calendar from = Calendar.getInstance();
		from.setTime(new Date());
		from.add(Calendar.MONTH, - 1);
		from.set(Calendar.HOUR, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);

		Calendar to = Calendar.getInstance();
		to.setTime(new Date());
		to.add(Calendar.DATE, -1);

		List<Sail> sails = sailService.listCompleteSailByDate(buyerId, new DateFilter(from.getTime(), to.getTime()));
		return sailService.getProfit(sails);
	}



	@Transactional
	public void generateBuyer() {
		regGenerateUser("MainBuyer", "password", null, null, new GregorianCalendar(2013, 2, 12).getTime());
	}



}
