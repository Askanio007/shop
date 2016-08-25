package sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Discount;
import service.DiscountService;

@Component
public class PrivateDiscount implements Runnable {

	@Autowired
	private DiscountService discountService;

	@Override
	public void run() {
		discountService.deactivateAllPrivate();
	}

}
