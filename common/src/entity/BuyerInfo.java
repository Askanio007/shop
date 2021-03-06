package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Check;
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
	@Check(constraints = "age >= 1 AND age <= 100")
	@Max(100)
	private Integer age;

	@Column(name = "phone")
	private String phone;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "info")
	private Buyer buyer;

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
