package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestUsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> usersList = userService.getUsersList();
        return ResponseEntity.ok(usersList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(UserUtil.mapUserToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(UserUtil.mapUserDTOToUser(userDTO));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(userDTO.getId(), userDTO);
        UserDTO updatedUserDTO = UserUtil.mapUserToUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();

    }

}
