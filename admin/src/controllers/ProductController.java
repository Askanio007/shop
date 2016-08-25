package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import entity.Buyer;
import entity.Discount;
import entity.PictureProduct;
import entity.Product;
import service.BuyerService;
import service.ChatService;
import service.DiscountService;
import service.ProductService;
import service.SettingsService;
import utils.LoadFileUtil;
import utils.LoadFileUtil.FileType;
import view.ViewPagination;
import utils.UploadZip;

@Controller
@SessionAttributes({ "pics", "infoMessage" })
public class ProductController {

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private ChatService serviceChat;

	@Autowired
	private SettingsService setting;

	@Autowired
	private DiscountService serviceDisc;

	@RequestMapping("/login")
	public String GoLogin() {
		return "../../login";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/upload", method = RequestMethod.POST)
	public String uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request, SessionStatus status)
			throws IllegalStateException, IOException {
		String dir = setting.getPathUploadPicProduct();
		List<PictureProduct> newListPic = new ArrayList<>();
		Object pics = request.getSession().getAttribute("pics");
		Object id = request.getParameter("id");
		
		if (pics != null)
			newListPic = (List<PictureProduct>) pics;

		if (LoadFileUtil.checkExtension(file, FileType.IMAGE)) {
			String path = LoadFileUtil.storeToFileWithOriginalName(file, dir);
			newListPic.add(new PictureProduct(path));
			request.getSession().setAttribute("pics", newListPic);
			if (id != null)
				return "redirect:/product/edit/" + id;
			return "redirect:/product/add";
		}

		if (LoadFileUtil.checkExtension(file, FileType.ARCHIVE)) {
			{
				UploadZip.getPicFromArchive(newListPic, dir, file);
				request.getSession().setAttribute("pics", newListPic);
				if (id != null)
					return "redirect:/product/edit/" + id;
				return "redirect:/product/add";
			}
		}
		request.getSession().setAttribute("infoMessage", "Invalid format");
		if (id != null)
			return "redirect:/product/edit/" + id;
		return "redirect:/product/add";
	}

	@RequestMapping(value = "/product/all", method = RequestMethod.GET)
	public String listProduct(HttpServletRequest request, Model model, SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request, serviceProduct.countAllProducts());
		List<Product> list = serviceProduct.listProduct(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("productList", list);
		model.addAttribute("product", new Product());
		status.setComplete();
		return "product/allproduct";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String addProductPage(Model model, HttpServletRequest request) {
		model.addAttribute("product", new Product());
		return "product/addproduct";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors())
			return "product/addproduct";
		List<PictureProduct> listPics = (List<PictureProduct>) request.getSession().getAttribute("pics");
		if (listPics != null) {
			product.setPicList(listPics);
		}
		serviceProduct.addProduct(product);
		request.getSession().setAttribute("infoMessage", "Add succes!");
		return "redirect:/product/all";
	}

	@RequestMapping("/product/delete")
	public String deleteProduct(HttpServletRequest request) {
		Product prod = serviceProduct.getProduct(Long.parseLong(request.getParameter("id")));
		serviceProduct.removeProduct(prod);
		request.getSession().setAttribute("infoMessage", "Delete succes!");
		return "redirect:/product/all";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/edit/{productId}", method = RequestMethod.GET)
	public String edit(@PathVariable("productId") Long productId, Model model, HttpServletRequest request) {
		Product prod = serviceProduct.getProduct(productId);
		Object pics = request.getSession().getAttribute("pics");
		if (pics != null) {
			prod.setPicList((List<PictureProduct>) pics);
			model.addAttribute("pic", pics);
		}
		model.addAttribute("product", prod);
		return "product/edit";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/edit/{productId}", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors())
			return "product/edit";
		
		List<PictureProduct> pics = (List<PictureProduct>) request.getSession().getAttribute("pics");
		if (pics == null) {
			serviceProduct.editProduct(product);
			request.getSession().setAttribute("infoMessage", "Edit success!");
			return "redirect:/product/all";
		}
		
		product.setPicList(pics);
		serviceProduct.editProduct(product);
		request.getSession().setAttribute("infoMessage", "Edit success!");
		return "redirect:/product/all";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest request) {
		model.addAttribute("buyers", serviceBuyer.listBuyer());
		model.addAttribute("product", Long.parseLong(request.getParameter("id")));
		model.addAttribute("disc", new Discount());
		return "product/addDiscount";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.POST)
	public String addDiscount(@ModelAttribute("disc") Discount disc, @RequestParam("buyerName") String name, HttpServletRequest request) {
		Buyer buyer = serviceBuyer.getBuyer(name);
		disc.setBuyer(buyer);
		String text = disc.getDiscount() + "% discount on the " + serviceProduct.getProduct(disc.getProductId()).getName() + " special for you, Mr. "
				+ buyer.getName();
		serviceDisc.addDiscount(disc, text);
		return "redirect:/product/all";
	}
}
