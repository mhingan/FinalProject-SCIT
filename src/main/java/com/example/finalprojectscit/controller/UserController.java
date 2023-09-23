package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    //index: logout
    @GetMapping("/")
    public String getIndexLogout(){
        return "index";
    }

    //login-get
    @GetMapping("/login")
    public String getLoginPage(){
        return "dashboard";
    }


}
