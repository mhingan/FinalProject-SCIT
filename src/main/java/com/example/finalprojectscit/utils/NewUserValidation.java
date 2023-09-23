package com.example.finalprojectscit.utils;

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

    private void validate_email(User user) {
        String email = user.getEmail();
        String regex = "^(.+)@(.+)$";

        for (User user1 : userRepository.findAll()) {
            if (user.getEmail().equals(user1.getEmail())) {
                //todo: change with custom Exception
                throw new RuntimeException("This email is already used by another account");
            } else {
                if (!email.matches(regex)) {
                    //todo: change with custom Exception
                    throw new RuntimeException("Email syntax error: must be in the format name@email.com");
                }
            }

        }
        System.out.println("Validate Email: success");
    }

    private void validate_name(User user) {
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();

        if (!firstName.matches("^[A-Za-z]+$") || !lastName.matches("^[A-Za-z]+$")) {
            //todo: change with custom Exception
            throw new RuntimeException("Name syntax error: must contain only letters (e.g., John Smith)");
        }
        System.out.println("Validate Name: success");
    }


}
