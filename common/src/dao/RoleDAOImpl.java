package dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import entity.Role;

@Repository("roleDAO")
public class RoleDAOImpl extends GeneralDAOImpl<Role> implements RoleDAO{

	@Override
	public Role getRoleByUserName(String name) {
		int id = asInt(createQuery("select id from User where name = :name")
					.setString("name", name)
					.uniqueResult());
		return (Role) createQuery("from Role where id = :id").setInteger("id", id).uniqueResult();
	}
	
	@Override
	public Role getUserRole(Long id) {
		return (Role) createQuery("from Role where id = :id")
					.setLong("id", id)
					.uniqueResult();
	}

	@Override
	public List<Role> getAllRole() {
		return find();
	}
}
