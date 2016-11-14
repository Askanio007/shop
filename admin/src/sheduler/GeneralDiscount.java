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

	@Override
	public void run() {

		Product product = serviceProduct.getRandomProduct();
		Discount discount = serviceDisc.getGeneral();
		try {
			if (discount == null) {
				serviceDisc.createGeneral(product);
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
