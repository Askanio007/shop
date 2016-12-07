package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import dto.BuyerDto;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.MessageDAO;
import entity.Buyer;
import entity.Message;
import utils.PaginationFilter;
import utils.Sender;
import view.ChatView;

@Service
public class MessageService {

	@Autowired
	@Qualifier("messageDao")
	private MessageDAO messageDao;
	
	@Transactional
	public List<ChatView> getViewChat(PaginationFilter dbFilter, BuyerDto buyer) {
		List<Message> chats = getChat(dbFilter, buyer);
		return getViewChat(chats, buyer);
	}

	// TODO: 16.10.2016 почему это публичный метод? ::: исправил здесь и в других классах
	private List<ChatView> getViewChat(List<Message> list, BuyerDto buyer) {
		if (list == null)
			return null;
		List<ChatView> viewChat = new ArrayList<>();
		for (Message c : list) {
			switch (c.getSender()) {
				case SUPPORT:
					viewChat.add(new ChatView(c, "Admin"));
				case BUYER:
					viewChat.add(new ChatView(c, buyer.getName()));
				case SYSTEM:
					viewChat.add(new ChatView(c, "SYSTEM"));
			}
		}
		return viewChat;
	}

	@Transactional
	public int count(BuyerDto buyer) {
		return messageDao.count(buyer);
	}

	// TODO: 16.10.2016 ::: Я не понял к чему ты клонишь
	//с условием что ид 0 это админ а нулл - система полуается что админ один, то есть это как
	// бы не админ, а очередь админских сообщений.
	// TODO: Kirill клоню к тому, что у тебя сейчас отделены пользователи-покупатели от юзеров-руководства. Buyer и User. Инь и янь. Добро и зло. Ой, что это я...
	// То есть тот, кто создает сообщение как addFromAdmin делает сообщение от некоторой обезличенной службы поддержки. не записывается айди источника сообщения.
	// То есть как то непонятно, кто же клиента на хрен послал. Ну это предложение к улучшени твоей системы сообщений

	@Transactional
	public void sendFromBuyer(String message, Buyer buyer) {
		create(message, buyer, null, Sender.BUYER);
	}

	@Transactional
	public void sendFromAdmin(String message, Buyer buyer, User support) {
		create(message, buyer, support, Sender.SUPPORT);
	}

	@Transactional
	public void sendFromSystem(String message, Buyer buyer) {
		create(message, buyer, null, Sender.SYSTEM);
	}

	@Transactional
	public void sendAll(String text) {
		create(text, null, null, Sender.SYSTEM);
	}

	private void create(String message, Buyer buyer, User support, Sender sender) {
		messageDao.save(new Message(support, buyer, message, new Date(), sender));
	}

	@Transactional
	private List<Message> getChat(PaginationFilter dbFilter, BuyerDto buyer) {
		return messageDao.getChat(dbFilter, buyer);
	}

}
