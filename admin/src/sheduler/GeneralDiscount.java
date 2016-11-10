package sheduler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Discount;
import entity.Product;
import service.MessageService;
import service.DiscountService;
import service.ProductService;

@Component
public class GeneralDiscount implements Runnable {

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private DiscountService serviceDisc;

	@Autowired
	private MessageService serviceChat;

	Random rnd = new Random();

	@Override
	public void run() {
		int numberProduct = 0;
		int countProduct = serviceProduct.countAll();
		if (countProduct == 0) {
			return;
		}
		while (numberProduct == 0) {
			numberProduct = rnd.nextInt(countProduct);
		}
		Product product = serviceProduct.getByNumber(numberProduct);
		Discount discount = serviceDisc.getGeneral();
		try {
			if (discount == null) {
				discount = new Discount();
				discount.setDiscount((byte) 50);
				discount.setProductId(product.getId());
				serviceDisc.save(discount);
			} else {
				discount.setProductId(product.getId());
				serviceDisc.edit(discount);
			}
			String text = discount.getDiscount() + "% discount on the " + product.getName();
			serviceChat.addFromSystem(text, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
