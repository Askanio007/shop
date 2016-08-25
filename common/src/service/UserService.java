package service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDAO;
import entity.Role;
import entity.User;
import utils.PaginationFilter;

@Service
public class UserService {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDao;

	@Autowired
	private BuyerService serviceBuyer;
	
	@Autowired
	private RoleService serviceRole;

	@Transactional
	public void addUser(User user, Long roleId) {
		Role r = serviceRole.getRole(roleId);
		user.setRole(r);
		user.setEnable(true);
		user.setPassword(serviceBuyer.getHashPassword(user.getPassword()));
		r.setUser(user);
		userDao.add(user);
	}

	@Transactional
	public List<User> listUser(PaginationFilter pagination) {
		List<User> list = userDao.find(pagination);
		for (User u : list) {
			Hibernate.initialize(u.getRole());
		}
		return userDao.find(pagination);
	}

	@Transactional
	public User getAdmin() {
		return userDao.getAdmin();
	}

	@Transactional
	public void deleteUser(Long userId) {
		userDao.delete(userId);
	}

	@Transactional
	public int getCountAllUsers() {
		return userDao.countAll();
	}
	
	@Transactional
	public List<User> allUsersByRole(Role r) {
		return userDao.allUserByRole(r);
	}

}
