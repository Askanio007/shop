package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;

import dto.ProductDto;
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

	private void deleteFromDisk(List<PictureProduct> pics) {
		pics.stream().forEach((pic) -> deleteFromDisk(pic));
	}

	private void deleteFromDisk(PictureProduct pic) {
		try {
			Files.delete(Paths.get(pic.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize(Product product) {
		Hibernate.initialize(product.getPicList());
	}

	@Transactional
	public List<Product> list(PaginationFilter dbPagination) {
		List<Product> products = productDao.find(dbPagination);
		return products;
	}

	@Transactional
	public List<ProductDto> listDto(PaginationFilter dbPagination) {
		return ProductDto.convertToDto(productDao.find(dbPagination));
	}

	@Transactional
	public List<ProductDto> listDto() {
		return ProductDto.convertToDto(productDao.find());
	}

	@Transactional
	public List<Product> list() {
		return productDao.find();
	}

	@Transactional
	public void remove(Long productId) {
		Product product = get(productId);
		deleteFromDisk(product.getPicList());
		productDao.delete(product.getId());
	}

	@Transactional
	public Product get(Long id) {
		return productDao.find(id);
	}

	@Transactional
	public ProductDto getDto(Long id) {
		return ProductDto.convertToDto(get(id));
	}

	@Transactional
	public void edit(ProductDto productDto) {
		Product prod = get(productDto.getId());
		productDao.update(productDto.transferDataToEntity(prod));
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

	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> addPicture(Object list, MultipartFile file) throws IOException  {
		List<String> newListPic = new ArrayList<>();

		if (list != null)
			newListPic = (List<String>) list;
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
