package controllers;

import models.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import service.BuyerService;
import service.StatisticReferralsService;
import utils.CookieBuilder;
import utils.ReferralParametersParser;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static auth.CustomAuthenticationProvider.getAuthentication;

@Controller
@SessionAttributes({"referCode"})
public class ShopController {

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private StatisticReferralsService serviceClickStatistic;

	@Autowired
	// @Qualifier("simplePassValid")
	@Qualifier("passValid")
	private Validator valid;

	@RequestMapping(value = "/reg/{param}", method = RequestMethod.GET)
	public String registrationParam(@PathVariable("param") String param, HttpServletResponse response) {
		CookieBuilder.addCookie(response, param);
		serviceClickStatistic.saveClickByLink(ReferralParametersParser.getParentCode(param), new Date(), ReferralParametersParser.getTracker(param));
		return "redirect:/reg";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("password", new Password());
		return "../../registration";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String registration(@RequestParam("name") String nameBuyer,
							   @RequestParam("refer") String referCode,
							   @ModelAttribute("password") Password password,
							   @CookieValue(value = "partnerCode", defaultValue = "") String referCodeFromCookie,
							   @CookieValue(value = "tracker", defaultValue = "") String tracker,
							   BindingResult result) {
		valid.validate(password, result);
		if (result.hasErrors())
			return "../../registration";
		if (referCode != "") {
			serviceClickStatistic.saveEnterCode(referCode, new Date());
			tracker =  null;
			referCodeFromCookie = referCode;
		}
		serviceBuyer.registration(nameBuyer,password.getNewPassword(),referCodeFromCookie, tracker);
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(nameBuyer, password.getNewPassword()));
		return "redirect:/user/profile";
	}
}