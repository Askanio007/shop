package service;

import java.util.List;

import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDAO;
import entity.Role;
import entity.User;
import utils.HashString;
import utils.PaginationFilter;

@Service
public class UserService {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDao;

	@Autowired
	private RoleService serviceRole;

	@Transactional
	public void save(UserDto userDto, Long roleId) {
		Role role = serviceRole.get(roleId);
		User user = convertToEntity(userDto, role);
		role.setUser(user);
		userDao.save(user);
	}

	private User convertToEntity(UserDto dto, Role role) {
		User user = new User();
		user.setEnable(true);
		user.setName(dto.getName());
		user.setPassword(HashString.toMD5(dto.getPassword()));
		user.setRole(role);
		return user;
	}

	@Transactional
	public List<User> list(PaginationFilter pagination) {
		// TODO: Kirill чтоб уж наверняка, а то вдруг Гибернейт не подтянет eager с первого раза сам ::: затуп) убрал инициализацию связей
		return userDao.find(pagination);
	}

	@Transactional
	public List<UserDto> listDto(PaginationFilter pagination) {
		return UserDto.convertToDto(list(pagination));
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
	public List<User> allByRole(long roleId) {
		return userDao.getByRole(roleId);
	}

	@Transactional
	public User find(String name) {
		return userDao.get(name);
	}

}
