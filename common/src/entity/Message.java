package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Message {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender")
	private Long from;

	@Column(name = "recipient")
	private Long to;

	@Column(name = "text")
	private String text;

	@Column(name = "date")
	private Date date;

	// TODO: 16.10.2016 конструктор необходимый только для гибернейта можно делать приватным или защищенным ::: исправил здесь и в других классах
	private Message() {
	}

	public Message(Long from, Long to, String message, Date date) {
		this.from = from;
		this.to = to;
		this.text = message;
		this.date = date;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public Long getFrom() {
		return from;
	}

	public Long getTo() {
		return to;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

}
