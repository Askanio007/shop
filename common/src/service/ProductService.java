package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import utils.LoadFileUtil;
import utils.PaginationFilter;
import utils.UploadZip;

@Service
public class ProductService {

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDao;

	@Autowired
	private SettingsService setting;

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
	public List<Product> list(PaginationFilter dbPagination) {
		List<Product> products = productDao.find(dbPagination);
		initialize(products);
		return products;
	}

	@Transactional
	public List<Product> list() {
		return productDao.find();
	}

	@Transactional
	public void remove(Product prod) {
		deleteFromDisk(prod.getPicList());
		productDao.delete(prod.getId());
	}

	@Transactional
	public Product get(Long id) {
		Product product = productDao.find(id);
		Hibernate.initialize(product.getPicList());
		return product;
	}

	@Transactional
	public void edit(Product product) {
		productDao.update(product);
		List<PictureProduct> list = getPicWithoutProductId();
		for (PictureProduct pic : list) {
			deleteFromDisk(pic);
			productDao.delete(pic);
		}
	}

	@Transactional
	public Product getByNumber(int number) {
		PaginationFilter filter = new PaginationFilter(number, 1);
		productDao.find(filter);
		return productDao.find(filter).get(0);
	}

	@Transactional
	public List<PictureProduct> getPicWithoutProductId() {
		return productDao.getPictureWithoutProductId();
	}

	@Transactional
	public int countAll() {
		return productDao.countAll();
	}

	@Transactional
	public String getPathPicture(Long picId) {
		return productDao.getPicturePath(picId);
	}

	@Transactional
	public void save(Product product) {
		productDao.save(product);
	}

	@Transactional
	public List<PictureProduct> addPicture(Object list, MultipartFile file) throws IOException  {
		List<PictureProduct> newListPic = new ArrayList<>();

		if (list != null)
			newListPic = (List<PictureProduct>) list;
		String dir = setting.getPathUploadPicProduct();

		if (LoadFileUtil.checkExtension(file, LoadFileUtil.FileType.IMAGE)) {
			LoadFileUtil.addPictureInList(file,newListPic, dir);
		}
		if (LoadFileUtil.checkExtension(file, LoadFileUtil.FileType.ARCHIVE)) {
			UploadZip.getPicFromArchive(newListPic, dir, file);
		}
		return newListPic;
	}

	@Transactional
	public Product getRandomProduct() {
		int countProduct = countAll();
		if (countProduct == 0) {
			return null;
		}
		Random rnd = new Random();
		int numberProduct = 0;
		while (numberProduct == 0) {
			numberProduct = rnd.nextInt(countProduct);
		}
		return getByNumber(numberProduct);
	}

}
