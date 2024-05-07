package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showUsers(Model model) {
        List<User> resultList = userService.getUsersList();
        model.addAttribute("users", resultList);
        return "show-all-users";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roles);
        return "add-new-user";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user, @RequestParam("selectedRoles") List<Role> roles) {
        user.setRoles(new HashSet<>(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/")
    public String editUser(Model model, @RequestParam("userId") int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PatchMapping
    public String update(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
