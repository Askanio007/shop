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
	public Role getByUserName(String userName) {
		return roleDao.getByUserName(userName);
	}

	@Transactional
	public Role get(Long roleId) {
		return roleDao.get(roleId);
	}
	
	@Transactional
	public List<Role> getAll() {
		return roleDao.getAll();
	}
	
	@Transactional
	public void edit(Role r) {
		roleDao.update(r);
	}
}
