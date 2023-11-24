package com.example.myapp.controller.UserController;

import com.example.myapp.controller.Response;
import com.example.myapp.enums.UserStatus;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity getUser(@CookieValue(name = "token", required = false) String token){
        String response = userService.checkToken(token);
        User user = userService.getUserByUserName(response);

        System.out.println(user.getNotes().get(0).getUser().getUserName());

        return ResponseEntity.ok().body(new Response<User>(0, user));
    }

    @GetMapping("check")
    public ResponseEntity checkUser(@CookieValue(name = "token", required = false) String token){
        String response = userService.checkToken(token);

        if (response.equals(UserStatus.ERROR_TOKEN_TIMEOVER.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals(UserStatus.ERROR_TOKEN_SIGNATURE.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals(UserStatus.ERROR_TOKEN_INCORRECT_OR_NULL.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals(UserStatus.ERROR_FALSE_TOKEN.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));

        return ResponseEntity.ok().body(new Response<String>(0, response));
    }

    @GetMapping("login/{userName}/{password}")
    public ResponseEntity loginUser(@PathVariable String userName, @PathVariable String password){
        String response = userService.loginUser(userName, password);

        if (response.equals(UserStatus.ERROR_IN_LOGIN_PASSWORD.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));

        return ResponseEntity.ok().body(new Response<String>(0, response));
    }

    @PostMapping("create")
    public ResponseEntity createUser(@RequestBody User user){
        String response = userService.createUser(user);

        if (response.equals(UserStatus.USER_CREATE.getText()))
            return ResponseEntity.ok().body(new Response<String>(0, response));
        else if (response.equals(UserStatus.ERROR_USER_LOGIN_EXIST.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
    }
}
