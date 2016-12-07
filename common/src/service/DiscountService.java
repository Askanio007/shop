package service;

import java.util.List;
import javax.transaction.Transactional;

import dto.DiscountDto;
import dto.ProductDto;
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

	@Autowired
	private BuyerService serviceBuyer;

	private Discount convertToEntity(DiscountDto dto) {
		Discount discount = new Discount();
		discount.setActive(dto.getActive());
		discount.setBuyer(serviceBuyer.get(dto.getNameBuyer()));
		discount.setDiscount(dto.getDiscount());
		discount.setProductId(dto.getProductId());
		return discount;
	}

	@Transactional
	public void save(DiscountDto dsc, String notice) {
		Discount discount = convertToEntity(dsc);
		try {
			save(discount);
			serviceChat.sendFromSystem(notice, discount.getBuyer());
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
	public DiscountDto availableDiscount(ProductDto product, Long buyerId) {
		Discount privateDiscount =  getPrivate(product, buyerId);
		if (privateDiscount != null)
			return DiscountDto.convertToDto(privateDiscount);
		Discount generalDiscount =  getGeneral();
		if (generalDiscount.getProductId() == product.getId())
			return DiscountDto.convertToDto(generalDiscount);
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
	public Discount getPrivate(ProductDto product, Long buyerId) {
		return discountDao.getPrivate(product.getId(), buyerId);
	}
	
	@Transactional
	public List<Discount> listActivePrivateByBuyerId(Long id) {
		return discountDao.getActivePrivateByBuyerId(id);
	}

	@Transactional
	public List<DiscountDto> listActivePrivateDtoByBuyerId(Long id) {
		return DiscountDto.convertToDto(listActivePrivateByBuyerId(id));
	}

	@Transactional
	public Discount getGeneral() {
		return discountDao.getGeneral();
	}

	@Transactional
	public DiscountDto getGeneralDto() {
		return DiscountDto.convertToDto(discountDao.getGeneral());
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
