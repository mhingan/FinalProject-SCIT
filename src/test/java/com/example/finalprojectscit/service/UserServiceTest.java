package com.example.finalprojectscit.service;
import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewUserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    private UserService userService;
    private NewUserValidation newUserValidation;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, newUserValidation);
    }

    @Test
    void test_set_user_active_when_inactive_success() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        user.set_active(false);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.setUserActive(userId);

        assertTrue(user.is_active());

        verify(userRepository, times(1)).findById(userId);

        verify(userRepository, times(1)).save(user);
        System.out.println("Test: Set User Active => OK");
    }

    @Test
    void test_set_user_active_when_active_fail_success() {
        int userId = 2;
        User user = new User();
        user.setId(userId);
        user.set_active(true);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> userService.setUserActive(userId)
        );

        assertEquals("User is already active", exception.getMessage());


        assertFalse(user.is_active());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(user);

        System.out.println("Test: Set User Active: Already Active => OK");
    }

    @Test
    void test_set_user_active_when_no_user_fail_success() {

        int userId = 3;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> userService.setUserActive(userId)
        );

        assertEquals("no user found with id: " + userId, exception.getMessage());

        System.out.println("Test: Set User Active: No user => OK");
    }



    @Test
    void test_set_user_inactive_when_active_success() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        user.set_active(true);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.setUserInactive(userId);

        assertFalse(user.is_active());

        verify(userRepository, times(1)).findById(userId);

        verify(userRepository, times(1)).save(user);
        System.out.println("Test: Set User Inactive => OK");
    }

    @Test
    void test_set_user_inactive_when_inactive_fail_success() {
        int userId = 2;
        User user = new User();
        user.setId(userId);
        user.set_active(false);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> userService.setUserInactive(userId)
        );

        assertEquals("User is already inactive", exception.getMessage());


        assertFalse(user.is_active());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(user);

        System.out.println("Test: Set User Inactive: Already Inactive => OK");
    }

    @Test
    void test_set_user_inactive_when_no_user_fail_success() {

        int userId = 3;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> userService.setUserInactive(userId)
        );

        assertEquals("no user found with id: " + userId, exception.getMessage());

        System.out.println("Test: Set User Inactive: No user => OK");
    }

    @Test
    void test_find_all_active_users_no_users() {
        List<User> userList = new ArrayList<>();

        when(userService.findActiveUsers()).thenReturn(userList);

        List<User> result = userService.findActiveUsers();

        assertEquals(0, result.size());
        System.out.println("Test: Find all active users: No users => OK");
    }

    @Test
    void test_find_all_active_users_no_with_active_users() {
        User user = new User();
        user.set_active(false);
        User user2 = new User();
        user2.set_active(false);

        List<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);

        when(userService.findActiveUsers()).thenReturn(userList);

        List<User> result = userService.findActiveUsers();

        assertEquals(0, result.size());

        System.out.println("Test: Find all active users: No users => OK");
    }

    @Test
    void test_find_all_active_user_success() {
        User user = new User();
        user.setRole("USER");
        user.set_active(true);
        User user2 = new User();
        user2.setRole("USER");
        user2.set_active(true);

        List<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);

        when(userService.findActiveUsers()).thenReturn(userList);

        List<User> result = userService.findActiveUsers();

        assertEquals(2, result.size());

        System.out.println("Test: Find all active users: With active user => OK");
    }

    @Test
    void test_find_all_inactive_users() {
    }
}