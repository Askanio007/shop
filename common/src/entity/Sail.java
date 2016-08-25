package entity;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import models.Basket;
import utils.StateSail;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
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

	@Column(name = "totalsum")
	private Double totalsum;

	@Column(name = "state")
	private String state;

	@Column(name = "cashback_percent")
	private Integer cashbackPercent;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sail_id")
	@LazyCollection(LazyCollectionOption.TRUE)
	@NotNull
	private Collection<SoldProduct> products;

	@JoinTable(name = "sail_by_buyer", joinColumns = {
			@JoinColumn(name = "sail_id", referencedColumnName = "sail_id") }, inverseJoinColumns = {
					@JoinColumn(name = "buyer_id", referencedColumnName = "buyer_id") })
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	@NotNull
	private Collection<Buyer> buyers;

	public Sail() {

	}

	public Sail(List<Buyer> buyers, List<SoldProduct> products, Basket basket) {
		this.buyers = buyers;
		this.cashbackPercent = buyers.get(0).getPercentCashback();
		this.products = products;
		this.amount = basket.countProducts();
		this.totalsum = basket.cost();
		this.date = new Date();
		setStateWithDate(StateSail.getState(StateSail.State.SENT));
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

	public void setTotalsum(Double fcount) {
		totalsum = fcount;
	}

	public void setProducts(Collection<SoldProduct> productList) {
		products = productList;
	}

	public void setBuyers(Collection<Buyer> buyerList) {
		buyers = buyerList;
	}

	public void setBuyer(Buyer buyer) {
		this.buyers = Arrays.asList(buyer);
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

	public Double getTotalsum() {
		return totalsum;
	}

	public Collection<SoldProduct> getProducts() {
		return products;
	}

	public Collection<Buyer> getBuyers() {
		return buyers;
	}

	public int getCashbackPercent() {
		return cashbackPercent;
	}

	public String getViewTotalsum()
	{
		return String.format("%.2f", this.totalsum);
	}



}
