/**
 * @author Mihaita Hingan
 */
package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.service.LikeService;
import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;

    @Autowired
    public PostController(UserService userService, PostService postService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
    }


    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getCreateNewPostPage() {
        return "post/new-post";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String sendPostCreationRequest(@RequestParam("title") String title,
                                          @RequestParam("category") String category,
                                          @RequestParam("description") String description
    ) {
        User user = userService.findCurrentUser();
        Post post = Post.builder()
                .title(title)
                .category(category)
                .description(description)
                .user(user)
                .build();

        postService.createPost(post);

        return "redirect:dashboard";
    }

    @GetMapping("/my-posts")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getAllCurrentUserPosts(Model model) {
        User user = userService.findCurrentUser();
        List<Post> allUPosts = postService.findAllPostsOfAUser(user);
        model.addAttribute("allUPosts", allUPosts);
        return "post/my-posts";
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getEditPostPage(@PathVariable("id") int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "post/edit-post";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('USER')")
    public String sendEditRequest(@PathVariable("id") int id, @ModelAttribute Post post) {
        Post existingPost = postService.findById(id);

        existingPost.setTitle(post.getTitle());
        existingPost.setCategory(post.getCategory());
        existingPost.setDescription(post.getDescription());

        postService.updatePost(existingPost);
        return "redirect:/my-posts";
    }

    @GetMapping("/dashboard/newest")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getByNewest(Model model) {
        List<Post> allPosts = postService.displayByNewest();
        likeService.getAllLikes();
        model.addAttribute("allPosts", allPosts);
        return "dashboard";
    }

    @GetMapping("/dashboard/get_that_contains")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getByTitleMatch(@RequestParam("searchWord") String searchWord, Model model) {
        List<Post> allPosts = postService.searchByTitle(searchWord);
        likeService.getAllLikes();
        model.addAttribute("allPosts", allPosts);
        return "dashboard";
    }

    @GetMapping("/dashboard/category")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getByCategory(@RequestParam("category") String category, Model model) {
        List<Post> allPosts = postService.getByCategory(category);
        likeService.getAllLikes();
        model.addAttribute("allPosts", allPosts);
        return "dashboard";
    }


}
