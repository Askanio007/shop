package view;

import entity.Message;

import java.util.Date;

public class ChatView {

	private final Date date;
	private final String text;
	private final String from;

	public ChatView(Message c, String from) {
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
