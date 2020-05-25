package com.appdeveloper.app.ws.userservice;

import com.appdeveloper.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.response.UserRest;

public interface UserService {
    UserRest createUser(UserDetailsRequestModel userDetails);
    UserRest getUser(String userId);
    UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetails);
    String deleteUser(String userId);
}
