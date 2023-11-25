package com.example.myapp.service.AuthService;

import com.example.myapp.enums.UserStatus;
import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.UserService.JWT;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JWT jwt;

    @Autowired
    public AuthService(UserRepository userRepository, JWT jwt) {
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    public String loginUser(String userName, String password){
        if (!(userRepository.findByUserNameAndPassword(userName, password).size() == 0)){
            String token = jwt.generateToken(userName);
            return token;
        }
        else
            return UserStatus.ERROR_IN_LOGIN_PASSWORD.getText();
    }

    public String createUser(User user){
        try{
            if (userRepository.findByUserName(user.getUserName()).size() == 0){
                if (user.getPassword().trim().equals("") || user.getUserName().trim().equals(""))
                    return UserStatus.ERROR_CREATE_USER.getText();

                userRepository.save(user);
                return UserStatus.USER_CREATE.getText();
            }
            else
                return UserStatus.ERROR_USER_LOGIN_EXIST.getText();
        }
        catch (Exception ex){
            return UserStatus.ERROR_CREATE_USER.getText();
        }
    }
}
