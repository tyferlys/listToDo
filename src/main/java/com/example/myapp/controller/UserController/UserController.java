package com.example.myapp.controller.UserController;

import com.example.myapp.controller.Response;
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

    @GetMapping("check")
    public ResponseEntity checkUser(@CookieValue(name = "token", required = false) String token){
        String response = userService.checkToken(token);

        if (response.equals("токен истек"))
            return ResponseEntity.badRequest().body(new Response(1, response));
        else if (response.equals("неправильная сигнатура токена"))
            return ResponseEntity.badRequest().body(new Response(1, response));
        else if (response.equals("токен некорректен или пуст"))
            return ResponseEntity.badRequest().body(new Response(1, response));
        else if (response.equals("неверный токен"))
            return ResponseEntity.badRequest().body(new Response(1, response));

        return ResponseEntity.ok().body(new Response(0, response));
    }

    @GetMapping("login/{userName}/{password}")
    public ResponseEntity loginUser(@PathVariable String userName, @PathVariable String password){
        String responseStatus = userService.loginUser(userName, password);

        if (responseStatus.equals("Неправильный логин или пароль"))
            return ResponseEntity.badRequest().body(new Response(1, responseStatus));

        return ResponseEntity.ok().body(new Response(0, responseStatus));
    }

    @PostMapping("create")
    public ResponseEntity createUser(@RequestBody User user){
        int responseStatus = Integer.parseInt(userService.createUser(user));

        if (responseStatus == 0)
            return ResponseEntity.ok().body(new Response(responseStatus, "Пользователь создан"));
        else if (responseStatus == 1)
            return ResponseEntity.badRequest().body(new Response(responseStatus, "Пользователь уже существует"));
        else
            return ResponseEntity.badRequest().body(new Response(responseStatus, "Ошибка при создании"));
    }
}
