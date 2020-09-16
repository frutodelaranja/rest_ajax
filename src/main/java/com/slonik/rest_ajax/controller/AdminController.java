package com.slonik.rest_ajax.controller;

import com.slonik.rest_ajax.entity.User;
import com.slonik.rest_ajax.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> users(){
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @PostMapping("/admin/saveUser")
    public ResponseEntity<Void> saveUser(HttpEntity<User> httpEntity) {
        User user = httpEntity.getBody();
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<Void> updateUser(HttpEntity<User> httpEntity){
        System.out.println(httpEntity.getBody());
        User user = httpEntity.getBody();
        userService.edit(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUserAuthority(Authentication authentication) {
        User userAuthority = userService.findByLogin(authentication.getName());
        return new ResponseEntity<>(userAuthority, HttpStatus.OK);
    }

}
