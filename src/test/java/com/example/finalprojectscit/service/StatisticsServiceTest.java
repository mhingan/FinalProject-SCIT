package com.example.finalprojectscit.service;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StatisticsServiceTest {
    @InjectMocks
    private StatisticsService service;

    @InjectMocks
    private PostService postService;

    @InjectMocks
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void get_Users_With_Rank_100() {
//        User user = User.builder()
//                .ranking(50)
//                .build();
//        User user2 = User.builder()
//                .ranking(150)
//                .build();
//
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(user2);
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        int result = service.getUsersWithRank100();
//
//        assertEquals(1, result);
//    }

//    @Test
//    void allTodaysPosts() {
//        LocalDate past = LocalDate.of(2023, 9, 22);
//        Post post = Post.builder()
//                .post_date(LocalDate.now())
//                .build();
//        Post post1 = Post.builder()
//                .post_date(past)
//                .build();
//
//        List<Post> allPosts = new ArrayList<>();
//        allPosts.add(post);
//        allPosts.add(post1);
//
//        // Mock the behavior of postRepository.findAll() to return the list of mock posts
//        when(postRepository.findAll()).thenReturn(allPosts);
//
//        // Call the allTodaysPosts method
//        int result = service.allTodaysPosts();
//
//        // Assert that the result contains only today's post
//        assertEquals(1, result);
//    }

    @Test
    void allPostsInTimeInterval() {
    }
}