package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.MessageDAO;
import entity.Buyer;
import entity.Message;
import utils.PaginationFilter;
import view.ChatView;

@Service
public class MessageService {

	@Autowired
	@Qualifier("messageDao")
	private MessageDAO messageDao;
	
	@Transactional
	public List<ChatView> getViewChat(PaginationFilter dbFilter, Buyer buyer) {
		List<Message> chats = getChat(dbFilter, buyer);
		return getViewChat(chats, buyer);
	}

	// TODO: 16.10.2016 почему это публичный метод? ::: исправил здесь и в других классах
	private List<ChatView> getViewChat(List<Message> list, Buyer buyer) {
		if (list == null)
			return null;
		List<ChatView> viewChat = new ArrayList<>();
		for (Message c : list) {
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
	public int count(Buyer buyer) {
		return messageDao.count(buyer);
	}

	// TODO: 16.10.2016 ::: Я не понял к чему ты клонишь
	//с условием что ид 0 это админ а нулл - система полуается что админ один, то есть это как
	// бы не админ, а очередь админских сообщений.
	// TODO: Kirill клоню к тому, что у тебя сейчас отделены пользователи-покупатели от юзеров-руководства. Buyer и User. Инь и янь. Добро и зло. Ой, что это я...
	// То есть тот, кто создает сообщение как addFromAdmin делает сообщение от некоторой обезличенной службы поддержки. не записывается айди источника сообщения.
	// То есть как то непонятно, кто же клиента на хрен послал. Ну это предложение к улучшени твоей системы сообщений

	@Transactional
	public void addToAdmin(String message, Long from) {
		create(message, from, (long) 0);
	}

	@Transactional
	public void addFromAdmin(String message, Long to) {
		create(message, (long) 0, to);
	}

	@Transactional
	public void addFromSystem(String message, Long to) {
		create(message, null, to);
	}

	private void create(String message, Long from, Long to) {
		messageDao.save(new Message(from, to, message, new Date()));
	}

	@Transactional
	public List<Message> getChat(PaginationFilter dbFilter, Buyer buyer) {
		return messageDao.getChat(dbFilter, buyer);
	}

}
