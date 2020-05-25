package com.appdeveloper.app.ws.ui.controller;

import com.appdeveloper.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloper.app.ws.ui.model.response.UserRest;
import com.appdeveloper.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController()
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

    Map<String,UserRest> users;

    @Autowired
    UserService userService;

    @GetMapping
    public String getUsers(@RequestParam(value = "page" , defaultValue = "1" ) int page,
                            @RequestParam(value = "limit" , defaultValue = "15") int limit,
                            @RequestParam(value="sort" , required = false,defaultValue = "desc") String sort){
        // required = false does not work with primitive like int , long etc. to deal with primitive we should use defaultValue to achieve the optional query parameter
        // required = false works with string or any other object
        return "get users was called page = "+page+" limit = "+limit + " sort "+sort;
    }

    @GetMapping(path = "/{userId}",
                        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId){
        // if(true) throw new UserServiceException("User service exception is thrown");
        // By default we relay on the status code what server is sending to client
        // But if we want to send status code from our side along with headers and
        // other information, we will be using ResponseEntity object
        UserRest userRest = userService.getUser(userId);
        if(userRest != null){
            return new ResponseEntity<>(userRest,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
                    produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails){
        UserRest userRest = userService.createUser(userDetails);
        return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> updateUser(@PathVariable String userId,@RequestBody UpdateUserDetailsRequestModel userDetails){
        UserRest userRest = userService.updateUser(userId,userDetails);
        if(userRest !=null )  return new ResponseEntity<>(userRest,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
       String response=userService.deleteUser(userId);
       if(response != null) return new ResponseEntity<>(HttpStatus.OK);
       return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}
