package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class StatisticsController {
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
        int rankUsers = service.getUsersWithRank100();
        //post
        int allPosts = service.allPosts();
        int allToday = service.allTodaysPosts();

        model.addAttribute("allUsers", allUsers);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("inactiveUsers", inactiveUsers);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("allToday", allToday);
        model.addAttribute("rankUsers", rankUsers);

        return "admin/statistics";
    }

    @GetMapping("/admin/statistics/getPostsBetween")
    public String getPostsBetween(@Param("start") LocalDate start,
                                  @Param("end") LocalDate end, Model model) {
        int postsNumber = service.allPostsInTimeInterval(start, end);
        model.addAttribute("postsNumber", postsNumber);

        return "admin/statistics";
    }


}
