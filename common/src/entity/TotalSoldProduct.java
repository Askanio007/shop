package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import utils.CalculatorDiscount;

import java.math.BigDecimal;

@Entity(name = "TotalSoldProduct")
@Table(name = "total_product_report")
public class TotalSoldProduct {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "total_cost")
	private BigDecimal totalCost;

	@Column(name = "total_amount")
	private Integer totalAmount;

	private TotalSoldProduct() {
	}

	public TotalSoldProduct(SoldProduct soldProduct, Product product) {
		this.product = product;
		this.totalCost = BigDecimal.valueOf(soldProduct.getAmount().doubleValue())
				.multiply(CalculatorDiscount.getCostWithDiscount(soldProduct.getCost(), soldProduct.getDiscount()));
		this.totalAmount = soldProduct.getAmount();
	}
	
	public void addData(SoldProduct soldProduct) {
		this.totalCost.add(BigDecimal.valueOf(soldProduct.getAmount().doubleValue())
				.multiply(CalculatorDiscount.getCostWithDiscount(soldProduct.getCost(), soldProduct.getDiscount())));
		this.totalAmount += soldProduct.getAmount();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

}
