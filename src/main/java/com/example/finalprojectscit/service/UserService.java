package com.example.finalprojectscit.service;

import com.example.finalprojectscit.model.Post;
import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.PostRepository;
import com.example.finalprojectscit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user found with id: " + id));
    }

    public void createUser(User user) {
       //todo: userValidation.validateNewUser(user);
        System.out.println("a intrat in create user");
        System.out.println("a setat user role");
            user.setRole("USER");
        System.out.println("a setat user rank");
            user.setRanking(0);
        System.out.println("a setat user post");
            user.setPosts(null);
        userRepository.save(user);
        System.out.println("a salvat userul");
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

    public void deleteUser(){
        User user = findCurrentUser();
        int userId = user.getId();
        //todo:check if working like that
        List<Post> userPosts = postRepository.findByUserId(userId);
        postRepository.deleteAll(userPosts);

        if(user.getPosts().isEmpty()){
            userRepository.delete(user);
        } else {
            //todo: sa schimb Runtime cu Custom (dupa impl clasei)
            throw new RuntimeException("User cannot be deleted. Associated posts found in db.");
        }

    }
}
