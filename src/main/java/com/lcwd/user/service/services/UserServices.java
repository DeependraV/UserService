package com.lcwd.user.service.services;

import com.lcwd.user.service.entity.User;

import java.util.List;

public interface UserServices {
    User saveUser(User user);
    List<User> getAllUser();
    User getUser(String userId);

}
