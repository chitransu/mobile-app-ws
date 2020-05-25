package com.appdeveloper.app.ws.userservice.impl;

import com.appdeveloper.app.ws.shared.Utils;
import com.appdeveloper.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.response.UserRest;
import com.appdeveloper.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    Map<String,UserRest> users;
    Utils utils;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        userRest.setFirstName(userDetails.getFirstName());
        userRest.setLastName(userDetails.getLastName());
        userRest.setEmail(userDetails.getEmail());

        String userId = utils.generateUserId();
        userRest.setUserId(userId);
        if(users == null) users = new HashMap<>();
        users.put(userId,userRest);
        return userRest;
    }

    @Override
    public UserRest getUser(String userId) {
       if(users.containsKey(userId)){
           return users.get(userId);
       }
       return null;
    }

    @Override
    public UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetails) {
        if(users.containsKey(userId)){
           UserRest userRest = users.get(userId);
            userRest.setFirstName(userDetails.getFirstName());
            userRest.setLastName(userDetails.getLastName());
            users.put(userId,userRest);
            return userRest;
        }
        return null;
    }

    @Override
    public String deleteUser(String userId) {
        if(users.containsKey(userId)){
            users.remove(userId);
            return userId;
        }
        return userId;
    }
}
