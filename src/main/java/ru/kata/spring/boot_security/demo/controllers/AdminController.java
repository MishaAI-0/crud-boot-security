package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showUsers(Model model, Principal principal) {
        String login = principal.getName();
        User currentUser = userService.getUserByName(login);
        List<User> allUsers = userService.getUsersList();
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("allRoles", allRoles);
        return "admin";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("newUser") User user,@RequestParam("selectedRole") String role) {

        List<Role> resultRoles = new ArrayList<>();

        role = "ROLE_" + role;
        resultRoles.add(roleService.findRoleByName(role));
        if(role.equals("ROLE_ADMIN")) {
            resultRoles.add(roleService.findRoleByName("ROLE_USER"));
        }
        user.setRoles(new HashSet<>(resultRoles));
        userService.saveUser(user);
        return "redirect:/admin";
    }



    @PatchMapping("/update")
    public String createUser(@RequestParam("updatedRole") String role,
                             @RequestParam("id") Long id,
                             @RequestParam("username") String username,
                             @RequestParam("surname") String surname,
                             @RequestParam("age") int age,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        List<Role> resultRoles = new ArrayList<>();
        role = "ROLE_" + role;
        resultRoles.add(roleService.findRoleByName(role));
        if(role.equals("ROLE_ADMIN")) {
            resultRoles.add(roleService.findRoleByName("ROLE_USER"));
        }
        User user = userService.getUserById(id);
        user.setUsername(username);
        user.setSurname(surname);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(new HashSet<>(resultRoles));
        userService.saveUser(user);
        return "redirect:/admin";
    }





    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("deleteUserId") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
