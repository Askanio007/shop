package view;

import entity.Chat;

import java.util.Date;

public class ChatView {

	private Date date;

	private String text;

	private String from;

	public ChatView(Chat c, String from) {
		this.date = c.getDate();
		this.from = from;
		this.text = c.getText();
	}

	public String getFrom() {
		return from;
	}


	public Date getDate() {
		return date;
	}

	public String getText() {
		return text;
	}


}
