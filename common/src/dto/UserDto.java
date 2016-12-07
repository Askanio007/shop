package dto;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private long id;

    private String name;

    private String password;

    private boolean enable;

    private String role;

    public UserDto() {}

    private UserDto(User user) {
        this.id = user.getId();
        this.enable = user.getEnable();
        this.name = user.getName();
        this.role = user.getRole().getRole();
        this.password = user.getPassword();
    }

    public static UserDto convertToDto(User entity) {
        return new UserDto(entity);
    }

    public static List<UserDto> convertToDto(List<User> entities) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : entities) {
            dtos.add(convertToDto(user));
        }
        return dtos;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getEnable() {
        return enable;
    }

    public String getRole() {
        return role;
    }
}
