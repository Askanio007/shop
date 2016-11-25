package entity;

import utils.Sender;

import java.util.Date;

import javax.persistence.*;

@Entity(name = "Message")
@Table(name = "chat")
public class Message {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "support_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	@Column(name = "text")
	private String text;

	@Column(name = "date")
	private Date date;

	@Column(name = "who_write")
	@Enumerated(EnumType.STRING)
	private Sender sender;

	private Message() {
	}

	public Message(User user, Buyer buyer, String message, Date date, Sender sender) {
		this.user = user;
		this.buyer = buyer;
		this.text = message;
		this.date = date;
		this.sender = sender;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
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

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}
}
