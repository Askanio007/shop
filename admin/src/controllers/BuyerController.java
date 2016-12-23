package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import dto.BuyerDto;
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
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceBuyer.countAll());
		List<BuyerDto> list = serviceBuyer.listDto(viewPagination.getDBPagination());
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
		BuyerDto buyer = serviceBuyer.getDto(buyerId);
		model.addAttribute("buyerEdit", buyer);
		return "buyer/edit";
	}

	@RequestMapping(value = "/buyer/edit/{buyerId}", method = RequestMethod.POST)
	public String edit(@ModelAttribute("buyerEdit") @Valid BuyerDto buyerDto, BindingResult result, Model model) {
		if (result.hasErrors())
			return "buyer/edit/" + buyerDto.getId();
		serviceBuyer.edit(buyerDto);
		model.addAttribute("infoMessage", "Edit success");
		return "redirect:/buyer/all";
	}

}