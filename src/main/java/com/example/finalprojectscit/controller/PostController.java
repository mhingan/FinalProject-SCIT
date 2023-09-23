package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    //get - create a new post page
    @GetMapping("/new")
    public String getCreateNewPostPage(){
        return "post/new-post";
    }

    @PostMapping("/new")
    public String sendPostCreationRequest(@RequestParam("title")String title,
                                          @RequestParam("category")String category,
                                          @RequestParam("description")String description,
                                          @RequestParam("post_date")LocalDate date){
        User user = userService.findCurrentUser();
        Post post = Post.builder()
                .title(title)
                .category(category)
                .description(description)
                .post_date(date)
                .user(user)
                .build();

        postService.createPost(post);

//        return "post/my-posts";
        //todo: chanage return file
        return "index";
    }
}
