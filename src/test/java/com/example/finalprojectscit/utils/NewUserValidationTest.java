package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class NewUserValidationTest {

    @InjectMocks
    private NewUserValidation newUserValidation;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_email_success() {
        User user = User.builder()
                .email("email@amial.com")
                .build();


        assertDoesNotThrow(() -> newUserValidation.validate_email(user));
        System.out.println("Test: User: Email: Valid Email => OK");
    }


    @Test
    void validate_email_not_matching_regex_of_email() {
        User user = User.builder()
                .email("email.email.com")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_email(user);
        });
        System.out.println("Test: User: Email: Email not matching regex 'email@email.com' => OK");
    }


    @Test
    void validate_email_empty() {
        User user = User.builder()
                .email("")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_email(user);
        });
        System.out.println("Test: User: Email: Email empty => OK");
    }



    @Test
    void validate_name_success() {
        User user = User.builder()
                .first_name("Mihai")
                .last_name("Hingan")
                .build();

        assertDoesNotThrow(() -> newUserValidation.validate_name(user));
        System.out.println("Test: User: Name: Valid first & last name => OK");
    }

    @Test
    void validate_name_empty_both(){
        User user = User.builder()
                .first_name("")
                .last_name("")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: Both empty => OK");
    }

    @Test
    void validate_name_empty_first_name(){
        User user = User.builder()
                .first_name("")
                .last_name("Hingan")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: First Name empty => OK");
    }

    @Test
    void validate_name_empty_last_name(){
        User user = User.builder()
                .first_name("Mihai")
                .last_name("")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: Last empty => OK");
    }

    @Test
    void validate_name_longer_than_20(){
        User user = User.builder()
                .first_name("Name Name Name Name ")
                .last_name("Name Name Name Name ")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: Both longer than 20 => OK");
    }

    @Test
    void validate_first_name_longer_than_20(){
        User user = User.builder()
                .first_name("Name Name Name Name  ")
                .last_name("Test")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: First name longer than 20 => OK");
    }

    @Test
    void validate_last_name_longer_than_20(){
        User user = User.builder()
                .first_name("test")
                .last_name("Name Name Name Name  ")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            newUserValidation.validate_name(user);
        });
        System.out.println("Test: User: Name: Last name longer than 20 => OK");
    }
}
