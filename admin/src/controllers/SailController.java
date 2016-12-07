package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import dto.ProductDto;
import dto.RoleDto;
import dto.SailDto;
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

	@RequestMapping("/sail/all")
	public String list(HttpServletRequest request, Model model, SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceSail.countAll());
		List<SailDto> sails = serviceSail.listDto(viewPagination.getDBPagination());
		status.setComplete();
		UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RoleDto role = serviceRole.getDtoByUserName(detail.getUsername());
		model.addAttribute("role", role);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sailView", SailView.convertSail(sails));
		return "sail/allsail";
	}

	@RequestMapping(value = "/sail/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("buyerList", serviceBuyer.listDto());
		model.addAttribute("productList", serviceProduct.listDto());
		model.addAttribute("sail", new SailDto());
		return "sail/addsail";
	}

	@RequestMapping(value = "/sail/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("sail") @Valid SailDto sail, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "redirect:/sail/add";
		}
		Basket buyerProdList = (Basket) request.getSession().getAttribute("buyerProdList");
		serviceSail.save(sail, buyerProdList);
		request.getSession().setAttribute("infoMessage", "Add Success");
		return "redirect:/sail/all";
	}

	@RequestMapping("/sail/delete")
	public String delete(HttpServletRequest request) {
		request.getSession().setAttribute("infoMessage", "Delete Success");
		if (request.getParameter("prod") != null) {
			serviceSail.remove(Long.parseLong(request.getParameter("id")));
			return "redirect:/sail/product/" + request.getParameter("prod");
		}
		if (request.getParameter("buy") != null) {
			serviceSail.remove(Long.parseLong(request.getParameter("id")));
			return "redirect:/sail/buyer/" + request.getParameter("buy");
		}
		serviceSail.remove(Long.parseLong(request.getParameter("id")));
		return "redirect:/sail/all";

	}

	@RequestMapping("/sail/buyer/{buyerId}")
	public String allSailByBuyer(@PathVariable("buyerId") Long buyerId, Model model, HttpServletRequest request) {
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceSail.countByBuyer(buyerId));
		List<SailDto> listDto = serviceSail.allDtoByBuyer(viewPagination.getDBPagination(), buyerId);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sailview", SailView.convertSail(listDto));
		model.addAttribute("buyer", serviceBuyer.getDto(buyerId));
		return "sail/sailbybuyer";
	}

	// AJAX
	@RequestMapping(value = "/sail/addBuy", method = RequestMethod.GET)
	public @ResponseBody List<String> addProductInSail(@RequestParam(value = "amount") String count,
													   @RequestParam(value = "idprod") String idprod,
													   HttpServletRequest request) throws JsonProcessingException {
		ProductDto newProd = serviceProduct.getDto(Long.parseLong(idprod));
		int amount = Integer.parseInt(count);
		Basket basket = (Basket)request.getSession().getAttribute("buyerProdList");
		if (basket == null)
			basket = new Basket();
		basket.addProduct(newProd, amount);
		request.getSession().setAttribute("buyerProdList", basket);
		return conv.listObjectToJson(basket.getProducts());

	}

	@RequestMapping(value = "/sail/removeBuy", method = RequestMethod.GET)
	public @ResponseBody List<String> removeProductInSail(@RequestParam(value = "idprod") String idprod,
			HttpServletRequest request) throws JsonProcessingException {
		Basket buyerProdList = (Basket) request.getSession().getAttribute("buyerProdList");
		buyerProdList.deleteProduct(Long.parseLong(idprod));
		return conv.listObjectToJson(buyerProdList.getProducts());

	}

}
