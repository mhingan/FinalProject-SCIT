package com.example.finalprojectscit.service;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewUserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NewUserValidation newUserValidation;



    @Autowired
    public UserService(UserRepository userRepository, NewUserValidation newUserValidation) {
        this.userRepository = userRepository;
        this.newUserValidation = newUserValidation;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomValidationException("No user found with id: " + id));
    }

    public void createUser(User user) {
        newUserValidation.validateUser(user);
        user.set_active(true);
        user.setRole("USER");
        user.setRanking(0);
        user.setPosts(null);
        userRepository.save(user);
    }

    public User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return (User) userRepository.findByEmail(currentUserName).orElse(null);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomValidationException("no user found"));
            userRepository.delete(user);

    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void setUserActive(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomValidationException("no user found with id: " + id));
        if (user.is_active()) {
            throw new CustomValidationException("User is already active");
        } else {
            user.set_active(true);
            userRepository.save(user);
        }
    }

    public void setUserInactive(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomValidationException("no user found with id: " + id));
        if (!user.is_active()) {
            throw new CustomValidationException("User is already inactive");
        } else {
            user.set_active(false);
            userRepository.save(user);
        }
    }

    public List<User> findActiveUsers() {
        List<User> all = userRepository.findAll();
        List<User> active = new ArrayList<>();
        for (User user : all) {
            if (user.is_active()&&user.getRole().equals("USER")) {
                active.add(user);
            }
        }
        return active;
    }

    public List<User> findInactiveUsers() {
        List<User> all = userRepository.findAll();
        List<User> inactive = new ArrayList<>();
        for (User user : all) {
            if (!user.is_active()&&user.getRole().equals("USER")) {
                inactive.add(user);
            }
        }
        return inactive;
    }


    public void changeUserRole(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new CustomValidationException("No user found"));
        if(user.getRole().equals("USER")&&user.is_active()){
            user.setRole("ADMIN");
            userRepository.save(user);
        } else {
            throw new CustomValidationException("Cannot change user role");
        }
    }
}
