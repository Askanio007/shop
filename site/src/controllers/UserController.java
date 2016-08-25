package controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import entity.Buyer;
import entity.BuyerInfo;
import models.Password;
import service.BuyerService;
import service.SailService;
import service.SettingsService;
import utils.CurrentUser;
import utils.LoadFileUtil;
import utils.LoadFileUtil.FileType;
import view.ViewPagination;

@Controller
public class UserController {

	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private SailService serviceSail;

	@Autowired
	private SettingsService setting;

	@Autowired
	// @Qualifier("simplePassValid")
	@Qualifier("passValid")
	private Validator valid;

	private static final int countRecordOnPage = 10;

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String editBuyerPage(Model model, HttpServletRequest request) {
		Buyer buyer = serviceBuyer.getBuyer(CurrentUser.getName());
		model.addAttribute("info", buyer.getInfo());
		model.addAttribute("newInfo", new BuyerInfo());
		return "user/editPrivateData";
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String cabinetUser(Model model, HttpServletRequest request) {
		Buyer user = serviceBuyer.getBuyer(CurrentUser.getName());
		user.setInfo(serviceBuyer.getInfo(user.getId()));
		ViewPagination viewPagination = new ViewPagination(request, serviceSail.countSailsByBuyer(user.getId()),
				countRecordOnPage);
		model.addAttribute("user", user);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sails", serviceSail.allSailByBuyer(viewPagination.getDBPagination(), user.getId()));
		BuyController.setCountProductBasketInModel(request, model);
		return "user/cabinet";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String editBuyer(@ModelAttribute("newInfo") @Valid BuyerInfo info, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "user/editPrivateData";
		}
		serviceBuyer.editBuyer(serviceBuyer.getBuyer(CurrentUser.getName()), info);
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model, HttpServletRequest request) {
		model.addAttribute("password", new Password());
		return "user/changePassword";
	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public String confChangePassword(@ModelAttribute("password") Password password,
			@RequestParam("oldPassword") String oldPass, BindingResult result, HttpServletRequest request) {
		valid.validate(password, result);
		if (result.hasErrors())
			return "user/changePassword";
		if (!serviceBuyer.checkEqualsOldPasswords(serviceBuyer.getBuyer(CurrentUser.getName()).getPassword(), oldPass)) {
			request.setAttribute("validEq", "Not correct old password");
			return "user/changePassword";
		}
		serviceBuyer.editPasswordBuyer(serviceBuyer.getBuyer(CurrentUser.getName()),
				password.getNewPassword());
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/saveAva", method = RequestMethod.POST)
	public String saveAva(Model model, HttpServletRequest request, SessionStatus status) {
		Buyer user = serviceBuyer.getBuyer(CurrentUser.getName());
		String picName = (String) request.getSession().getAttribute("ava");
		user.getInfo().setAva(setting.getPathUploadAva() + "\\" + picName);
		serviceBuyer.editBuyer(user);
		request.getSession().removeAttribute("ava");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/uploadAva", method = RequestMethod.GET)
	public String uploadAvaGet(Model model, HttpServletRequest request) {
		model.addAttribute("user", serviceBuyer.getBuyer(CurrentUser.getName()));
		return "user/uploadAva";
	}

	@RequestMapping(value = "/user/uploadAva", method = RequestMethod.POST)
	public String uploadAvaPost(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request)
			throws IllegalStateException, IOException {
		if (LoadFileUtil.checkExtension(file, FileType.IMAGE)) {
			LoadFileUtil.storeToFileWithOriginalName(file, setting.getPathUploadAva());
			request.getSession().setAttribute("ava", file.getOriginalFilename());
		}
		return "user/uploadAva";
	}

	@RequestMapping(value = "/user/generateInviteLink", method = RequestMethod.GET)
	public String generateLink(Model model,HttpServletRequest request) {
		BuyController.setCountProductBasketInModel(request, model);
		return "user/inviteLink";
	}

	@RequestMapping(value = "/user/generateInviteLink", method = RequestMethod.POST)
	public String generateLink(@RequestParam("ancor") String ancor,
							   @RequestParam("tracker") String tracker, Model model,HttpServletRequest request) {
		Buyer b  = serviceBuyer.getBuyer(CurrentUser.getName());
		String link = "http://localhost:8080/site/reg/"+b.getRefCode() ;
		if (tracker != "") link = link + "&tracker=" + tracker;
		if (ancor != "") link = "&lt;a href=\""+link+"\"&gt;"+ancor+"&lt;/a&gt;";
		model.addAttribute("inviteLink", link);
		BuyController.setCountProductBasketInModel(request, model);
		return "user/inviteLink";
	}
}
