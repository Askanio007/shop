package sheduler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Discount;
import entity.Product;
import service.ChatService;
import service.DiscountService;
import service.ProductService;

@Component
public class GeneralDiscount implements Runnable {

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private DiscountService serviceDisc;

	@Autowired
	private ChatService serviceChat;

	Random rnd = new Random();

	@Override
	public void run() {
		int numberProduct = 0;
		int countProduct = serviceProduct.countAllProducts();
		if (countProduct == 0) {
			return;
		}
		while (numberProduct == 0) {
			numberProduct = rnd.nextInt(countProduct);
		}
		Product product = serviceProduct.getProductByNumber(numberProduct);
		Discount discount = serviceDisc.getGeneralDiscount();
		try {
			if (discount == null) {
				discount = new Discount();
				discount.setDiscount((byte) 50);
				discount.setProductId(product.getId());
				serviceDisc.addDiscount(discount);
			} else {
				discount.setProductId(product.getId());
				serviceDisc.editDiscount(discount);
			}
			String text = discount.getDiscount() + "% discount on the " + product.getName();
			serviceChat.addMessageFromSystem(text, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
