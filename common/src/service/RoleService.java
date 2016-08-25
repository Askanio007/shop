package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.RoleDAO;
import entity.Role;

@Service
public class RoleService {
	
	@Autowired
	@Qualifier("roleDAO")
	private RoleDAO roleDao;
	
	@Transactional
	public Role getRoleByUserName(String userName) {
		return roleDao.getRoleByUserName(userName);
	}

	@Transactional
	public Role getRole(Long roleId) {
		return roleDao.getUserRole(roleId);
	}
	
	@Transactional
	public List<Role> getAllRole() {
		return roleDao.getAllRole();
	}
	
	@Transactional
	public void editRole(Role r) {
		roleDao.update(r);
	}
}
