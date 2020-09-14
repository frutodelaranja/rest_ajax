package com.slonik.rest_ajax.controller;

import com.slonik.rest_ajax.entity.Role;
import com.slonik.rest_ajax.entity.User;
import com.slonik.rest_ajax.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/admin/users")
//    List<User> users(Model model, Authentication authentication) {
//        User userAuthority = userService.findByLogin(authentication.getName());
//        List<User> users = userService.listUsers();
//        model.addAttribute("userAuthority", userAuthority);
//        model.addAttribute("users", users);
//        model.addAttribute("newUser", (new User()));
//        return users;
//    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> users(){
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @PostMapping("/admin/saveUser")
    public ResponseEntity saveUser(HttpEntity<User> httpEntity) {
        User user = httpEntity.getBody();
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/admin/edit")
    public ResponseEntity updateUser(HttpEntity<User> httpEntity){
        System.out.println(httpEntity.getBody());
        User user = httpEntity.getBody();
        user.getRoles().forEach(role -> System.out.println(role.getName()));
        userService.edit(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUserAuthority(Authentication authentication) {
        User userAuthority = userService.findByLogin(authentication.getName());
        return new ResponseEntity<>(userAuthority, HttpStatus.OK);
    }

}
