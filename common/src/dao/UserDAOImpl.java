package dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import entity.Role;
import entity.User;

@Repository("userDAO")
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {

	@Override
	public User get(String name) {
		return (User)createQuery("from User where name = :nameUser")
					.setString("nameUser", name)
					.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getByRole(Role role) {
		return createQuery("from User where role.id = :id")
					.setLong("id", role.getId())
					.list();
	}
}
