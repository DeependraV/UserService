package com.lcwd.user.service.controller;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.services.UserServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServices userServices;

    Logger log= LoggerFactory.getLogger(UserController.class);
    private int retryCount=1;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        String randomID=UUID.randomUUID().toString();
        user.setUserId(randomID);
       User user1= userServices.saveUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelRetry",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        log.info("Retry number "+ retryCount++);
        User user= userServices.getUser(userId);
        retryCount=1;
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> allUser=userServices.getAllUser();
        return ResponseEntity.ok(allUser);

    }

    //this would be called when fallback occurs
    private  ResponseEntity<User> ratingHotelFallback(String userId,Exception e)
    {
        User user=User.builder()
                .email("dummy@email")
                .name("Dummy Name")
                .about("this user is created dummy because service is down ")
                .userId("123456")
                .build();
        log.error("Fallback is executed because service is down Total retry "+(--retryCount)+" "+e.getMessage());
        retryCount=1;
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
