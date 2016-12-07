package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.ProductDto;
import dto.SoldProductDto;
import dto.TotalSoldProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceTotalSoldProduct.count(filter));
		String sort = request.getParameter("sort");
		List<TotalSoldProductDto> totalDoldProductsDto = serviceTotalSoldProduct.listDto(viewPagination.getDBPagination(), sort,filter);
		model.addAttribute("sort", sort);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("soldProducts", totalDoldProductsDto);
		filter.clear();
		model.addAttribute("filterTotalSoldProduct", filter);
		return "statistic/statistic";
	}
	
	@RequestMapping(value = "/statistic/buyersByProduct/{productId}", method = RequestMethod.GET)
	public String productByBuyer(@PathVariable("productId") Long productId,  HttpServletRequest request, Model model) {
		ProductDto product = serviceProduct.getDto(productId);
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceSoldProduct.count(product));
		List<SoldProductDto> soldProducts = serviceSoldProduct.listDto(product, viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("soldProducts", soldProducts);
		model.addAttribute("product", product);
		return "statistic/buyersByProduct";
	}

}
