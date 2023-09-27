package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.service.LikeService;
import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;

    @Autowired
    public UserController(UserService userService, PostService postService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
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
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getDashboard(Model model) {
        List<Post> allPosts = postService.findAll();

        //nu am gasit alta metoda decat sa pun for-ul asta aici
        //todo: sa mai caut alte metode
        for (Post post : allPosts) {
           int likes =likeService.getAllLikes(post);
            post.setFavorites(likes);
        }

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
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getProfilePage(Model model) {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('USER')")
    public String sendUpdateDataRequest(@ModelAttribute User user) {
        User existingUser = userService.findCurrentUser();

        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setEmail(user.getEmail());

        userService.update(existingUser);
        return "redirect:/my-profile";
    }


    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable("postId") int postId) {
        User user = userService.findCurrentUser();
        Post post = postService.findById(postId);

        try {
            likeService.likePost(post, user);
        } catch (CustomValidationException customValidationException) {
            return "admin/error/general-error";
        }

        return "redirect:/dashboard";
    }



}
