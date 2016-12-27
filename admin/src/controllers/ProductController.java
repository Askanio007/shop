package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import dto.DiscountDto;
import dto.ProductDto;
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

import service.BuyerService;
import service.DiscountService;
import service.ProductService;
import utils.LoadFileUtil;
import view.ViewPagination;

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
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceProduct.countAll());
		List<ProductDto> list = serviceProduct.listDto(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("productList", list);
		status.setComplete();
		return "product/allproduct";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("product", new ProductDto());
		return "product/addproduct";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("product") @Valid ProductDto product, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors())
			return "product/addproduct";

		Object listPics =  request.getSession().getAttribute("pics");
		if (listPics != null) {
			product.setPicPath((List<String>) listPics);
		}
		serviceProduct.save(product.convertToEntity());
		request.getSession().setAttribute("infoMessage", "Add succes!");
		return "redirect:/product/all";
	}

	@RequestMapping("/product/delete")
	public String delete(HttpServletRequest request) {
		serviceProduct.remove(Long.parseLong(request.getParameter("id")));
		request.getSession().setAttribute("infoMessage", "Delete succes!");
		return "redirect:/product/all";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/edit/{productId}", method = RequestMethod.GET)
	public String edit(@PathVariable("productId") Long productId, Model model, HttpServletRequest request) {
		ProductDto prod = serviceProduct.getDto(productId);
		Object pics = request.getSession().getAttribute("pics");
		if (pics != null) {
			prod.setPicPath((List<String>) pics);
			model.addAttribute("pic", pics);
		}
		model.addAttribute("product", prod);
		return "product/edit";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/edit/{productId}", method = RequestMethod.POST)
	public String edit(@ModelAttribute("product") @Valid ProductDto product, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors())
			return "product/edit";
		Object pics = request.getSession().getAttribute("pics");
		if (pics != null) {
			product.setPicPath((List<String>) pics);
		}
		serviceProduct.edit(product);
		request.getSession().setAttribute("infoMessage", "Edit success!");
		return "redirect:/product/all";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest request) {
		model.addAttribute("buyers", serviceBuyer.listDto());
		model.addAttribute("product", Long.parseLong(request.getParameter("id")));
		model.addAttribute("disc", new DiscountDto());
		return "product/addDiscount";
	}

	@RequestMapping(value = "/product/addDiscount", method = RequestMethod.POST)
	public String addDiscount(@ModelAttribute("disc") DiscountDto disc, @RequestParam("buyerName") String name) {
		disc.setNameBuyer(name);
		String text = disc.getDiscount() + "% discount on the " + serviceProduct.getDto(disc.getProductId()).getName() + " special for you, Mr. "
				+ name;
		serviceDisc.save(disc, text);
		return "redirect:/product/all";
	}
}
