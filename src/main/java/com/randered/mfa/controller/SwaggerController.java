package com.randered.mfa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SwaggerController {

    @RequestMapping("/")
    public ModelAndView redirectToSwagger() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
