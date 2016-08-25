package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.ChatDAO;
import entity.Buyer;
import entity.Chat;
import utils.PaginationFilter;
import view.ChatView;

@Service
public class ChatService {

	@Autowired
	@Qualifier("chatDAO")
	private ChatDAO chatDao;
	
	@Transactional
	public List<ChatView> getViewChat(PaginationFilter dbFilter, Buyer buyer) {
		List<Chat> chats = getChat(dbFilter, buyer);
		return getViewChat(chats, buyer);
	}

	public List<ChatView> getViewChat(List<Chat> list, Buyer buyer) {
		if (list == null)
			return null;
		List<ChatView> viewChat = new ArrayList<>();
		for (Chat c : list) {
			if (c.getFrom() == null) {
				viewChat.add(new ChatView(c, "SYSTEM"));
				continue;
			}

			if (c.getFrom() == 0) {
				viewChat.add(new ChatView(c, "Admin"));
				continue;
			}
			if (c.getFrom() != 0 && c.getFrom() != null) {
				viewChat.add(new ChatView(c, buyer.getName()));
				continue;
			}

		}
		return viewChat;
	}

	@Transactional
	public int countRecordChat(Buyer buyer) {
		return chatDao.count(buyer);
	}

	@Transactional
	public void addMessageToAdmin(String message, Long from) {
		createMessage(message, from, (long) 0);
	}

	@Transactional
	public void addMessageFromAdmin(String message, Long to) {
		createMessage(message, (long) 0, to);
	}

	@Transactional
	public void addMessageFromSystem(String message, Long to) {
		createMessage(message, null, to);
	}

	public void createMessage(String message, Long from, Long to) {
		chatDao.add(new Chat(from, to, message, new Date()));
	}

	@Transactional
	public List<Chat> getChat(PaginationFilter dbFilter, Buyer buyer) {
		return chatDao.getChat(dbFilter, buyer);
	}

}
