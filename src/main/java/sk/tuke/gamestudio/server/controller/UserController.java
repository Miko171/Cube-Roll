package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    @Autowired
    private UserService userService;

    private User loggedUser;


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        User user = userService.login(username, password);

        if (user != null) {
            this.loggedUser = user;
            // Redirect directly to CubeRoll instead of menu
            return "redirect:/CubeRoll";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      Model model) {
        // Check ci hesla sedia
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // Check ci je uz v databaze
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // vytvor noveho usera
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // In a real application, this should be hashed


        userService.register(newUser);

        // prihlas usera
        this.loggedUser = newUser;


        return "redirect:/CubeRoll";
    }

    // odhlasenie pouzivatela
    @GetMapping("/logout")
    public String logout() {
        this.loggedUser = null;
        return "redirect:/login";
    }


    public User getLoggedUser() {
        return loggedUser;
    }


    public boolean isLoggedIn() {
        return loggedUser != null;
    }
}