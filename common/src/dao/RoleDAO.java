package dao;

import java.util.List;

import entity.Role;

public interface RoleDAO extends GeneralDAO<Role> {
	
	Role getRoleByUserName(String name);
	Role getUserRole(Long id);
	List<Role> getAllRole();

}
