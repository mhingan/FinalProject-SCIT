package com.example.finalprojectscit.service;

import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public StatisticsService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    //todo: getAllNumberOfPosts
    //todo:change user rank to rise by how many posts user have
    //todo: get users with rank ....
    //todo: getAllPostsWithDate....
    //todo:get all active users
    //todo: get all inactive users
    //todo:
}
