package utils;

import dto.DiscountDto;
import dto.ProductDto;
import entity.Discount;
import entity.Product;

import java.math.BigDecimal;
import java.util.List;

public class CalculatorDiscount {

	public static BigDecimal getCostWithDiscount(BigDecimal cost, byte discount) {
		BigDecimal disc = new BigDecimal(discount * 0.01);
		return cost.subtract(cost.multiply(disc));
	}

	public static void setNewCost(ProductDto product, DiscountDto discount) {
		if (product.getId() == discount.getProductId())
			product.setCost(getCostWithDiscount(product.getCost(), discount.getDiscount()));
	}
	
	public static void calculateGeneralDiscount(List<ProductDto> products, DiscountDto discount) {
		if (discount != null) {
			if (discount.getProductId() != 0) {
				calculate(products, discount);
			}
		}
	}

	public static void calculatePrivateDiscount(List<ProductDto> products, List<DiscountDto> discounts) {
		if (discounts.isEmpty())
			return;
		discounts.stream().forEach((discount) -> calculate(products, discount));
	}

	private static void calculate(List<ProductDto> products, DiscountDto discount) {
		for (ProductDto product : products) {
			if (product.getId() == discount.getProductId()) {
				setNewCost(product, discount);
				break;
			}
		}
	}

}
