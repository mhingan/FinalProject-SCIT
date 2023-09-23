package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
//TODO: ADD USER A NEW FIELD: DEACTIVATED-TO CAN DELETE THE USER
//todo: make a deactivate account request page for admin

@Controller
public class AdminController {
    //admin-panel (see users, statistics)
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public AdminController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @GetMapping("/admin/panel")
    public String getAdminHomePage() {
        return "admin/admin-panel";
    }


    //get users page
    @GetMapping("/admin/active-users")
    public String getAllActiveUsers(Model model) {
        //if(user.isActive)...
        List<User> all = userRepository.findAll();
        model.addAttribute("all", all);
        return "admin/users";
    }
}
