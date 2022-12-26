package com.example.Task9.controlles;


import com.example.Task9.models.User;
import com.example.Task9.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "welcomePage";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") User user){
        return "add";
    }

    @PostMapping("/")
    public String addUserToBD(@ModelAttribute("user") @Valid User user,BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "add";
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("list",userService.getAllUser());
        return "listOfUsers";
    }

    @DeleteMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user",userService.findUser(id));
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@ModelAttribute("user")  @Valid User user,BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "add";
        userService.editUser(user);
        return "redirect:/users";
    }

}
