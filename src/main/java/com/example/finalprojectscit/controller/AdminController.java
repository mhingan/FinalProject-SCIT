package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public AdminController(UserRepository userRepository, PostRepository postRepository, UserService userService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }


    @GetMapping("/admin/panel")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAdminHomePage() {
        return "admin/admin-panel";
    }


    @GetMapping("/admin/active-users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAllActiveUsers(Model model) {
        List<User> all = userService.findActiveUsers();
        model.addAttribute("all", all);
        return "admin/users";
    }

    @GetMapping("/admin/inactive-users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAllInactiveUsers(Model model) {
        List<User> all = userService.findInactiveUsers();
        model.addAttribute("all", all);
        return "admin/users";
    }

    @GetMapping("/admin/manage/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getManageUserPage(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomValidationException("No user found"));
        model.addAttribute("user", user);
        return "admin/manage-user-page";
    }

    @PostMapping("/admin/manage/setActive/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String setUserActive(@PathVariable("id") int id) {
        User user = userService.findById(id);
        userService.setUserActive(id);
        return "redirect:/admin/active-users";

    }

    @PostMapping("/admin/manage/setInactive/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String setUserInactive(@PathVariable("id") int id) {
        User user = userService.findById(id);
        userService.setUserInactive(id);
        return "redirect:/admin/inactive-users";
    }

    @RequestMapping(value = "/admin/delete-user/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "admin/admin-panel";
    }

    @RequestMapping(value = "/admin/set-user-as-admin/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String changeUserRole(@PathVariable("id") int id) {
        userService.changeUserRole(id);
        return "admin/admin-panel";
    }


}
