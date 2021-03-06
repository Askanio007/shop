package controllers;

import dto.BuyerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.BuyerService;
import service.MessageService;
import utils.CurrentUser;
import view.ChatView;
import view.ViewPagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ChatController {
	
	@Autowired
	private MessageService serviceChat;
	
	@Autowired
	private BuyerService serviceBuyer;

	@RequestMapping(value = "/user/chat", method = RequestMethod.GET)
	public String chat(Model model, HttpServletRequest request) {
		BuyerDto buyer = serviceBuyer.getDto(CurrentUser.getName());
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceChat.count(buyer));
		List<ChatView> list = serviceChat.getViewChat(viewPagination.getDBPagination(), buyer);
		model.addAttribute("chat", list);
		model.addAttribute("pagination", viewPagination);
		BuyController.setCountProductBasketInModel(request, model);
		return "user/chat";
	}

	@RequestMapping(value = "/user/addMessage", method = RequestMethod.POST)
	public String addMessage(@RequestParam("message") String text) {
		serviceChat.sendFromBuyer(text, serviceBuyer.get(CurrentUser.getName()));
		return "redirect:/user/chat";
	}

}
