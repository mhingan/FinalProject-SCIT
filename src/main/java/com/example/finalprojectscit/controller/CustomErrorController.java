package com.example.finalprojectscit.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        String errorMessage = (String) webRequest.getAttribute("javax.servlet.error.message", WebRequest.SCOPE_REQUEST);

        model.addAttribute("errorMessage", errorMessage);

        return "admin/error/general-error";
    }
}
