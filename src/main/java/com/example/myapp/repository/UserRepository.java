package com.example.myapp.repository;

import com.example.myapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.userName = :userName")
    List<User> findByUserName(@Param("userName") String userName);

    @Query(value = "SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password")
    List<User> findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.userName = :userName")
    void deleteByUserName(@Param("userName") String userName);
}
