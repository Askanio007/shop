package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_shop")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "enable")
	private boolean enable;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public User() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public boolean getEnable() {
		return enable;
	}

	public Role getRole() {
		return role;
	}
}
