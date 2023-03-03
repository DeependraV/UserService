package com.lcwd.user.service.controller;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServices userServices;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        System.out.println("This is crete user controller");
        String randomID=UUID.randomUUID().toString();
        user.setUserId(randomID);
       User user1= userServices.saveUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        System.out.println(userId);
        User user= userServices.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> allUser=userServices.getAllUser();
        return ResponseEntity.ok(allUser);

    }

}
