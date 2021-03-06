package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dto.ProductDto;
import entity.Product;

import java.math.BigDecimal;

public class ProductBasket{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private long id;

	@JsonProperty("amount")
	private int amount;

	@JsonIgnore
	private BigDecimal cost;
	
	@JsonIgnore
	private byte discount;

	private ProductBasket() {

	}

	public ProductBasket(ProductDto product, int amount, byte discount) {
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

	public void setCost(BigDecimal cost) {
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

	public long getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public BigDecimal getCost() {
		return cost;
	}

}
