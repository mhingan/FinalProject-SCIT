/**
 * @author Mihaita Hingan
 */
package com.example.finalprojectscit.utils;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUserValidation {
    private final UserRepository userRepository;

    @Autowired
    public NewUserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUser(User user) {
        validate_name(user);
        validate_email(user);

    }

    void validate_email(User user) {
        String email = user.getEmail();
        String regex = "^(.+)@(.+)$";

        if (email == null) {
            throw new CustomValidationException("Email cannot be null");
        }

        if (!email.matches(regex)) {
            throw new CustomValidationException("Email syntax error: must be in the format name@email.com");
        }

        for (User user1 : userRepository.findAll()) {
            if (user.getEmail().equals(user1.getEmail())) {
                throw new CustomValidationException("This email is already used by another account");
            }
        }

        System.out.println("Validate Email: success");
    }


    void validate_name(User user) {
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new CustomValidationException("First name / Last name cannot be null");
        }

        if (!firstName.matches("^[A-Za-z]+$") || !lastName.matches("^[A-Za-z]+$")) {
            throw new CustomValidationException("Name syntax error: must contain only letters (e.g., John Smith)");
        }

        if (firstName.length() > 20 || lastName.length() > 20) {
            throw new CustomValidationException("Name must be lenghts max: 20");
        }
        System.out.println("Validate Name: success");
    }


}
