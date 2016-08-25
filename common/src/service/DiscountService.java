package service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.DiscountDAO;
import entity.Discount;
import entity.Product;

@Service
public class DiscountService {

	@Autowired
	@Qualifier("discountDAO")
	private DiscountDAO discountDao;

	@Autowired
	private ChatService serviceChat;

	@Transactional
	public void addDiscount(Discount dsc, String notice) {
		try {
			addDiscount(dsc);
			serviceChat.addMessageFromSystem(notice, dsc.getBuyer().getId());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void addDiscount(Discount dsc) {
		dsc.setActive(true);
		discountDao.add(dsc);
	}
	
	@Transactional
	public Discount getDiscountByProduct(Product product, Long buyerId) {
		Discount privateDiscount =  getPrivateDiscount(product, buyerId);
		if (privateDiscount != null)
			return privateDiscount;
		Discount generalDiscount =  getGeneralDiscount();
		if (generalDiscount.getProductId() == product.getId())
			return generalDiscount;
		return null;
	}

	@Transactional
	public void deactivateAllPrivate() {
		for (Discount disc : listActivePrivateDiscount()) {
			disc.setActive(false);
			editDiscount(disc);
		}
	}

	@Transactional
	public Discount getPrivateDiscount(Product product, Long buyerId) {
		return discountDao.getPrivateDiscount(product, buyerId);
	}
	
	@Transactional
	public List<Discount> listActivePrivateDiscountByBuyerId(Long id) {
		return discountDao.getActivePrivateDiscByBuyerId(id);
	}

	@Transactional
	public Discount getGeneralDiscount() {
		return discountDao.getGeneralDisc();
	}

	@Transactional
	public void editDiscount(Discount dsc) {
		discountDao.update(dsc);
	}

	@Transactional
	public List<Discount> listActivePrivateDiscount() {
		return discountDao.getActivePrivateDisc();
	}
}
