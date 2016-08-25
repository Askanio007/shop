package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.ProductDAO;
import entity.PictureProduct;
import entity.Product;
import utils.PaginationFilter;

@Service
public class ProductService {

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDao;

	public void deleteFromDisk(List<PictureProduct> pics) {
		for (PictureProduct pic : pics) {
			deleteFromDisk(pic);
		}
	}

	public void deleteFromDisk(PictureProduct pic) {
		try {
			Files.delete(Paths.get(pic.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize(List<Product> products) {
		for (Product p : products) {
			initialize(p);
		}
	}

	public void initialize(Product product) {
		Hibernate.initialize(product.getPicList());
	}

	@Transactional
	public List<Product> listProduct(PaginationFilter dbPagination) {
		List<Product> products = productDao.find(dbPagination);
		initialize(products);
		return products;
	}

	@Transactional
	public List<Product> listProduct() {
		return productDao.find();
	}

	@Transactional
	public void removeProduct(Product prod) {
		deleteFromDisk(prod.getPicList());
		productDao.delete(prod.getId());
	}

	@Transactional
	public Product getProduct(Long id) {
		Product product = productDao.find(id);
		Hibernate.initialize(product.getPicList());
		return product;
	}

	@Transactional
	public void editProduct(Product product) {
		productDao.update(product);
		List<PictureProduct> list = getPicWithoutProductId();
		for (PictureProduct pic : list) {
			deleteFromDisk(pic);
			productDao.delete(pic);
		}
	}

	@Transactional
	public Product getProductByNumber(int number) {
		PaginationFilter filter = new PaginationFilter(number, 1);
		productDao.find(filter);
		return productDao.find(filter).get(0);
	}

	@Transactional
	public void generateProducts() {
		String[] firstWom = {"Прикольная", "Инновационная", "Уникальная", "Единственная в своём роде", "Всепознающая", "Исцеляющая", "Убийственная", "Фееричная", "Безмозглая" };
		String[] secondWom = {"Накидка", "Голова", "Наковальня", "Палица", "Рукоятка", "Кружка", "Мышка", "Регистратура", "Почта России" };
		String[] threeWom = {"Смертоносного гладиатора", "Кенарийской экспедиции", "Илидана, Ярости бурь", "Короля лича", "Псении Кобчак", "Цветка Декабриста", "Больницы №9", "Педро Паскаля", "Исаака Ньютона" };

		String[] firstMan = {"Хвалёный", "Непреклонный", "Очень не очень", "Чуть-чуть отвратитльный очень сильно", "Волшебный", "Православный", "Переключающий", "Литературный", "Всепожирающий" };
		String[] secondMan = {"Меч", "Мяч", "Скотч", "Мангал", "Балкон", "Замок", "Джим Карр", "Яндекс", "Ультиматум" };
		String[] threeMan = {"Никого", "Винтерфела", "Харви Спектера", "Короля Бангладеша", "Борна", "Турецкого", "Оленя", "Петербурга", "'Кефирчик'" };

		Random r = new Random();
		for (int i = 0; i < 3000; i++) {
			String name;
			if (i % 2 == 0 )
				name = firstWom[r.nextInt(3)] + " " + secondWom[r.nextInt(8)] + " " + threeWom[r.nextInt(8)];
			else
				name = firstMan[r.nextInt(3)] + " " + secondMan[r.nextInt(8)] + " " + threeMan[r.nextInt(8)];
			double cost = ThreadLocalRandom.current().nextDouble(0, 30000);
			addProduct(new Product(name, cost));
		}
	}

	@Transactional
	public List<PictureProduct> getPicWithoutProductId() {
		return productDao.getPictureWithoutProductId();
	}

	@Transactional
	public int countAllProducts() {
		return productDao.countAll();
	}

	@Transactional
	public String getPathPicture(Long picId) {
		return productDao.getPicturePath(picId);
	}

	@Transactional
	public void addProduct(Product product) {
		productDao.add(product);
	}


}
