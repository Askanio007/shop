package dao;

import java.util.List;

import entity.Role;
import entity.User;

public interface UserDAO extends GeneralDAO<User>  {
	
	User getUser(String name);
	User getAdmin();
	List<User> allUserByRole(Role role);

}
