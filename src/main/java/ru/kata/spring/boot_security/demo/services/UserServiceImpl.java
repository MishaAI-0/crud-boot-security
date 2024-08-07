package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).get();
    }


    @Override
    @Transactional
    public List<User> getUsersList() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void saveUser(User user, String role) {

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(modifyRole(role));
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id).get();
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser, String role) {
        Optional<User> existingUserOptional = userRepository.getById(updatedUser.getId());


        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setSurname(updatedUser.getSurname());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRoles(modifyRole(role));


            userRepository.save(existingUser);
        } else {

            throw new IllegalArgumentException("User not found with id: " + updatedUser.getId());
        }
    }

    private Set<Role> modifyRole(String role) {
        List<Role> resultRoles = new ArrayList<>();

        role = "ROLE_" + role;
        resultRoles.add(roleService.findRoleByName(role));
        if (role.equals("ROLE_ADMIN")) {
            resultRoles.add(roleService.findRoleByName("ROLE_USER"));
        }
        return new HashSet<>(resultRoles);
    }


}


