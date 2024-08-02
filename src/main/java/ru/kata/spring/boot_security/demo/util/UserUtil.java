package ru.kata.spring.boot_security.demo.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserUtil {

    private static RoleService roleService;


    private RoleService roleServiceBean;

    @PostConstruct
    public void init() {
        roleService = roleServiceBean;
    }

    public static UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setSurname(user.getSurname());
        userDTO.setAge(user.getAge());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles().stream().map(x -> new RoleDTO(x.getName())).collect(Collectors.toSet()));
        return userDTO;
    }

    public static User mapUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles()
                .stream().map(x -> roleService.findRoleByName(x.getName())).collect(Collectors.toSet()));
        return user;
    }

}
