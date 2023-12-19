package com.springboot.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getUser")
    public String getUser(@RequestParam String userId, Model model) {
        Optional<User> user = repo.findById(Integer.parseInt(userId));
        model.addAttribute("user", user.orElse(null));
        return "showUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int userId) {
        User user = repo.findById(userId).orElse(null);
        if(user != null)
            repo.delete(user);
        return "redirect:/";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam int userId, @RequestParam String userName) {
        User user = repo.findById(userId).orElse(null);
        if(user != null) {
            user.setName(userName);
            repo.save(user);
        }

        return "redirect:/";
    }

    @GetMapping("findByTech")
    public String findUserByTech(@RequestParam String tech, Model model) {
        List<User> users = repo.findByTech(tech);
        System.out.println(users);
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("findByName")
    public String findUserByName(@RequestParam String userName, Model model) {
        User user = repo.findByName(userName).orElse(null);
        if(user != null) {
            System.out.println(user);
            System.out.println(repo.findByIdGreaterThan(1));
            System.out.println(repo.findByTechSorted("java"));
            model.addAttribute("user", user);
            return "showUser";
        }
        return "redirect:/";

    }

    @RequestMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @RequestMapping("/user/{userId}")
    @ResponseBody
    public  Optional<User> getUser(@PathVariable int userId) {
        return repo.findById(userId);
    }

    @RequestMapping(path="/user/name/{userName}", produces = {"application/xml"})
    @ResponseBody
    public Optional<User> getUser(@PathVariable String userName) {
        return repo.findByName(userName);
    }

    /**
     * Example Curl Request
     * curl -X POST -H "Content-Type: application/json" -d '{"id":"1","name":"john", "tech":"python"}' http://localhost:8080/createUser
     * @param user
     * @return
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> createUserRest(@RequestBody User user) {
        repo.save(user);
        return new ResponseEntity<>("User Created Successfully!!", HttpStatus.CREATED);
    }

    /**
     * Example curl request
     * curl -X DELETE http://localhost:8080/deleteUser/1
     * @param userId
     * @return
     */

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUserRest(@PathVariable int userId) {
        User user = repo.findById(userId).orElse(null);
        if(user != null) {
            repo.delete(user);
            return new ResponseEntity<>("User Deleted Successfully!!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No Such user exists", HttpStatus.BAD_REQUEST);
        }

    }


}
