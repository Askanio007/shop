package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
		// TODO: 16.10.2016 у тебя разве не 8ая джава в проекте? если так, то стримы тебе в руки ::: сейчас 7, обновлю до 8, поизучаю
		int count = 0;
		for (ProductBasket product : products) {
			count = count + product.getAmount();
		}
		return count;
	}

	// TODO: 16.10.2016 return boolean - isProductInBasket(...) or just contains(...) ::: исправил
	public boolean isProductInBasket(ProductBasket product) {
		// TODO: 16.10.2016 мне кажется я 100 раз обращал внимание на это - в таких ситуация выглядит отстойно ::: исправил
		// больше не буду повторять, см сам. если нравится так  - делай так.
		return products.contains(product);
	}

	public void addExistProduct(ProductBasket product) {
		for (ProductBasket prod : products) {
			// TODO: 16.10.2016 что за блажь для лонг переменных использовать equals()? ::: исправил и в других местах
			if (prod.getId() == product.getId())
				prod.setAmount(prod.getAmount() + product.getAmount());
		}
	}

	public void addProduct(Product product, int amount, byte discount) {
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

	public void addProduct(Product product, int amount) {
		products.add(new ProductBasket(product, amount, (byte)0));
	}

	// TODO: 16.10.2016 рубрика "интересный факт" http://zotka.ru/otlp3v4r4r ::: прочел, принял к сведению
	public void clear() {
		products.clear();
	}

	public Double cost() {
		Double totalSum = 0.0;
		for (ProductBasket prod : products) {
			double cost = CalculatorDiscount.getCostWithDiscount(prod.getCost(), prod.getDiscount());
			totalSum += (cost * prod.getAmount());
		}
		return totalSum;
	}

}
