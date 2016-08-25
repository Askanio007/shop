package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.Buyer;
import entity.Discount;
import entity.Product;
import entity.Sail;
import models.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import service.*;
import utils.CalculatorDiscount;
import utils.CurrentUser;
import utils.StateSail;
import view.ViewPagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes({ "basket"})
public class BuyController {
	
	@Autowired
	private SailService serviceSail;
	
	@Autowired
	private ProductService serviceProduct;
	
	@Autowired
	private BuyerService serviceBuyer;
	
	@Autowired
	private DiscountService serviceDisc;

	@Autowired
	TotalSoldProductService serviceTotalSold;

	private static final int countRecordOnPage = 10;

	public static void setCountProductBasketInModel(HttpServletRequest request, Model model) {
		Basket basket = (Basket)request.getSession().getAttribute("basket");
		model.addAttribute("countProductInBasket", basket != null ?  basket.countProducts() : 0);
	}
	
	@RequestMapping(value = "/user/basket", method = RequestMethod.GET)
	public String basket(Model model, HttpServletRequest request) {
		setCountProductBasketInModel(request, model);
		return "user/basket";
	}

	@RequestMapping(value = "/user/deleteFromBasket", method = RequestMethod.GET)
	public String deleteFromBasket(Model model, HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		basket.deleteProduct(Long.parseLong(request.getParameter("id")));
		request.removeAttribute("id");
		setCountProductBasketInModel(request, model);
		return "user/basket";
	}

	@RequestMapping(value = "/user/order", method = RequestMethod.GET)
	public String order(HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		Buyer b = serviceBuyer.getBuyer(CurrentUser.getName());
		Double balanceBuyer = b.getBalance() - basket.cost();
		if (balanceBuyer < 0)
			return "redirect:/user/deposit";
		b.setBalance(balanceBuyer);
		serviceSail.addUserSail(CurrentUser.getName(), basket);
		serviceBuyer.editBuyer(b);
		basket.clean();
		request.getSession().setAttribute("basket", basket);
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/deliveredSail", method = RequestMethod.GET)
	public String stateToDelivered(HttpServletRequest request) {
		serviceSail.sailComplete(Long.parseLong(request.getParameter("id")));
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/conflictSail", method = RequestMethod.GET)
	public String stateToConflict(HttpServletRequest request) {
		serviceSail.sailConflict(Long.parseLong(request.getParameter("id")));
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/buy", method = RequestMethod.POST)
	public String addProductInSailForUser(@RequestParam("amount") String count, HttpServletRequest request)
			throws JsonProcessingException {
		Product newProd = serviceProduct.getProduct(Long.parseLong(request.getParameter("id")));
		int amount = Integer.parseInt(count);

		Basket basket = (Basket)request.getSession().getAttribute("basket");
		if (basket == null)
			basket = new Basket();
        
		byte discount = 0;
		Discount disc = serviceDisc.getDiscountByProduct(newProd, serviceBuyer.getBuyer(CurrentUser.getName()).getId());
		if ( disc != null )
			discount = disc.getDiscount();				
		basket.addProduct(newProd, amount, discount);
		request.getSession().setAttribute("basket", basket);
		return "redirect:/user/products";
	}
	
	@RequestMapping(value = "/user/products", method = RequestMethod.GET)
	public String productList(Model model, HttpServletRequest request) {
		ViewPagination viewPagination = new ViewPagination(request, serviceProduct.countAllProducts(),
				countRecordOnPage);
		List<Product> productList = serviceProduct.listProduct(viewPagination.getDBPagination());
		Discount discount = serviceDisc.getGeneralDiscount();
		List<Discount> activeUserDiscount = serviceDisc.listActivePrivateDiscountByBuyerId(serviceBuyer.getBuyer(CurrentUser.getName()).getId());
		CalculatorDiscount.calculateGeneralDiscount(productList, discount);
		CalculatorDiscount.calculatePrivateDiscount(productList, activeUserDiscount);
		model.addAttribute("discount", discount);
		model.addAttribute("privateDiscount", activeUserDiscount);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("productList", productList);
		setCountProductBasketInModel(request, model);
		return "user/productList";
	}

}
