package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "buyer_info")
public class BuyerInfo {

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "buyer") )
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "info_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "second_name")
	private String secondName;

	@Column(name = "ava_path")
	private String ava;

	@Column(name = "age")
	@Max(100)
	private Integer age;

	@Column(name = "phone")
	private String phone;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "info")
	private Buyer buyer;

	public BuyerInfo(String secondName, Integer age, String phone) {
		this.secondName = secondName;
		this.age = age;
		this.phone = phone;
	}

	// TODO: 16.10.2016 суть - какая-то логика в ентити, не имеющая отношения к ентити. да еще и не правильная. ::: убрал логику, она не использовалась нигде, не знаю откуда она вообще
	// полей больше, а присваевается только часть
	
	public BuyerInfo(Buyer buyer) {
		this.buyer = buyer;
	}

	public BuyerInfo() {

	}

	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	public void setAva(String newAva) {
		ava = newAva;
	}

	public void setAge(Integer newAge) {
		age = newAge;
	}

	public void setSecondName(String newSecondName) {
		secondName = newSecondName;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAva() {
		return ava;
	}

	public Integer getAge() {
		return age;
	}

	public Long getId() {
		return id;
	}

	public Buyer getBuyer() {
		return buyer;
	}

}
