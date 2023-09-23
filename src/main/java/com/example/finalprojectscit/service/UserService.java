package com.example.finalprojectscit.service;

import com.example.finalprojectscit.exception.CustomValidationException;
import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import com.example.finalprojectscit.utils.NewUserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private NewUserValidation newUserValidation;

    @Autowired
    public UserService(PostRepository postRepository, UserRepository userRepository, NewUserValidation newUserValidation) {
        this.postRepository = postRepository;
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


    //todo: sa fac acelasi lucru dar pentru postari: afisate in functie de rankig-ul userului
    public List<User> usersByRank() {
        List<User> users = userRepository.findAll();

        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRanking() > 5)
                .toList();

        return filteredUsers.stream()
                .sorted(Comparator.comparing(User::getRanking))
                .collect(Collectors.toList());
    }


    //todo: check if ok when logged in
    public User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return (User) userRepository.findByEmail(currentUserName).orElse(null);
    }

    public void deleteUser() {
        User user = findCurrentUser();
        int userId = user.getId();
        //todo:check if working like that
        List<Post> userPosts = postRepository.findByUserId(userId);
        postRepository.deleteAll(userPosts);

        if (user.getPosts().isEmpty()) {
            userRepository.delete(user);
        } else {
            throw new CustomValidationException("User cannot be deleted. Associated posts found in db.");
        }

    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void setUserActive(int id){
        User user = userRepository.findById(id).orElseThrow(()->new CustomValidationException("no user found with id: " + id));
        user.set_active(true);
        userRepository.save(user);
    }

    public void setUserInactive(int id){
        User user = userRepository.findById(id).orElseThrow(()->new CustomValidationException("no user found with id: " + id));
        user.set_active(false);
        userRepository.save(user);
    }

    //todo: make more verifications
    public List<User> findActiveUsers() {
        List<User> all = userRepository.findAll();
        List<User> active = new ArrayList<>();
        for(User user: all){
            if(user.is_active()){
                active.add(user);
            }
        }
        return active;
    }

    //todo: make more verifications
    public List<User> findInactiveUsers() {
        List<User> all = userRepository.findAll();
        List<User> inactive = new ArrayList<>();
        for(User user: all){
            if(!user.is_active()){
                inactive.add(user);
            }
        }
        return inactive;
    }
}
