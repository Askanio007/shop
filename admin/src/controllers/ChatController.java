package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import entity.Buyer;
import entity.Chat;
import service.BuyerService;
import service.ChatService;
import view.ChatView;
import view.ViewPagination;

@Controller
public class ChatController {
	
	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private ChatService serviceChat;
	
	@RequestMapping(value = "/buyer/chat/{buyerName}", method = RequestMethod.GET)
	public String chatBuyer(@PathVariable("buyerName") String name, Model model, HttpServletRequest request, SessionStatus status) {
		Buyer buyer = serviceBuyer.getBuyer(name);
		ViewPagination viewPagination = new ViewPagination(request, serviceChat.countRecordChat(buyer));
		List<ChatView> list = serviceChat.getViewChat(viewPagination.getDBPagination(), buyer);
		model.addAttribute("chatView", list);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("nameBuyer", name);
		return "buyer/chatAdmin";
	}

	@RequestMapping(value = "/buyer/addMessage", method = RequestMethod.POST)
	public String addMessageBuyer(@RequestParam("message") String text, HttpServletRequest request) {
		//параметр name гарантировано существует?
		String name = request.getParameter("name");
		Buyer buyer = serviceBuyer.getBuyer(name);
		//buyer точно найдется?
		serviceChat.addMessageFromAdmin(text, buyer.getId());
		return "redirect:/buyer/chat/" + name;
	}

}
