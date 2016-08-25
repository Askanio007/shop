package entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "buyer")
public class Buyer {
	
	@Id
	@Column(name = "buyer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	@Size(min = 2, max=30)
	@NotNull
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "enable")
	private boolean enable;

	@Column(name = "reg_date")
	private Date dateReg;

	@Column(name = "ref_id")
	private Long refId;

	@Column(name = "ref_code")
	private String refCode;

	@Column(name = "tracker")
	private String tracker;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "percent_cashback")
	private Integer percentCashback;
	
	@ManyToMany(mappedBy = "buyers", fetch = FetchType.LAZY)
	private Collection<Sail> sails;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private Collection<ClickByLink> clicks;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private BuyerInfo info;
	
	public Buyer(String name, String pass, BuyerInfo info) {
		this.name = name;
		this.password = pass;
		this.info = info;
	}
	
	public Buyer(String name, String pass, Date dateReg) {
		this.name = name;
		this.password = pass;
		this.dateReg = dateReg;
		this.enable = true;
	}
	
	public Buyer(Builder builder) {
		this.name = builder.name;
		this.password = builder.password;
		this.dateReg = builder.dateReg;
		this.enable = builder.enable;
		this.balance = builder.balance;
		this.percentCashback = builder.percentCashback;
		this.refCode = builder.refCode;
		this.refId = builder.refId;
		this.tracker = builder.tracker;
		this.info = builder.info;
		this.info.setBuyer(this);
	}

	public Buyer() {

	}
	
	public static class Builder {
		private String name;
		private String password;
		private Date dateReg;
		private String refCode;
		private BuyerInfo info;
		
		private boolean enable = true;
		private Long refId = null;
		private Double balance = 0.0;
		private Integer percentCashback = 50;
		private String tracker = null;
		
		public Builder(String name, String pass, String code) {
		      this.name = name;
		      this.password = pass;
		      this.dateReg = new Date();
		      this.refCode = code;
		      this.info = new BuyerInfo();
		    }

		public Builder(String name, String pass, String code, Date date) {
			this.name = name;
			this.password = pass;
			this.dateReg = date;
			this.refCode = code;
			this.info = new BuyerInfo();
		}
		
		public Builder refId(Long id){
			this.refId = id;
			return this;
		}
		
		public Builder percentCashback(int percent){
			this.percentCashback = percent;
			return this;
		} 
		
		public Builder tracker(String tracker){
			this.tracker = tracker;
			return this;
		} 
		
		public Builder balance(Double balance){
			this.balance = balance;
			return this;
		} 
		
		public Builder enable(boolean enable){
			this.enable = enable;
			return this;
		} 
		
		public Buyer build() {
			return new Buyer(this);
		}
		
	}


	//SET

	public void setEnable(boolean active) {
		this.enable = active;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public void setSails(Collection<Sail> sailList) {
		sails = sailList;
	}

	public void setInfo(BuyerInfo newInfo) {
		this.info = newInfo;
	}
	
	public void setDateReg(Date date) {
		dateReg = date;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setPercentCashback(int percentCashback) {
		this.percentCashback = percentCashback;
	}

	public void setTracker(String tracker) {
		this.tracker = tracker;
	}

	public void setClicks(Collection<ClickByLink> clicks) {
		this.clicks = clicks;
	}

	//GET


	public Collection<ClickByLink> getClicks() {
		return clicks;
	}

	public String getTracker() {
		return tracker;
	}

	public int getPercentCashback() {
		return percentCashback;
	}

	public Double getBalance() {
		return balance;
	}

	public BuyerInfo getInfo() {
		return info;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Collection<Sail> getSails() {
		return sails;
	}

	public boolean getEnable() {
		return enable;
	}
	
	public Date getDateReg() {
		return dateReg;
	}

	public Long getRefId() {
		return refId;
	}

	public String getRefCode() {
		return refCode;
	}
}
