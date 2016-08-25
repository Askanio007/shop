package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import entity.Product;

public class ProductBasket{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("amount")
	private int amount;

	@JsonIgnore
	private Double cost;
	
	@JsonIgnore
	private byte discount;

	public ProductBasket() {

	}

	public ProductBasket(Product product, int amount, byte discount) {
		this.id = product.getId();
		this.name = product.getName();
		this.cost = product.getCost();
		this.amount = amount;
		this.discount = discount;
	}

	// SET
	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAmount(int count) {
		this.amount = count;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	

	public void setDiscount(byte discount) {
		this.discount = discount;
	}
	
	// GET
	public String getName() {
		return name;
	}
	
	public byte getDiscount() {
		return discount;
	}

	public Long getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public Double getCost() {
		return cost;
	}

}
