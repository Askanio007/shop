package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import dto.BuyerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import entity.Buyer;
import entity.BuyerInfo;
import service.BuyerService;
import view.ViewPagination;

@Controller
@SessionAttributes("infoMessage")
public class BuyerController {

	@Autowired
	private BuyerService serviceBuyer;	

	@RequestMapping(value = "/buyer/all", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request, serviceBuyer.countAll());
		List<BuyerDTO> list = serviceBuyer.list(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("buyerList", list);
		status.setComplete();
		return "buyer/all";
	}

	@RequestMapping("/buyer/delete")
	public String delete(HttpServletRequest request) {
		serviceBuyer.remove(Long.parseLong(request.getParameter("id")));
		request.getSession().setAttribute("infoMessage", "Remove success");
		return "redirect:/buyer/all";
	}

	@RequestMapping(value = "/buyer/edit/{buyerId}", method = RequestMethod.GET)
	public String edit(@PathVariable("buyerId") Long buyerId, Model model) {
		Buyer buyer = serviceBuyer.get(buyerId);
		model.addAttribute("buyer", buyer);
		model.addAttribute("buyerInfo", buyer.getInfo());
		return "buyer/edit";
	}

	@RequestMapping(value = "/buyer/edit/{buyerId}", method = RequestMethod.POST)
	public String edit(@ModelAttribute("buyerInfo") @Valid BuyerInfo buyerInfo,@PathVariable("buyerId") Long buyerId, HttpServletRequest request, BindingResult result, Model model) {
		if (result.hasErrors())
			return "buyer/edit";
		serviceBuyer.edit(buyerId, buyerInfo, request.getParameter("active") == null);
		model.addAttribute("infoMessage", "Edit success");
		return "redirect:/buyer/all";
	}

}