package com.example.finalprojectscit.comparators;

import com.example.finalprojectscit.model.Post;

import java.util.Comparator;

public class PostDateComparator implements Comparator<Post> {
    @Override
    public int compare(Post post1, Post post2) {
        return post2.getPost_date().compareTo(post1.getPost_date());
    }
}
