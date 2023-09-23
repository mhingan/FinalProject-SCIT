package com.example.finalprojectscit.controller;

import com.example.finalprojectscit.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class CustomExceptionController {
    @ExceptionHandler(CustomValidationException.class)
    public ModelAndView handleCustomValidationException(CustomValidationException ex) {
        ModelAndView modelAndView = new ModelAndView("admin/error/general-error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("errorCode", HttpStatus.BAD_REQUEST.value());
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
