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

    public User getUserByUserName(String userName){
        User user = userRepository.findByUserName(userName).get(0);
        return user;
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

    public String loginUser(String userName, String password){
        if (!(userRepository.findByUserNameAndPassword(userName, password).size() == 0)){
            String token = jwt.generateToken(userName);
            return token;
        }
        else
            return UserStatus.ERROR_IN_LOGIN_PASSWORD.getText();
    }

    public String checkToken(String token){
        try {
            Claims data = jwt.decodeToken(token);

            return data.getSubject();
        }
        catch (ExpiredJwtException ex){
            return UserStatus.ERROR_TOKEN_TIMEOVER.getText();
        }
        catch (SignatureException ex){
            return UserStatus.ERROR_TOKEN_SIGNATURE.getText();
        }
        catch (IllegalArgumentException ex){
            return UserStatus.ERROR_TOKEN_INCORRECT_OR_NULL.getText();
        }
        catch (Exception ex){
            return UserStatus.ERROR_FALSE_TOKEN.getText();
        }
    }
}
