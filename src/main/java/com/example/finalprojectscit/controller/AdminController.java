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
    public String getAdminHomePage() {
        return "admin/admin-panel";
    }


    @GetMapping("/admin/active-users")
    public String getAllActiveUsers(Model model) {
        List<User> all = userService.findActiveUsers();
        model.addAttribute("all", all);
        return "admin/users";
    }

    @GetMapping("/admin/inactive-users")
    public String getAllInactiveUsers(Model model) {
        List<User> all = userService.findInactiveUsers();
        model.addAttribute("all", all);
        return "admin/users";
    }

    @GetMapping("/admin/manage/{id}")
    public String getManageUserPage(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomValidationException("No user found"));
        model.addAttribute("user", user);
        return "admin/manage-user-page";
    }

    @PostMapping("/admin/manage/setActive/{id}")
    public String setUserActive(@PathVariable("id") int id) {
        User user = userService.findById(id);
        userService.setUserActive(id);
        return "redirect:/admin/active-users";

    }

    @PostMapping("/admin/manage/setInactive/{id}")
    public String setUserInactive(@PathVariable("id") int id) {
        User user = userService.findById(id);
        userService.setUserInactive(id);
        return "redirect:/admin/inactive-users";
    }

    @RequestMapping(value = "/admin/delete-user/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN-MANAGER')")
    public String deleteUser(@PathVariable("id") int id) {
        userService.findById(id);
        userService.deleteUser();
        return "admin/admin-panel";
    }


}
