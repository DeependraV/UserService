package com.lcwd.user.service.services;

import com.lcwd.user.service.entity.Hotel;
import com.lcwd.user.service.entity.Rating;
import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.external.services.HotelService;
import com.lcwd.user.service.services.external.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserServices {
    @Autowired
    RatingService ratingService;

    @Autowired
    HotelService hotelService;

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users= userRepository.findAll();
        users.stream().map(user -> {
            user.setRatings((getRatingAndHotel(user)));
            return user;
        }).collect(Collectors.toList());
        return  users;
    }

    @Override
    public User getUser(String userId) {
        //get the user details by user id
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id not found"));
        user.setRatings(getRatingAndHotel(user));
        return user;
    }

    private List<Rating> getRatingAndHotel(User user) {
        System.out.println("Rating hotel function running");
        //calling localhost:8083/ratings/user/e1a34504-c8ef-4959-bfb2-271e568366ba using RestTemplate
        List<Rating> ratings = ratingService.getRatings(user.getUserId());
        System.out.println(ratings);
        ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        System.out.println(ratings);
        return ratings;
    }
}
