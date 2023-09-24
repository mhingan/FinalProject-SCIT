package com.example.finalprojectscit.service;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public StatisticsService(UserRepository userRepository, PostRepository postRepository, UserService userService, PostService postService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userService = userService;
        this.postService = postService;
    }

    //Statistics for user:
    //all users (active&inactive)
    public int allUsers() {
        return userRepository.findAll().size();
    }

    //all active users
    public int allActiveUsers() {
        return userService.findActiveUsers().size();
    }

    //all inactive users
    public int allInactiveUsers() {
        return userService.findInactiveUsers().size();
    }

    //todo: think twice daca sa pun sau nu asta (prea mult de implementat cu controller si html)
    public int getUsersWithRank(int rank) {
        List<User> activeUsers = userService.findActiveUsers();
        List<User> usersWithRank = new ArrayList<>();
        for (User user : activeUsers) {
            if (user.getRanking() == rank) {
                usersWithRank.add(user);
            } else throw new CustomValidationException("No users with rank " + rank + " were found.");
        }
        return usersWithRank.size();
    }

    //Statistics for post:
    //all posts
    public int allPosts() {
        return postService.findAll().size();
    }

    //all today's posts
    public int allTodaysPosts() {
        LocalDate today = LocalDate.now();
        List<Post> all = postService.findAll();
        List<Post> todayPosts = new ArrayList<>();

        for (Post post : all) {
            if (post.getPost_date().equals(today)) {
                todayPosts.add(post);
            }
        }
        return todayPosts.size();
    }

    //todo: check if is working, else delete it
    //media postarilor pe zi
    public int postsAnnualMedia() {
        int media = 0;
        int currentYear = LocalDate.now().getYear();
        List<Post> all = postService.findAll();
        List<Post> thisYear = new ArrayList<>();
        for (Post post : all) {
            if (post.getPost_date().getYear() == currentYear) {
                thisYear.add(post);
            }

        }
        return thisYear.size() % 365;
    }

}
