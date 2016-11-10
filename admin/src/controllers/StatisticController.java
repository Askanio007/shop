package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.Product;
import entity.SoldProduct;
import entity.TotalSoldProduct;
import models.FilterTotalSoldProduct;
import service.ProductService;
import service.SoldProductService;
import service.TotalSoldProductService;
import view.ViewPagination;

@Controller
public class StatisticController {

	@Autowired
	private SoldProductService serviceSoldProduct;

	@Autowired
	private ProductService serviceProduct;
	
	@Autowired
	private TotalSoldProductService serviceTotalSoldProduct;

	@RequestMapping(value = "/statistic/products", method = RequestMethod.GET)
	public String list(@ModelAttribute("filterTotalSoldProduct") FilterTotalSoldProduct filter, HttpServletRequest request, Model model) {

		if (filter == null)
			filter = new FilterTotalSoldProduct();

		ViewPagination viewPagination = new ViewPagination(request, serviceTotalSoldProduct.count(filter));
		String sort = request.getParameter("sort");
		List<TotalSoldProduct> totalDoldProducts = serviceTotalSoldProduct.list(viewPagination.getDBPagination(), sort,filter);
		model.addAttribute("sort", sort);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("soldProducts", totalDoldProducts);
		filter.clear();
		model.addAttribute("filterTotalSoldProduct", filter);
		return "statistic/statistic";
	}
	
	@RequestMapping(value = "/statistic/buyersByProduct/{productId}", method = RequestMethod.GET)
	public String productByBuyer(@PathVariable("productId") Long productId,  HttpServletRequest request, Model model) {
		Product product = serviceProduct.get(productId);
		ViewPagination viewPagination = new ViewPagination(request, serviceSoldProduct.count(product));
		List<SoldProduct> soldProducts = serviceSoldProduct.list(product, viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("soldProducts", soldProducts);
		model.addAttribute("product", product);
		return "statistic/buyersByProduct";
	}

}
