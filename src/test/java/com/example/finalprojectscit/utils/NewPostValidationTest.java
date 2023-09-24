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
    void test_validate_title_success() {
        Post post = Post.builder()
                .title("test")
                .build();
        assertDoesNotThrow(() -> newPostValidation.validate_title(post));
        System.out.println("Test: Title: Title is present => OK");
    }

    @Test
    void test_validate_title_empty() {
        Post post = Post.builder()
                .title("")
                .build();
        assertThrows(CustomValidationException.class, () ->
        {
            newPostValidation.validate_title(post);
        });
        System.out.println("Test: Title: Title is empty => OK");
    }

    @Test
    void test_validate_title_longer_than_50() {
        Post post = Post.builder()
                .title("ajalamijdn  clpsngjbd dhdjbcng fhhdbkng fjdkpolnghjrtdss")
                .build();
        assertThrows(CustomValidationException.class, () ->
        {
            newPostValidation.validate_title(post);
        });
        System.out.println("Test: Title: Title is longer than 50 => OK");
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