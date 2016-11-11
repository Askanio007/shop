package dao;

import java.util.List;

import entity.PictureProduct;
import entity.Product;

public interface ProductDAO extends GeneralDAO<Product> {
	
	List<PictureProduct> getPictureWithoutProductId();
	
	String getPicturePath(Long picId);

}
