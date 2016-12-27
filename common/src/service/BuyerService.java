package service;

import dao.BuyerDAO;
import dto.BuyerDto;
import dto.BuyerInfoDto;
import entity.Buyer;
import entity.BuyerInfo;
import entity.Sail;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.DateBuilder;
import utils.DateFilter;
import utils.HashString;
import utils.PaginationFilter;

import java.math.BigDecimal;
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
	public String getPathAva(Long buyerId) {
		return buyerDao.getAvaPathById(buyerId);
	}


	@Transactional
	public Buyer getByRefCode(String code) {
		return buyerDao.getBuyerByReferCode(code);
	}

	// TODO: Kirill будь я придирчивым и говнистым, мне бы название не понравилось. ::: исправил название метода и переменных. Я подразумевал, что
	// сравниваются две строки, а хеш это или нет это уже не важно

	// TODO: Artyom а ещё, какая разница, хеш в переменной или не хеш? Ты дал методу две строки пароля, а что он там с ними делает это его проблемы. Или я всё не так понимаю?)
	// Говорят сравни, пароль старый с новым равны? А сравнивают не хеш одного пароля с хешем другого
	public boolean checkOldPasswords(String oldPassword, String enteredOldPassword) {
		return oldPassword.equals(HashString.toMD5(enteredOldPassword));
	}

	protected String randomReferCode() {
		return UUID.randomUUID().toString();
		// TODO: Kirill чтобы получить строку без дефисов, то можешь например  ::: когда-то на джава раш ешё это проходил даже )
		// return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Transactional
	public void accrueRevenue(Buyer parent, Sail sail) {
		parent.setBalance(parent.getBalance().add(sailService.profitFromSail(sail, parent.getPercentCashback())));
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
				.Builder(name, HashString.toMD5(password), code)
				.percentCashback(settings.getBaseCashback())
				.refId(referId)
				.tracker(tracker)
				.build();
		save(buyer);
	}

	// use in generic statistic
	@Transactional
	public List<Buyer> list(PaginationFilter dbPagination) {
		return buyerDao.find(dbPagination);
	}

	@Transactional
	public List<BuyerDto> listDto(PaginationFilter dbPagination) {
		return BuyerDto.convertToDTO(list(dbPagination));
	}

	@Transactional
	public List<BuyerDto> listDto() {
		return BuyerDto.convertToDTO(list());
	}

	@Transactional
	public List<Buyer> list() {
		return buyerDao.find();
	}

	@Transactional
	public Buyer get(Long buyerId) {
		return buyerDao.find(buyerId);
	}

	@Transactional
	public BuyerDto getDto(Long buyerId) {
		return BuyerDto.convertToDTO(get(buyerId));
	}

	@Transactional
	public BuyerDto getDto(String buyerName) {
		return BuyerDto.convertToDTO(get(buyerName));
	}

	@Transactional
	public void edit(BuyerDto buyerDto) {
		Buyer buyer = get(buyerDto.getId());
		edit(buyerDto.transferDataToEntity(buyer));
	}

	@Transactional
	public void editPassword(BuyerDto buyerDto, String newPass) {
		Buyer buyer = get(buyerDto.getId());
		buyer.setPassword(HashString.toMD5(newPass));
		edit(buyer);
	}

	@Transactional
	public void edit(String nameBuyer, BuyerInfoDto buyerInfo) {
		Buyer buyer = get(nameBuyer);
		buyer.getInfo().setAge(buyerInfo.getAge());
		buyer.getInfo().setPhone(buyerInfo.getPhone());
		buyer.getInfo().setSecondName(buyerInfo.getSecondName());
		edit(buyer);
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
	public BigDecimal getProfitByLastMonth(Long buyerId) {
		List<Sail> sails = sailService.listCompletedByDate(buyerId, DateBuilder.getLastMonth());
		return sailService.profitFromSail(sails);
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
			BigDecimal profit = BigDecimal.ZERO;
			for (Sail sail : todaySails) {
				profit.add(sailService.profitFromSail(sail, parent.getPercentCashback()));
			}
			statisticService.saveProfit(parent, buyer.getTracker(), profit);
		}
	}

	@Transactional
	public void changeBalance(String nameBuyer, BigDecimal sum) {
		Buyer b = get(nameBuyer);
		b.setBalance(b.getBalance().add(BigDecimal.valueOf(sum.doubleValue())));
		edit(b);
	}

}
