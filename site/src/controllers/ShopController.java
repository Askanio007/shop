package controllers;

import models.Basket;
import models.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import service.BuyerService;
import service.ClickStatisticService;
import service.ProductService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"referCode"})
public class ShopController {

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private ClickStatisticService serviceClickStatistic;

	@Autowired
	// @Qualifier("simplePassValid")
	@Qualifier("passValid")
	private Validator valid;

	@RequestMapping(value = "/reg/{param}", method = RequestMethod.GET)
	public String registrationParam(@PathVariable("param") String param, Model model, HttpServletRequest request, HttpServletResponse response) {
		String [] params = param.trim().split("&");

		Cookie code = new Cookie("code", params[0]);
		String tracker = null;
		code.setMaxAge(86400);
		response.addCookie(code);
		Cookie track;
		for (int i = 1; i<params.length; i++) {
			String[] nameAndValue = params[i].trim().split("=");
			if ("tracker".equals(nameAndValue[0])) tracker = nameAndValue[1];
			track = new Cookie(nameAndValue[0], nameAndValue[1]);
			track.setMaxAge(86400);
			response.addCookie(track);
		}
		serviceClickStatistic.setClickStatistic(params[0], new Date(), tracker);
		return "redirect:/reg";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String registration(Model model, HttpServletRequest request) {
		model.addAttribute("password", new Password());
		return "../../registration";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String registration(@RequestParam("name") String nameBuyer,
							   @RequestParam("refer") String referCode,
							   @ModelAttribute("password") Password password,
							   @CookieValue(value = "code", defaultValue = "") String referCodeCookie,
							   @CookieValue(value = "tracker", defaultValue = "") String tracker,
							   HttpServletRequest request, BindingResult result) {

		valid.validate(password, result);
		if (result.hasErrors())
			return "../../registration";

		if (referCode == "")
			referCode = referCodeCookie;
		else {
			tracker =  null;
			serviceClickStatistic.setEnterCode(referCode, new Date());
		}

		serviceBuyer.regUser(nameBuyer,password.getNewPassword(),referCode, tracker);
		List<GrantedAuthority> role = new ArrayList<GrantedAuthority>();
		role.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "isAuthenticated()";
			}
		});
		Authentication auth = new UsernamePasswordAuthenticationToken(nameBuyer, password.getNewPassword(), role);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/user/profile";
	}
}