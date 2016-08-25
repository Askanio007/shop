package utils;

import entity.Discount;
import entity.Product;

import java.util.List;

public class CalculatorDiscount {

	public static double getCostWithDiscount(double cost, byte discount) {
		double disc = discount * 0.01;
		double finalCost = cost - (cost * disc);
		return finalCost;
	}

	public static void setNewCost(Product product, Discount discount) {
		if (product.getId().equals(discount.getProductId()))
			product.setCost(getCostWithDiscount(product.getCost(), discount.getDiscount()));
	}
	
	public static void calculateGeneralDiscount(List<Product> products, Discount discount) {
		if (discount.getProductId() != null) {
			calculate(products, discount);
		}
	}

	public static void calculatePrivateDiscount(List<Product> products, List<Discount> discounts) {
		if (discounts.isEmpty())
			return;
		for (Discount discount : discounts) {
			calculate(products, discount);
		}
	}

	private static void calculate(List<Product> products, Discount discount) {
		for (Product product : products) {
			if (product.getId().equals(discount.getProductId())) {
				setNewCost(product, discount);
				break;
			}
		}
	}

}
