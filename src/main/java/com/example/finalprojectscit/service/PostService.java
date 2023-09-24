package com.example.finalprojectscit.service;

import com.example.finalprojectscit.comparators.PostDateComparator;
import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewPostValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NewPostValidation postValidation;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, NewPostValidation postValidation, UserService userService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postValidation = postValidation;
        this.userService = userService;
    }

    public void createPost(Post post) {
        int id = userService.findCurrentUser().getId();
        postValidation.validatePost(post);
        LocalDate now = LocalDate.now();
        post.setPost_date(now);
        setUserRankWhenPost(id);
        postRepository.save(post);
    }

    private void setUserRankWhenPost(int id) {
        User user = userService.findById(id);
        int newRanking = user.getPosts().size() * 5;
        user.setRanking(newRanking);
        userRepository.save(user);
    }


    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomValidationException("No post found with id: " + id));
    }

    public List<Post> findByCategory(String category) {
        List<Post> postsInCategory = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            if (post.getCategory().equals(category)) {
                postsInCategory.add(post);
            } else {
                throw new CustomValidationException("No posts found in this category: " + category);
            }
        }
        return postsInCategory;
    }

    //TODO - CHECK FUNCTIONALITY
    public List<Post> findByTitle(String name) {
        List<Post> postsWitName = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            if (post.getTitle().matches(name)) {
                postsWitName.add(post);
            } else {
                throw new CustomValidationException("No post with this title found: " + name);
            }
        }
        return postsWitName;
    }


    public List<Post> findAllPostsOfAUser(User user) {
        return user.getPosts();
    }


    public void updatePost(Post existingPost) {
        postValidation.validatePost(existingPost);
        postRepository.save(existingPost);
    }

    public List<Post> displayByNewest() {
        List<Post> allUnsorted = postRepository.findAll();
        Collections.sort(allUnsorted, new PostDateComparator());
        return allUnsorted;
    }


    //search by title
    public List<Post> searchByTitle(String searchWord) {
        List<Post> allPosts = postRepository.findAll();

        List<Post> matchingPosts = allPosts.stream()
                .filter(post -> post.getTitle().toLowerCase().contains(searchWord.toLowerCase()))
                .collect(Collectors.toList());

        return matchingPosts;
    }


}
