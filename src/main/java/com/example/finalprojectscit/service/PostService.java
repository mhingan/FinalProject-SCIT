package com.example.finalprojectscit.service;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("No post found with id: " + id));
    }

    public List<Post> findByCategory(String category) {
        List<Post> postsInCategory = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            if (post.getCategory().equals(category)) {
                postsInCategory.add(post);
            } else {
                throw new RuntimeException("No posts found in this category: " + category);
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
                throw new RuntimeException("No post with this title found: " + name);
            }
        }
        return postsWitName;
    }

    public void likePost(Post post) {
        int totalLikes;
        if (!post.isLiked()) {
            totalLikes = post.getLikes() + 1;
            post.setLikes(totalLikes);
            post.setLiked(true);
        } else {
            totalLikes = post.getLikes() - 1;
            post.setLikes(totalLikes);
            post.setLiked(false);
        }
        post.setLikes(totalLikes);
    }

}
