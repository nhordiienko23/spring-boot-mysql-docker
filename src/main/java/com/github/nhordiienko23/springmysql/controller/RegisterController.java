package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try{
            registerService.register(username, email, password);

            redirectAttributes.addFlashAttribute("success", "Registration successful! You can now log in.");
            return "redirect:/login";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "register";
        }

    }

}
