package com.example.myapp.repository;

import com.example.myapp.model.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Note n WHERE n.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
