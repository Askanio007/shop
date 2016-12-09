package dto;

import entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {

    private long id;

    private String role;

    private boolean info;

    private boolean addSail;

    private RoleDto() {}

    private RoleDto(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.addSail = role.getAddSail();
        this.info = role.getInfo();
    }

    public static RoleDto convertToDto(Role role) {
        return new RoleDto(role);
    }

    public static List<RoleDto> convertToDto(List<Role> roles) {
        List<RoleDto> rolesDto = new ArrayList<>();
        roles.stream().forEach((p) -> rolesDto.add(convertToDto(p)));
        return rolesDto;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public boolean getInfo() {
        return info;
    }

    public boolean getAddSail() {
        return addSail;
    }
}
