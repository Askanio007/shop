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
	private MessageService serviceChat;

	@Transactional
	public void save(Discount dsc, String notice) {
		try {
			save(dsc);
			serviceChat.sendFromSystem(notice, dsc.getBuyer());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void save(Discount dsc) {
		dsc.setActive(true);
		discountDao.save(dsc);
	}
	
	@Transactional
	public Discount availableDiscount(Product product, Long buyerId) {
		Discount privateDiscount =  getPrivate(product, buyerId);
		if (privateDiscount != null)
			return privateDiscount;
		Discount generalDiscount =  getGeneral();
		if (generalDiscount.getProductId() == product.getId())
			return generalDiscount;
		return null;
	}

	@Transactional
	public void deactivateAllPrivate() {
		for (Discount disc : listActivePrivate()) {
			disc.setActive(false);
			edit(disc);
		}
	}

	@Transactional
	public Discount getPrivate(Product product, Long buyerId) {
		return discountDao.getPrivate(product, buyerId);
	}
	
	@Transactional
	public List<Discount> listActivePrivateByBuyerId(Long id) {
		return discountDao.getActivePrivateByBuyerId(id);
	}

	@Transactional
	public Discount getGeneral() {
		return discountDao.getGeneral();
	}

	@Transactional
	public void edit(Discount dsc) {
		discountDao.update(dsc);
	}

	@Transactional
	public List<Discount> listActivePrivate() {
		return discountDao.getActivePrivate();
	}

	@Transactional
	public void createGeneral(Product product) {
		save(new Discount(product, (byte) 50));
	}
}
