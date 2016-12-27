package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.BuyerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.BuyerService;
import service.MessageService;
import service.UserService;
import utils.CurrentUser;
import view.ChatView;
import view.ViewPagination;

@Controller
public class ChatController {
	
	@Autowired
	private BuyerService serviceBuyer;

	@Autowired
	private MessageService serviceChat;

	@Autowired
	private UserService serviceUser;
	
	@RequestMapping(value = "/buyer/chat/{buyerName}", method = RequestMethod.GET)
	public String chat(@PathVariable("buyerName") String name, Model model, HttpServletRequest request) {
		BuyerDto buyer = serviceBuyer.getDto(name);
		ViewPagination viewPagination = new ViewPagination(request.getParameter(ViewPagination.NAME_PARAM_PAGE), serviceChat.count(buyer));
		List<ChatView> list = serviceChat.getViewChat(viewPagination.getDBPagination(), buyer);
		serviceChat.assignedAdmin(CurrentUser.getName(),buyer);
		model.addAttribute("chatView", list);
		model.addAttribute("pagination", viewPagination);
		model.addAttribute("nameBuyer", name);
		return "buyer/chatAdmin";
	}

	@RequestMapping(value = "/buyer/addMessage", method = RequestMethod.POST)
	public String addMessage(@RequestParam("message") String text, HttpServletRequest request) {
		//параметр name гарантировано существует? TODO он передаётся как параметр, пропадёт, если стереть руками
		String name = request.getParameter("name");
		//buyer точно найдется? TODO точно, только если руками не изменить передаваемое name
		serviceChat.sendFromAdmin(text, serviceBuyer.get(name), serviceUser.find(CurrentUser.getName()));
		return "redirect:/buyer/chat/" + name;
	}

}
