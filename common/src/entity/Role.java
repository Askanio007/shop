package entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role")
	private String role;

	@Column(name = "info")
	private boolean info;

	@Column(name = "add_sail")
	private boolean addSail;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Collection<User> users;

	public void setId(Long id) {
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public void setUser(User user) {
		if (users != null)
			users.add(user);
		else {
			users = new ArrayList<User>();
			users.add(user);
		}
	}

	public void setInfo(boolean info) {
		this.info = info;
	}

	public void setAddSail(boolean add) {
		this.addSail = add;
	}

	public boolean getAddSail() {
		return addSail;
	}

	public boolean getInfo() {
		return info;
	}

	public String getRole() {
		return role;
	}

	public Long getId() {
		return id;
	}

	public Collection<User> getUsers() {
		return users;
	}

}
