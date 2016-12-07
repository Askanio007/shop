package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.RoleDto;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import service.*;
import view.ViewPagination;

@Controller
@SessionAttributes("infoMessage")
public class UserController {
	
	@Autowired
	private UserService serviceUser;

	@Autowired
	private RoleService serviceRole;

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private GeneratorStatistic generatorService;

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Model model) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			model.addAttribute("user", "Anonim");
			return "info";
		}
		UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RoleDto role = serviceRole.getDtoByUserName(detail.getUsername());
		model.addAttribute("user", role.getInfo());
		return "info";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("user", new UserDto());
		model.addAttribute("roles",serviceRole.getAllDto());
	return "user/add";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("user") UserDto user,HttpServletRequest request) {
		Long roleId = Long.parseLong(request.getParameter("role"));
		serviceUser.save(user, roleId);
	return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceUser.countAll());
		List<UserDto> list = serviceUser.listDto(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("userList", list);
		status.setComplete();
	return "user/list";
	}
	
	@RequestMapping("/user/delete")
	public String delete(HttpServletRequest request) {
		serviceUser.delete(Long.parseLong(request.getParameter("id")));
		request.getSession().setAttribute("infoMessage", "Remove succes");
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/user/listRole", method = RequestMethod.GET)
	public String list(Model model) {
		List<RoleDto> roles = serviceRole.getAllDto();
		model.addAttribute("roles", roles);
	return "user/listRole";
	}
	
	@RequestMapping(value = "/user/editRole", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, Model model) {
		model.addAttribute("role", serviceRole.getDto(id));
		return "user/editRole";
	}
	
	@RequestMapping(value = "/user/editRole", method = RequestMethod.POST)
	public String edit(@ModelAttribute("role") RoleDto role) {
		serviceRole.edit(role);
		return "redirect:/user/listRole";
	}

	@RequestMapping(value = "/generateBuyer", method = RequestMethod.GET)
	public String generateBuyer() {
		generatorService.generateBuyer();
		return "redirect:/info";
	}

	@RequestMapping(value = "/generateRef", method = RequestMethod.GET)
	public String generateReferrals() {
		generatorService.generateReferral();
		return "redirect:/info";
	}

	@RequestMapping(value = "/generateProd", method = RequestMethod.GET)
	public String generateProd() {
		generatorService.generateProducts();
		return "redirect:/info";
	}

	@RequestMapping(value = "/generateSail", method = RequestMethod.GET)
	public String generateSail() {
		generatorService.generateSail();
	return "redirect:/info";
	}

	@RequestMapping(value = "/calcProfit", method = RequestMethod.GET)
	public String calcProfit() {
		serviceBuyer.aggregateProfitStatistic();
		return "redirect:/info";
	}
	
}
