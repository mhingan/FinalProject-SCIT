/**
 * @author Mihaita Hingan
 */
package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import org.springframework.stereotype.Component;

@Component
public class NewPostValidation {
    public void validatePost(Post post) {
        validate_title(post);
        validate_description(post);
        validate_category(post);
    }

    void validate_description(Post post) {
        String description = post.getDescription();
        if (description.isEmpty()) {
            throw new CustomValidationException("Description is empty");
        }
        if (description.length() > 500) {
            throw new CustomValidationException("Description is too long");
        }
    }


    void validate_title(Post post) {
        String postTitle = post.getTitle();
        if (postTitle.isEmpty()) {
            throw new CustomValidationException("Title is empty");
        }
        if (postTitle.length() > 50) {
            throw new CustomValidationException("Title is too long");
        }
    }

    void validate_category(Post post) {
        String[] categories = {"Food", "Self-Improvement", "Travel", "HomeGarden", "ScienceEducation", "Literature", "Health", "Arts", "Technology", "Others"};
        String postCategory = post.getCategory();
        boolean categoryFound = false;

        for (String category : categories) {
            if (category.equals(postCategory)) {
                categoryFound = true;
                break;
            }
        }

        if (!categoryFound) {
            post.setCategory("Others");
        }

    }

}
