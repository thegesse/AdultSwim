package com.geese.as.Controllers;

import com.geese.as.Commands.CommandRegistry;
import com.geese.as.Users.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TerminalController {

    @Autowired
    private CommandRegistry commandRegistry;

    @GetMapping("/terminal")
    public String terminal(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("commands", commandRegistry.getCommandNames());
        return "terminal";
    }

    @PostMapping("/terminal/execute")
    @ResponseBody
    public String execute(HttpSession session, @RequestParam("input") String input) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "Error: no users";
        }

        if (input.trim().equalsIgnoreCase("clear")) {
            return "{\"action\": \"clear\"}";
        }

        try {
            return commandRegistry.execute(input);
        }catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
