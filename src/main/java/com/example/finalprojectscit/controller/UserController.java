package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String getIndexLogout() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {

        List<Post> allPosts = postService.findAll();
        model.addAttribute("allPosts", allPosts);

        return "dashboard";
    }


    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String sendSignUpRequest(@RequestParam("first_name") String first_name,
                                    @RequestParam("last_name") String last_name,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password) {

        User user = User.builder()
                .first_name(first_name)
                .last_name(last_name)
                .email(email)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .build();

        userService.createUser(user);

        return "redirect:/login";
    }


    @GetMapping("/my-profile")
    public String getProfilePage(Model model) {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
    public String sendUpdateDataRequest(@ModelAttribute User user) {
        User existingUser = userService.findCurrentUser();

        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setEmail(user.getEmail());

        userService.update(existingUser);
        return "redirect:/my-profile";
    }


}
