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

        if (response.equals("токен истек"))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals("неправильная сигнатура токена"))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals("токен некорректен или пуст"))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
        else if (response.equals("неверный токен"))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));

        return ResponseEntity.ok().body(new Response(0, response));
    }

    @GetMapping("login/{userName}/{password}")
    public ResponseEntity loginUser(@PathVariable String userName, @PathVariable String password){
        String response = userService.loginUser(userName, password);

        if (response.equals("Неправильный логин или пароль"))
            return ResponseEntity.badRequest().body(new Response<String>(1, response));

        return ResponseEntity.ok().body(new Response<String>(0, response));
    }

    @PostMapping("create")
    public ResponseEntity createUser(@RequestBody User user){
        int response = Integer.parseInt(userService.createUser(user));

        if (response == 0)
            return ResponseEntity.ok().body(new Response<String>(response, "Пользователь создан"));
        else if (response == 1)
            return ResponseEntity.badRequest().body(new Response<String>(response, "Пользователь уже существует"));
        else
            return ResponseEntity.badRequest().body(new Response<String>(response, "Ошибка при создании"));
    }
}
