package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showUsers(Model model, Principal principal) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", userService.getUsersList());
        model.addAttribute("currentUser", userService.getUserByName(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("newUser") User user, @RequestParam("selectedRole") String role) {
        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String createUser(@RequestParam("updatedRole") String role, @ModelAttribute User user) {
        userService.updateUser(user, role);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("deleteUserId") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
