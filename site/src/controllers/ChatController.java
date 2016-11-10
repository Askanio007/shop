package controllers;

import entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
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
	public String chat(Model model, HttpServletRequest request, SessionStatus status) {
		Buyer buyer = serviceBuyer.get(CurrentUser.getName());
		ViewPagination viewPagination = new ViewPagination(request, serviceChat.count(buyer));
		List<ChatView> list = serviceChat.getViewChat(viewPagination.getDBPagination(), buyer);
		model.addAttribute("chat", list);
		model.addAttribute("pagination", viewPagination);
		BuyController.setCountProductBasketInModel(request, model);
		return "user/chat";
	}

	@RequestMapping(value = "/user/addMessage", method = RequestMethod.POST)
	public String addMessage(@RequestParam("message") String text,HttpServletRequest request) {
		serviceChat.addToAdmin(text, serviceBuyer.get(CurrentUser.getName()).getId());
		return "redirect:/user/chat";
	}

}
