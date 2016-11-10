package dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import entity.Role;

@Repository("roleDAO")
public class RoleDAOImpl extends GeneralDAOImpl<Role> implements RoleDAO{

	@Override
	public Role getByUserName(String name) {
		return (Role) createQuery("select role from User as u inner join u.role as role where name = :name")
				.setString("name", name)
				.uniqueResult();
	}
	
	@Override
	public Role get(Long id) {
		return (Role) createQuery("from Role where id = :id")
					.setLong("id", id)
					.uniqueResult();
	}

	@Override
	public List<Role> getAll() {
		return find();
	}
}
