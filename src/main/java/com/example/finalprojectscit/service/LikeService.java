package com.example.finalprojectscit.service;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Like;
import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }


    public void likePost(Post post, User user) {
        int userId = user.getId();
        int postId = post.getId();
        List<Like> likes = post.getLikes();
        boolean userAlreadyLiked = false;

        for (Like like : likes) {
            if (like.getUser().getId() == userId) {
                userAlreadyLiked = true;
                break;
            }
        }
        //todo: not working
        if (userAlreadyLiked) {
            for (Like like : likes) {
                likes.remove(like);
            }
        } else {
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            likes.add(newLike);
            likeRepository.save(newLike);
        }

        postService.updatePost(post);
    }


    public int getAllLikes(Post post) {
        return post.getLikes().size();
    }

}

