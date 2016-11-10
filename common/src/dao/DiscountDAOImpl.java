package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Discount;
import entity.Product;

// TODO: 16.10.2016 сконфигурировать плагин hibernate
@Repository("discountDAO")
public class DiscountDAOImpl extends GeneralDAOImpl<Discount>implements DiscountDAO {

	public Discount getGeneral() {
		return (Discount) createQuery("from Discount disc where buyer is null")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Discount> getActivePrivate() {
		return createQuery("from Discount disc inner join disc.buyer buyer where active = true and buyer.id is not null")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Discount> getActivePrivateByBuyerId(Long id) {
		return createQuery("from Discount disc inner join disc.buyer buyer where active = true and buyer.id = :id")
				.setLong("id", id).list();
	}

	@Override
	public Discount getPrivate(Product product, Long id) {
		return (Discount) createQuery("from Discount disc inner join disc.buyer buyer where active = true and buyer.id = :id and disc.productId = :product")
				.setLong("id", id)
				.setLong("product",product.getId())
				.uniqueResult();
	}
}
