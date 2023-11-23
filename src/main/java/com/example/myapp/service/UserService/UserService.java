package com.example.myapp.service.UserService;

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

    public String createUser(User user){
        try{
            if (userRepository.findByUserName(user.getUserName()).size() == 0){
                if (user.getPassword().trim().equals("") || user.getUserName().trim().equals(""))
                    return "2";

                userRepository.save(user);
                return "0";
            }
            else
                return "1";
        }
        catch (Exception ex){
            return "2";
        }
    }

    public String loginUser(String userName, String password){
        if (!(userRepository.findByUserNameAndPassword(userName, password).size() == 0)){
            String token = jwt.generateToken(userName);
            return token;
        }
        else
            return "Неправильный логин или пароль";
    }

    public String checkToken(String token){
        try {
            Claims data = jwt.decodeToken(token);

            return data.getSubject();
        }
        catch (ExpiredJwtException ex){
            return "токен истек";
        }
        catch (SignatureException ex){
            return "неправильная сигнатура токена";
        }
        catch (IllegalArgumentException ex){
            return  "токен некорректен или пуст";
        }
        catch (Exception ex){
            return "неверный токен";
        }
    }
}
