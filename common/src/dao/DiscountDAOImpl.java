package dao;

import java.util.List;

import dto.ProductDto;
import org.springframework.stereotype.Repository;

import entity.Discount;
import entity.Product;

@Repository("discountDAO")
public class DiscountDAOImpl extends GeneralDAOImpl<Discount>implements DiscountDAO {

	@Override
	public Discount getGeneral() {
		return (Discount) createQuery("from Discount disc where buyer is null")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Discount> getActivePrivate() {
		return createQuery("select disc from Discount disc inner join disc.buyer buyer where active = true and buyer.id is not null")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Discount> getActivePrivateByBuyerId(Long id) {
		return createQuery("select disc from Discount disc inner join disc.buyer buyer where active = true and buyer.id = :id")
				.setLong("id", id)
				.list();
	}

	@Override
	public Discount getPrivate(long productId, long buyerId) {
		return (Discount) createQuery("select disc from Discount disc inner join disc.buyer buyer where active = true and buyer.id = :id and disc.productId = :product")
				.setLong("id", buyerId)
				.setLong("product",productId)
				.uniqueResult();
	}
}
