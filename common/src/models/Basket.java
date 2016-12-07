package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dto.ProductDto;
import entity.Product;
import utils.CalculatorDiscount;

public class Basket {

	private List<ProductBasket> products = new ArrayList<>();

	public Basket() {}

	public Basket(List<ProductBasket> products) {
		this.products = products;
	}

	public List<ProductBasket> getProducts() {
		return products;
	}

	public int countProducts() {
		return products.stream().mapToInt((p) -> p.getAmount()).sum();
	}


	public boolean isProductInBasket(ProductBasket product) {
		return products.contains(product);
	}

	public void addExistProduct(ProductBasket product) {
		for (ProductBasket prod : products) {
			// TODO: 16.10.2016 что за блажь для лонг переменных использовать equals()? ::: исправил и в других местах
			// TODO: Kirill прошу прощения, не совсем точно... скорее совсем не точно... выразился. ::: Исправил id в ProductBasket с Long на long.
			// В классе ProductBasket тип id ссылочный Long, не
			// нужен, достаточно обычного примитивного. Для ссылочного, действительно верно сравнивать equals'ом иначе
			// ты сравниваешь ссылки а не значения.

			if (prod.getId() == product.getId())
				prod.setAmount(prod.getAmount() + product.getAmount());
		}
	}

	public void addProduct(ProductDto product, int amount, byte discount) {
		ProductBasket prod = new ProductBasket(product, amount, discount);
		if (isProductInBasket(prod)) {
			addExistProduct(prod);
			return;
		}
		products.add(prod);
	}

	public void deleteProduct(Long productId) {
		Iterator<ProductBasket> iter = products.iterator();
		while (iter.hasNext()) {
			if (iter.next().getId() == productId)
				iter.remove();
		}
	}

	public void addProduct(ProductDto product, int amount) {
		products.add(new ProductBasket(product, amount, (byte)0));
	}

	public void clear() {
		products.clear();
	}

	public BigDecimal cost() {
		BigDecimal totalSum = BigDecimal.valueOf(0.0);
		for (ProductBasket prod : products) {
			totalSum.add(CalculatorDiscount.getCostWithDiscount(prod.getCost(), prod.getDiscount()).multiply(new BigDecimal(""+prod.getAmount()+"")));
		}
		return new BigDecimal(totalSum.toString());
	}

}
