package com.example.myapp.controller.AuthController;

import com.example.myapp.controller.Response;
import com.example.myapp.enums.UserStatus;
import com.example.myapp.model.User;
import com.example.myapp.service.AuthService.AuthService;
import com.example.myapp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Авторизация пользователя по логину и паролю
     * Возвращает токен или текст ошибки
     * **/
    @GetMapping("{userName}/{password}")
    public ResponseEntity loginUser(@PathVariable String userName, @PathVariable String password){
        String response = authService.loginUser(userName, password);

        if (response.equals(UserStatus.ERROR_IN_LOGIN_PASSWORD.getText()))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));

        return ResponseEntity.ok().body(new Response<String>(0, response));
    }
    /**
     * Регистрация пользователя, получает в теле запроса модель пользователя - логин и пароль
     * Возвращает статус ошибки
     * **/
    @PostMapping("")
    public ResponseEntity createUser(@RequestBody User user){
        String response = authService.createUser(user);

        if (response.equals(UserStatus.USER_CREATE.getText()))
            return ResponseEntity.ok().body(new Response<String>(0, response));
        else
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
    }
}
