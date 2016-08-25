package converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import entity.Product;
import service.ProductService;

@Component
public class ProductConverter implements Converter<String, Product> {

	@Autowired
	private ProductService serviceProduct;

	@Override
	public Product convert(String arg0) {
		return serviceProduct.getProduct(Long.parseLong(arg0));
	}

}
