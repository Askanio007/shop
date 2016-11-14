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
import utils.LinkBuilder;
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
	public String editBuyerPage(Model model) {
		Buyer buyer = serviceBuyer.get(CurrentUser.getName());
		model.addAttribute("info", buyer.getInfo());
		model.addAttribute("newInfo", new BuyerInfo());
		return "user/editPrivateData";
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String cabinetUser(Model model, HttpServletRequest request) {
		Buyer user = serviceBuyer.getFullInfo(CurrentUser.getName());
		ViewPagination viewPagination = new ViewPagination(request, serviceSail.countByBuyer(user.getId()), countRecordOnPage);
		model.addAttribute("user", user);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("sails", serviceSail.allByBuyer(viewPagination.getDBPagination(), user.getId()));
		BuyController.setCountProductBasketInModel(request, model);
		return "user/cabinet";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String editBuyer(@ModelAttribute("newInfo") @Valid BuyerInfo info, BindingResult result) {
		if (result.hasErrors()) {
			return "user/editPrivateData";
		}
		serviceBuyer.edit(serviceBuyer.get(CurrentUser.getName()), info);
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		model.addAttribute("password", new Password());
		return "user/changePassword";
	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public String confChangePassword(@ModelAttribute("password") Password password,
									 @RequestParam("oldPassword") String oldPass,
									 BindingResult result, HttpServletRequest request) {
		valid.validate(password, result);
		if (result.hasErrors())
			return "user/changePassword";
		if (!serviceBuyer.checkEqualsOldPasswords(serviceBuyer.get(CurrentUser.getName()).getPassword(), oldPass)) {
			request.setAttribute("validEq", "Not correct old password");
			return "user/changePassword";
		}
		serviceBuyer.editPassword(serviceBuyer.get(CurrentUser.getName()), password.getNewPassword());
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/saveAva", method = RequestMethod.POST)
	public String saveAva(HttpServletRequest request) {
		serviceBuyer.saveAvatar(CurrentUser.getName(), (String) request.getSession().getAttribute("ava"));
		request.getSession().removeAttribute("ava");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/uploadAva", method = RequestMethod.GET)
	public String uploadAva(Model model) {
		model.addAttribute("user", serviceBuyer.get(CurrentUser.getName()));
		return "user/uploadAva";
	}

	@RequestMapping(value = "/user/uploadAva", method = RequestMethod.POST)
	public String uploadAva(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
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
							   @RequestParam("tracker") String tracker,
							   Model model, HttpServletRequest request) {
		BuyController.setCountProductBasketInModel(request, model);
		model.addAttribute("inviteLink", LinkBuilder.buildReferralLink(serviceBuyer.get(CurrentUser.getName()), tracker, ancor,request));
		return "user/inviteLink";
	}
}
