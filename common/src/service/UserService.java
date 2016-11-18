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
import utils.EncryptionString;
import utils.PaginationFilter;

@Service
public class UserService {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDao;

	@Autowired
	private RoleService serviceRole;

	@Transactional
	public void save(User user, Long roleId) {
		Role r = serviceRole.get(roleId);
		user.setRole(r);
		user.setEnable(true);
		user.setPassword(EncryptionString.toMD5(user.getPassword()));
		r.setUser(user);
		userDao.save(user);
	}

	@Transactional
	public List<User> list(PaginationFilter pagination) {
		List<User> list = userDao.find(pagination);
		for (User u : list) {
			// TODO: Kirill чтоб уж наверняка, а то вдруг Гибернейт не подтянет eager с первого раза сам
			Hibernate.initialize(u.getRole());
		}
		return userDao.find(pagination);
	}

	@Transactional
	public void delete(Long userId) {
		userDao.delete(userId);
	}

	@Transactional
	public int countAll() {
		return userDao.countAll();
	}
	
	@Transactional
	public List<User> allByRole(Role r) {
		return userDao.getByRole(r);
	}

}
