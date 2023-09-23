package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.exception.CustomValidationException;
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
            throw new CustomValidationException("Description is empty");
        }
        if (description.length() > 1000) {
            throw new CustomValidationException("Description is too long");
        }
    }


    private void validate_title(Post post) {
        String postTitle = post.getTitle();
        if (postTitle.isEmpty()) {
            throw new CustomValidationException("Title is empty");
        }
        if (postTitle.length() > 50) {
            throw new CustomValidationException("Title is too long");
        }
    }

}
