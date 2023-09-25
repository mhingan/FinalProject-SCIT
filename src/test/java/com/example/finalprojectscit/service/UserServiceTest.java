package com.example.finalprojectscit.service;
import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewUserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    void setUserInactive() {
    }

    @Test
    void findActiveUsers() {
    }

    @Test
    void findInactiveUsers() {
    }
}