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

	// TODO: 16.10.2016 Это тоже не часть доступного апи. никто не знает о твое волшебной схеме выше. тельзя так просто ::: Исправил
	// взять и написать сообщение от одного к другому. и как одно и тоже действие
	// превращается из "добавить сообщение" в "создать сообщение"

	private void create(String message, Long from, Long to) {
		messageDao.save(new Message(from, to, message, new Date()));
	}

	// TODO: 16.10.2016 chat - это беседа состоящая из сообщение. это понятно и логично. Ты изначально взял понятие ::: Изменил названия
	// для одного сообщение чат и теперь никто не может понять что я получу в методе getChat после того как "добавил"
	// или "создал", ктож разберет несколько Message
	@Transactional
	public List<Message> getChat(PaginationFilter dbFilter, Buyer buyer) {
		return messageDao.getChat(dbFilter, buyer);
	}

}
