package dao;

import java.util.List;

import entity.Role;
import entity.User;

public interface UserDAO extends GeneralDAO<User>  {
	
	User get(String name);
	List<User> getByRole(long roleId);

}
