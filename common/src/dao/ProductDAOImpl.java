package dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;
import org.springframework.stereotype.Repository;

import entity.PictureProduct;
import entity.Product;

@Repository("productDAO")
public class ProductDAOImpl extends GeneralDAOImpl<Product>implements ProductDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<PictureProduct> getPictureWithoutProductId() {
		return session().createQuery("from PictureProduct where prod is null").list();
	}

	@Override
	public String getPicturePath(Long picId) {
		return (String) createQuery("select path from PictureProduct where id = :id")
					.setLong("id", picId)
					.uniqueResult();
	}

}
