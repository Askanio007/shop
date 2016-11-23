package entity;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import models.Basket;
import utils.StateSail;
import view.ViewFormat;

@Entity(name = "Sail")
@Table(name = "sail")
public class Sail {

	@Id
	@Column(name = "sail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	@Column(name = "date")
	@NotNull
	private Date date;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	@Column(name = "date_change_state")
	@NotNull
	private Date dateChangeState;

	@Column(name = "amount")
	private Integer amount;

	// TODO: Kirill для любых действий с деньгами пользоваться необходимо только BigDecimal  ::: исправио везде на BigDecimal
	@Column(name = "totalsum")
	private BigDecimal totalsum;

	@Column(name = "state")
	private String state;

	@Column(name = "cashback_percent")
	private Integer cashbackPercent;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sail_id")
	@LazyCollection(LazyCollectionOption.TRUE)
	@NotNull
	private Collection<SoldProduct> products;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	@NotNull
	private Buyer buyer;// TODO: 16.10.2016 кто это? ::: Это список покупателей. Я изначально сделал так, что одна и та же покупка может быть у нескольких покупателей
	// TODO: Kirill тогда я не понимаю что такое покупка в твоих терминах. думаю что это ерунда какая-то ::: оставил только одного покупателя

	public Sail() {

	}

	public Sail(Buyer buyer, List<SoldProduct> products, Basket basket) {
		setBuyer(buyer);
		this.cashbackPercent = buyer.getPercentCashback();
		this.products = products;
		this.amount = basket.countProducts();
		this.totalsum = basket.cost();
		this.date = new Date();
		setStateWithDate(StateSail.getState(StateSail.State.SENT));
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAmount(Integer count) {
		this.amount = count;
	}

	public void setTotalsum(BigDecimal fcount) {
		totalsum = fcount;
	}

	public void setProducts(Collection<SoldProduct> productList) {
		products = productList;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public void setStateWithDate(String state) {
		this.state = state;
		this.dateChangeState = new Date();
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDateChangeState(Date dateChangeState) {
		this.dateChangeState = dateChangeState;
	}

	public void setCashbackPercent(int cashbackPercent) {
		this.cashbackPercent = cashbackPercent;
	}

	// GET

	public Date getDateChangeState() {
		return dateChangeState;
	}

	public String getState() {
		return state;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Integer getAmount() {
		return amount;
	}

	public BigDecimal getTotalsum() {
		return totalsum;
	}

	public Collection<SoldProduct> getProducts() {
		return products;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public int getCashbackPercent() {
		return cashbackPercent;
	}
}
