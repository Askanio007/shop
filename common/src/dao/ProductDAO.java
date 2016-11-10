package dao;

import java.util.List;

import entity.PictureProduct;
import entity.Product;

public interface ProductDAO extends GeneralDAO<Product> {
	
	List<PictureProduct> getPictureWithoutProductId();
	
	List<PictureProduct> getPictureByProduct(Long id);
	
	String getPicturePath(Long picId);
	
	List<Product> getAllBySail(Long sailid);

}
