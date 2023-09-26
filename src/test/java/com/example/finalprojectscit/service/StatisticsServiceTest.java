package com.example.finalprojectscit.service;

import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewUserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostService postService;
    private StatisticsService service;
    private NewUserValidation newUserValidation;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, newUserValidation);
        service = new StatisticsService(userRepository, postRepository, userService, postService);
    }

    @Test
    public void test_users_with_rank_100_no_users() {
        List<User> activeUsers = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(activeUsers);

        int result = service.getUsersWithRank100();

        assertEquals(0, result);
    }


}
