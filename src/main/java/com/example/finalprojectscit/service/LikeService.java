package com.example.finalprojectscit.service;

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
        //1. fac un boolean sa vad daca e like deja
        boolean userAlreadyLiked = false;

        //iterate prin lista de likeuri si daca a dat like setez booleanul la true si ies din lista
        for (Like like : likes) {
            if (like.getUser().getId() == userId) {
                userAlreadyLiked = true;
                break;
            }
        }

        if (userAlreadyLiked) {
            // (presupun ca il scot cand da clikc din nou)
            // dar, totusi, daca a dat like mai sus si e true booleanu, il scot din lista de likeuri
            for (Iterator<Like> iterator = likes.iterator(); iterator.hasNext(); ) {
                Like like = iterator.next();
                if (like.getUser().getId() == userId) {
                    iterator.remove();
                    likeRepository.save(like);
                    //salvez like in db
                }
            }

        } else {
            // daca nu a dat like, adaug in listam
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

