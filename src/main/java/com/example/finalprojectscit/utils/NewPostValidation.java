package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.model.Post;
import org.springframework.stereotype.Component;

@Component
public class NewPostValidation {
    public void validatePost(Post post) {
        validate_title(post);
        validate_description(post);

    }

    private void validate_description(Post post) {
        String description = post.getDescription();
        if (description.isEmpty()) {
            throw new RuntimeException("Description is empty");
        }
        if (description.length() > 1000) {
            throw new RuntimeException("Description is too long");
        }
    }


    private void validate_title(Post post) {
        String postTitle = post.getTitle();
        if (postTitle.isEmpty()) {
            //todo: throw custom exception - for ALL
            throw new RuntimeException("Title is empty");
        }
        if (postTitle.length() > 50) {
            throw new RuntimeException("Title is too long");
        }
    }

}