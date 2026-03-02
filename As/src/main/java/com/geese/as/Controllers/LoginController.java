package com.geese.as.Controllers;

import com.geese.as.Users.User;
import com.geese.as.Validation.UserValidation;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserValidation userValidation;

    @GetMapping({"/", "/login"})
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "invalid username or password");
        }
        return "login";
    }

    @PostMapping("/login")
    public String valLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {

        User user = userValidation.getUserByName(username).orElse(null);
        if (user == null || !user.getPassword().equals(password)) {
            return "redirect:/login?error";
        }
        session.setAttribute("currentUser", user);
        return "redirect:/terminal";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
