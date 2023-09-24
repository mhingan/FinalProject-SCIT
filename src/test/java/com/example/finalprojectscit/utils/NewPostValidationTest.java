package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewPostValidationTest {
    private NewPostValidation newPostValidation;

    @BeforeEach
    void setUp() {
        newPostValidation = new NewPostValidation();
    }

    @Test
    void test_validate_description_success() {
        Post post = Post.builder()
                .title("test")
                .description("Aceasta este descrierea.")
                .build();
        assertDoesNotThrow(() -> newPostValidation.validatePost(post));
        System.out.println("Test: Description: Description is present => OK");
    }

    @Test
    void test_validate_description_empty() {
        Post post = Post.builder()
                .title("test")
                .description("")
                .build();
        assertThrows(CustomValidationException.class, () -> {
            newPostValidation.validate_description(post);
        });
        System.out.println("Test: Description: Empty => OK");
    }

    @Test
    void test_validate_description_length_bigger_than_500() {
        Post post = Post.builder()
                .title("test")
                .description("aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  " +
                        "aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  " +
                        "aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  " +
                        "aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  aabbccllooppsshhttbbllaaooaallaappttnnggllrruurr  ")
                .build();
        assertThrows(CustomValidationException.class, () -> {
            newPostValidation.validate_description(post);
        });
        System.out.println("Test: Description: Longer than 500 => OK");
    }

}