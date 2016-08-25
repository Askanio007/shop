package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import entity.Role;
import entity.User;
import service.*;
import view.ViewPagination;

@Controller
@SessionAttributes("infoMessage")
public class UserController {
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private ProductService serviceProduct;
	
	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private SailService serviceSail;

	@Autowired
	private ReferralService serviceReferal;

	@Autowired
	private RoleService serviceRole;

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(HttpServletRequest request, Model model) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			model.addAttribute("user", "Anonim");
			return "info";
		}
		UserDetails detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Role role = serviceRole.getRoleByUserName(detail.getUsername());
		model.addAttribute("user", role.getInfo());
		return "info";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String addUserPage(Model model) {
		model.addAttribute("user", new User());
		List<Role> list = serviceRole.getAllRole();
		model.addAttribute("roles",list);
	return "user/add";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user,BindingResult result,HttpServletRequest request) {
		Long roleId = Long.parseLong(request.getParameter("role"));
		serviceUser.addUser(user, roleId);
	return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String listUser(HttpServletRequest request, Model model,SessionStatus status) {
		ViewPagination viewPagination = new ViewPagination(request, serviceUser.getCountAllUsers());
		List<User> list = serviceUser.listUser(viewPagination.getDBPagination());
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("userList", list);
		status.setComplete();
	return "user/list";
	}
	
	@RequestMapping("/user/delete")
	public String deleteBuyer(HttpServletRequest request) {
		serviceUser.deleteUser(Long.parseLong(request.getParameter("id")));
		request.getSession().setAttribute("infoMessage", "Remove succes");
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/user/listRole", method = RequestMethod.GET)
	public String editRole(HttpServletRequest request, Model model) {
		List<Role> roles = serviceRole.getAllRole();
		model.addAttribute("roles", roles);
	return "user/listRole";
	}
	
	@RequestMapping(value = "/user/editRole", method = RequestMethod.GET)
	public String editRole(@RequestParam("id") Long id, Model model) {
		model.addAttribute("role", serviceRole.getRole(id));
	return "user/editRole";
	}
	
	@RequestMapping(value = "/user/editRole", method = RequestMethod.POST)
	public String editRole(@ModelAttribute("role") Role role, BindingResult result, Model model) {
	role.setUsers(serviceUser.allUsersByRole(role));
	serviceRole.editRole(role);
	return "redirect:/user/listRole";
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public String generate() {
		serviceBuyer.generateBuyer();
		serviceReferal.generateReferral();
	return "redirect:/info";
	}
	
	@RequestMapping(value = "/generateSail", method = RequestMethod.GET)
	public String generateSail() {
		serviceProduct.generateProducts();
		serviceSail.generateSail();
	return "redirect:/info";
	}

	
}
