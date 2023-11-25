package com.example.myapp.service.UserService;

import com.example.myapp.enums.UserStatus;
import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWT jwt;

    @Autowired
    public UserService(UserRepository userRepository, JWT jwt) {
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    //GET МЕТОДЫ-----------------------------------------------------------------------------------
    public User getUserById(Integer id){
        User user = userRepository.findById(id).get();

        return user;
    }
    public User getUserByUserName(String userName){
        User user = userRepository.findByUserName(userName).get(0);

        return user;
    }
    //DELETE МЕТОДЫ-----------------------------------------------------------------------------------
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }
    public void deleteUserByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
    //PUT МЕТОДЫ-----------------------------------------------------------------------------------

    //ПРОВЕРКА ТОКЕНА НА ВАЛИДНОСТЬ И ПОЛУЧЕНИЯ ЛОГИНА ИЗ ТОКЕНА
    public String checkToken(String token){
        Claims data = jwt.decodeToken(token);

        return data.getSubject();
    }
}
