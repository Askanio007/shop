package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Discount;
import entity.Product;

@Repository("discountDAO")
public class DiscountDAOImpl extends GeneralDAOImpl<Discount>implements DiscountDAO {

	public Discount getGeneralDisc() {
		return (Discount) createQuery("from Discount where buyer_id is null")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Discount> getActivePrivateDisc() {
		return createQuery("from Discount where active = true and buyer_id is not null")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Discount> getActivePrivateDiscByBuyerId(Long id) {
		return createQuery("from Discount where active = true and buyer_id = :id")
				.setLong("id", id).list();
	}

	@Override
	public Discount getPrivateDiscount(Product product, Long id) {
		return (Discount) createQuery("from Discount where active = true and buyer_id = :id and productId = :product")
				.setLong("id", id)
				.setLong("product",product.getId())
				.uniqueResult();
	}
}
