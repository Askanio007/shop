package entity;

import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "cost", nullable = false)
	@Check(constraints = "cost >= 1")
	@NotNull
	@Min(1)
	private BigDecimal cost;

	// TODO: Kirill чтобы не тиражировать ошибки дальше, так как приложение более ли менее по размеру, а будет так вообще
	// алиэкспрес, делать будем правильно. Ентити в контроллере быть не должно. То есть учитываю что потребители твоих сервисов
	// именно контроллеры, то грубо говоря, сервисы не возвращают ентити. Исключение, если те методы вызываются другими сервисами,
	// однако и там можно рассмотреть конкретные случаи и скорее всего можно будет обойтись DTO объектами.
	// Если не понятно чокак, можешь спросить поподробнее

	// TODO: Artyom добавил классы дто, теперь в контроллерах вся работа происходит с ними.

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<PictureProduct> picList;

	public Product() {
	}

	public Product(String name, BigDecimal cost) {
		this.name = name;
		this.cost = cost;
	}

	public void setName(String newname) {
		name = newname;
	}

	public void setId(Long productId) {
		id = productId;
	}

	public void setCost(BigDecimal newcost) {
		cost = newcost;
	}

	public void setPicList(List<PictureProduct> list) {
		picList = list;
	}

	// GET
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public List<PictureProduct> getPicList() {
		return picList;
	}

}
