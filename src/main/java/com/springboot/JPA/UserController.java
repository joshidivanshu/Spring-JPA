package com.springboot.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing user-related operations and views
 */

@Controller
public class UserController {

    @Autowired
    UserRepo repo;

    /**
     * Displays the user form, initialising the model with a new User model.
     * @param model the model to which the "user" attribute will be added
     * @return the view name for the user form
     * */

    @RequestMapping("/")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    /**
     * Handles the submission of user creation form
     * @param user The user object populated with the form data
     * @return a redirect to the user form or another view
     */

    @PostMapping("/processUser")
    public String processUser(@ModelAttribute User user) {
        repo.save(user);
        return "redirect:/"; // Redirect to the form page again or another page
    }

    /**
     * Returns us the list of users stored in the database.
     * @param model
     * @return userList view which will show all the users in the database
     */

    @GetMapping("/getUsers")
    public String getUsers(Model model) {
        Iterable<User> userIterable = repo.findAll();
        List<User> users = new ArrayList<>();

        userIterable.forEach(users::add);
        model.addAttribute("users", users);
        return "userList";
    }




}
