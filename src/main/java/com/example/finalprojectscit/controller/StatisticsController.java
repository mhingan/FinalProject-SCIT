package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {
    //todo: controllers
    private final StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }


    @GetMapping("/admin/statistics")
    public String statistics(Model model) {
        //user
        int allUsers = service.allUsers();
        int activeUsers = service.allActiveUsers();
        int inactiveUsers = service.allInactiveUsers();
        //post
        int allPosts = service.allPosts();
        int allToday = service.allTodaysPosts();
        int media = service.postsAnnualMedia();

        model.addAttribute("allUsers", allUsers);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("inactiveUsers", inactiveUsers);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("allToday", allToday);
        model.addAttribute("media", media);

        return "admin/statistics";
    }
}
