package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.service.PostService;
import com.example.finalprojectscit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public String getCreateNewPostPage() {
        return "post/new-post";
    }

    @PostMapping("/new")
    public String sendPostCreationRequest(@RequestParam("title") String title,
                                          @RequestParam("category") String category,
                                          @RequestParam("description") String description,
                                          @RequestParam("post_date") LocalDate date) {
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

    //view - all posts of a user
    @GetMapping("/my-posts")
    public String getAllCurrentUserPosts(Model model) {
        User user = userService.findCurrentUser();
        List<Post> allUPosts = postService.findAllPostsOfAUser(user);
        model.addAttribute("allUPosts", allUPosts);
        return "post/my-posts";
    }


    @GetMapping("/edit/{id}")
    public String getEditPostPage(@PathVariable("id") int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "post/edit-post";
    }


    //send update request
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String sendEditRequest(@PathVariable("id") int id, @ModelAttribute Post post) {
        Post existingPost = postService.findById(id);

        existingPost.setTitle(post.getTitle());
        existingPost.setCategory(post.getCategory());
        existingPost.setDescription(post.getDescription());

        postService.updatePost(existingPost);
        return "redirect:/my-posts";
    }

    //like a post
    @PostMapping("/like/{postId}")
    public String likePost(@PathVariable int postId) {
        User currentUser = userService.findCurrentUser();
        Post post = postService.findById(postId);

        postService.likePost(post, currentUser);

        //todo: do something better - not refresh
        return "redirect:/dashboard";
    }

    //getAllSortedByNewest
    @GetMapping("/dashboard/newest")
    public String getByNewest(Model model){
        List<Post> allPosts = postService.displayByNewest();
        model.addAttribute("allPosts", allPosts);
        return "dashboard";
    }

    @GetMapping("/dashboard/get_that_contains")
    public String getByTitleMatch(@RequestParam("searchWord")String searchWord, Model model){
        List<Post> allPosts = postService.searchByTitle(searchWord);
        model.addAttribute("allPosts", allPosts);
        return "dashboard";
    }


}
