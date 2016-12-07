package entity;

import dto.DiscountDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "Discount")
@Table(name = "discount")
public class Discount {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "buyer_id", nullable = false)
	private Buyer buyer;

	@Column(name = "active")
	private boolean active;

	@Column(name = "discount")
	@NotNull
	@Min(0)
	@Max(100)
	private byte dscnt;

	@Column(name = "product_id")
	private Long productId;

	public Discount() {}

	public Discount(Product product, byte discount) {
		this.productId = product.getId();
		this.dscnt = discount;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public void setDiscount(byte disc) {
		this.dscnt = disc;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public byte getDiscount() {
		return dscnt;
	}

	public Long getProductId() {
		return productId;
	}

	public boolean getActive() {
		return active;
	}

}
