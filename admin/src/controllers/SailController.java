package controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import converter.JsonConverter;
import entity.Product;
import entity.Role;
import entity.Sail;
import models.Basket;
import service.BuyerService;
import service.DiscountService;
import service.ProductService;
import service.RoleService;
import service.SailService;
import view.SailView;
import view.ViewPagination;

@Controller
@SessionAttributes({ "buyerProdList", "infoMessage" })
public class SailController {

	@Autowired
	private SailService serviceSail;

	@Autowired
	private ProductService serviceProduct;

	@Autowired
	private BuyerService serviceBuyer;
	
	@Autowired
	protected DiscountService serviceDisc;

	@Autowired
	private JsonConverter conv;

	@Autowired
	private RoleService serviceRole;

	public SailController() {

	}

	@PostConstruct
	public void anyNameOfMethodAsYouWishButUsually_init_or_initialize() {
	}

	@RequestMapping("/sail/all")
	public String listSail(HttpServletRequest request, Model model, SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request, serviceSail.countAllSails());
		List<SailView> listSailView = serviceSail.listViewSail(viewPagination.getDBPagination());
		status.setComplete();
		UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Role role = serviceRole.getRoleByUserName(detail.getUsername());
		model.addAttribute("role", role);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sailView", listSailView);
		return "sail/allsail";
	}

	@RequestMapping(value = "/sail/add", method = RequestMethod.GET)
	public String addSailPage(Model model) {
		model.addAttribute("buyerList", serviceBuyer.listBuyer());
		model.addAttribute("productList", serviceProduct.listProduct());
		model.addAttribute("sail", new Sail());
		return "sail/addsail";
	}

	@RequestMapping(value = "/sail/add", method = RequestMethod.POST)
	public String addSail(@ModelAttribute("sail") @Valid Sail sail, BindingResult result, Model model,HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("buyerList", serviceBuyer.listBuyer());
			model.addAttribute("productList", serviceProduct.listProduct());
			return "sail/addsail";
		}
		Basket buyerProdList = (Basket) request.getSession().getAttribute("buyerProdList");
		serviceSail.addSail(sail, buyerProdList);
		request.getSession().setAttribute("infoMessage", "Add Success");
		return "redirect:/sail/all";
	}

	@RequestMapping("/sail/delete")
	public String deleteSail(HttpServletRequest request) {
		request.getSession().setAttribute("infoMessage", "Delete Success");
		if (request.getParameter("prod") != null) {
			serviceSail.removeSail(Long.parseLong(request.getParameter("id")));
			return "redirect:/sail/product/" + request.getParameter("prod");
		}
		if (request.getParameter("buy") != null) {
			serviceSail.removeSail(Long.parseLong(request.getParameter("id")));
			return "redirect:/sail/buyer/" + request.getParameter("buy");
		}
		serviceSail.removeSail(Long.parseLong(request.getParameter("id")));
		return "redirect:/sail/all";

	}

	@RequestMapping("/sail/buyer/{buyerId}")
	public String allSailByBuyer(@PathVariable("buyerId") Long buyerId, Model model, HttpServletRequest request) {
		ViewPagination viewPagination = new ViewPagination(request, serviceSail.countSailsByBuyer(buyerId));
		List<SailView> listSailView = serviceSail.allViewSailByBuyer(viewPagination.getDBPagination(), buyerId);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sailview", listSailView);
		model.addAttribute("buyer", serviceBuyer.getBuyer(buyerId));
		return "sail/sailbybuyer";
	}

	// AJAX
	@RequestMapping(value = "/sail/addBuy", method = RequestMethod.GET)
	public @ResponseBody List<String> addProductInSail(
			@RequestParam(value = "amount") String count,
			@RequestParam(value = "idprod") String idprod,
			HttpServletRequest request
	) throws JsonProcessingException {
		Product newProd = serviceProduct.getProduct(Long.parseLong(idprod));
		int amount = Integer.parseInt(count);
		Basket basket = (Basket)request.getSession().getAttribute("buyerProdList");
		if (basket == null)
			basket = new Basket();
		basket.addProduct(newProd, amount);
		request.getSession().setAttribute("buyerProdList", basket);
		return conv.listObjectToJson(basket.getProducts());

	}

	@RequestMapping(value = "/sail/removeBuy", method = RequestMethod.GET)
	public @ResponseBody List<String> removeProductinSail(@RequestParam(value = "idprod") String idprod,
			HttpServletRequest request) throws JsonProcessingException {
		Basket buyerProdList = (Basket) request.getSession().getAttribute("buyerProdList");
		buyerProdList.deleteProduct(Long.parseLong(idprod));
		return conv.listObjectToJson(buyerProdList.getProducts());

	}

}
