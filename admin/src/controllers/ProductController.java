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
	private DiscountService serviceDisc;

	@RequestMapping("/login")
	public String GoLogin() {
		return "../../login";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/upload", method = RequestMethod.POST)
	public String uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
		Object id = request.getParameter("id");
		Object pics = request.getSession().getAttribute("pics");
		request.getSession().setAttribute("pics", serviceProduct.addPicture(pics, file));
		if (!LoadFileUtil.isCorrectFormat(file))
			request.getSession().setAttribute("infoMessage", "Invalid format");
		if (id != null)
			return "redirect:/product/edit/" + id;
		return "redirect:/product/add";
	}

	@RequestMapping(value = "/product/all", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request, serviceProduct.countAll());
		List<Product> list = serviceProduct.list(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("productList", list);
		model.addAttribute("product", new Product());
		status.setComplete();
		return "product/allproduct";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("product", new Product());
		return "product/addproduct";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("product") @Valid Product product, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors())
			return "product/addproduct";

		Object listPics =  request.getSession().getAttribute("pics");
		if (listPics != null) {
			product.setPicList((List<PictureProduct>) listPics);
		}
		serviceProduct.save(product);
		request.getSession().setAttribute("infoMessage", "Add succes!");
		return "redirect:/product/all";
	}

	@RequestMapping("/product/delete")
	public String delete(HttpServletRequest request) {
		Product prod = serviceProduct.get(Long.parseLong(request.getParameter("id")));
		serviceProduct.remove(prod);
		request.getSession().setAttribute("infoMessage", "Delete succes!");
		return "redirect:/product/all";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/edit/{productId}", method = RequestMethod.GET)
	public String edit(@PathVariable("productId") Long productId, Model model, HttpServletRequest request) {
		Product prod = serviceProduct.get(productId);
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
	public String edit(@ModelAttribute("product") @Valid Product product, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors())
			return "product/edit";
		Object pics = request.getSession().getAttribute("pics");
		if (pics != null) {
			product.setPicList((List<PictureProduct>) pics);
		}
		serviceProduct.edit(product);
		request.getSession().setAttribute("infoMessage", "Edit success!");
		return "redirect:/product/all";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest request) {
		model.addAttribute("buyers", serviceBuyer.list());
		model.addAttribute("product", Long.parseLong(request.getParameter("id")));
		model.addAttribute("disc", new Discount());
		return "product/addDiscount";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.POST)
	public String addDiscount(@ModelAttribute("disc") Discount disc, @RequestParam("buyerName") String name) {
		Buyer buyer = serviceBuyer.get(name);
		disc.setBuyer(buyer);
		String text = disc.getDiscount() + "% discount on the " + serviceProduct.get(disc.getProductId()).getName() + " special for you, Mr. "
				+ buyer.getName();
		serviceDisc.save(disc, text);
		return "redirect:/product/all";
	}
}
