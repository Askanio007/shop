package dao;

import java.util.List;

import entity.Role;

public interface RoleDAO extends GeneralDAO<Role> {
	
	Role getByUserName(String name);
	Role get(Long id);
	List<Role> getAll();

}
