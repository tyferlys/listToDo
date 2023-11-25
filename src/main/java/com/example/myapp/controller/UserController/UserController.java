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

    //GET ЗАПРОСЫ-----------------------------------------------------------------------------------
    /**
     * Получение пользователя по токену, который находится в куки
     * Возваращает модель пользователя
     * **/
    @GetMapping("")
    public ResponseEntity getUser(@CookieValue(name = "token", required = false) String token){
        try{
            String response = userService.checkToken(token);
            User user = userService.getUserByUserName(response);

            return ResponseEntity.ok().body(new Response<User>(0, user));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, ex.getMessage()));
        }
    }
    /**
     * Поолучение пользователя по Id
     * Возваращает модель пользователя
     * **/
    @GetMapping("{id}")
    public ResponseEntity getUserById(@PathVariable("id") Integer id){
        try{
            User user = userService.getUserById(id);
            return ResponseEntity.ok().body(new Response<User>(0, user));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, ex.getMessage()));        }
        }
    //DELETE ЗАПРОСЫ-----------------------------------------------------------------------------------
    /**
     * Удаление пользователя по токену, который находится в куки
     * **/
    @DeleteMapping("")
    public ResponseEntity deleteUser(@CookieValue(name = "token", required = false) String token){
        try{
            String response = userService.checkToken(token);
            userService.deleteUserByUserName(response);

            return ResponseEntity.ok().body(new Response<String>(0, "удален"));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, ex.getMessage()));
        }
    }
    /**
     * Удаление пользователя по id
     * **/
    @DeleteMapping("{id}")
    public ResponseEntity deleteUserById(@PathVariable Integer id){
        try{
            userService.deleteUserById(id);
            return ResponseEntity.ok().body(new Response<String>(0, "удален"));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, ex.getMessage()));
        }
    }
    //PUT ЗАПРОСЫ-----------------------------------------------------------------------------------

}
