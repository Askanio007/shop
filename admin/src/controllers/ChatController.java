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
import service.BuyerService;
import service.MessageService;
import view.ChatView;
import view.ViewPagination;

@Controller
public class ChatController {
	
	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private MessageService serviceChat;
	
	@RequestMapping(value = "/buyer/chat/{buyerName}", method = RequestMethod.GET)
	public String chat(@PathVariable("buyerName") String name, Model model, HttpServletRequest request) {
		Buyer buyer = serviceBuyer.get(name);
		ViewPagination viewPagination = new ViewPagination(request, serviceChat.count(buyer));
		List<ChatView> list = serviceChat.getViewChat(viewPagination.getDBPagination(), buyer);
		model.addAttribute("chatView", list);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("nameBuyer", name);
		return "buyer/chatAdmin";
	}

	@RequestMapping(value = "/buyer/addMessage", method = RequestMethod.POST)
	public String addMessage(@RequestParam("message") String text, HttpServletRequest request) {
		//параметр name гарантировано существует? TODO он передаётся как параметр, пропадёт, если стереть руками
		String name = request.getParameter("name");
		Buyer buyer = serviceBuyer.get(name);
		//buyer точно найдется? TODO точно, только если руками не изменить передаваемое name
		serviceChat.addFromAdmin(text, buyer.getId());
		return "redirect:/buyer/chat/" + name;
	}

}
