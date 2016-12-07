package service;

import java.util.List;

import dto.RoleDto;
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

	@Autowired
	private UserService serviceUser;
	
	@Transactional
	public Role getByUserName(String userName) {
		return roleDao.getByUserName(userName);
	}

	@Transactional
	public RoleDto getDtoByUserName(String userName) {
		return RoleDto.convertToDto(getByUserName(userName));
	}

	@Transactional
	public Role get(Long roleId) {
		return roleDao.get(roleId);
	}

	@Transactional
	public RoleDto getDto(Long roleId) {
		return RoleDto.convertToDto(get(roleId));
	}
	
	@Transactional
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Transactional
	public List<RoleDto> getAllDto() {
		return RoleDto.convertToDto(getAll());
	}
	
	@Transactional
	public void edit(RoleDto roleDto) {
		Role role = transferDataToEntity(roleDto);
		role.setUsers(serviceUser.allByRole(roleDto.getId()));
		roleDao.update(role);
	}

	private Role transferDataToEntity(RoleDto roleDto) {
		Role role = get(roleDto.getId());
		role.setAddSail(roleDto.getAddSail());
		role.setInfo(role.getInfo());
		return role;
	}
}
