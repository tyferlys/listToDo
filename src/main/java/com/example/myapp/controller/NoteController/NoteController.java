package com.example.myapp.controller.NoteController;

import com.example.myapp.controller.Response;
import com.example.myapp.model.Note;
import com.example.myapp.service.NoteService.NoteModelRequest;
import com.example.myapp.service.NoteService.NoteService;
import com.example.myapp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("note")
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    @Autowired
    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }
    //GET ЗАПРОСЫ-----------------------------------------------------------------------------------
    /**
     * Получение заметки по id
     * Возвращает заметку или ошибку
     * **/
    @GetMapping("/{id}")
    public ResponseEntity getNoteById(@PathVariable Integer id){
        try{
            Note note = noteService.getNoteById(id);
            return ResponseEntity.ok().body(new Response<Note>(0, note));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, "Ошибка при поиске заметки"));
        }
    }
    /**
     * Получение заметки по userName создателя и количество записей (limit)
     * Возвращаает массив заметок или ошибку
     * **/
    @GetMapping("/userName/{userName}/{limit}")
    public ResponseEntity getNoteByUserName(@PathVariable("userName") String userName, @PathVariable("limit") Integer limit){
        try{
            List<Note> notes = noteService.getNoteByUserName(userName, limit);
            return ResponseEntity.ok().body(new Response<List<Note>>(0, notes));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, "Ошибка при поиске заметки"));
        }
    }
    /**
     * Получение заметки по id создателя и количество записей (limit)
     * Возвращаает массив заметок или ошибку
     * **/
    @GetMapping("/userId/{id}/{limit}")
    public ResponseEntity getNoteByUserId(@PathVariable("id") Integer id, @PathVariable("limit") Integer limit){
        try{
            List<Note> notes = noteService.getNoteByUserId(id, limit);
            return ResponseEntity.ok().body(new Response<List<Note>>(0, notes));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, "Ошибка при поиске заметки"));
        }
    }
    //POST ЗАПРОСЫ-----------------------------------------------------------------------------------
    /**
     * Создание заметки - логин пользователя и название заметки
     * Возвращает статус - создана ли заметка
     * **/
    @PostMapping("")
    public ResponseEntity createNote(@RequestBody NoteModelRequest note){
        int response = Integer.parseInt(noteService.createNote(note));

        if (response == 0)
            return ResponseEntity.ok().body(new Response<String>(0, "Заметка создана"));
        else
            return ResponseEntity.badRequest().body(new Response<String>(1,"Ошибка при создании заметки"));
    }
    //DELETE ЗАПРОСЫ-----------------------------------------------------------------------------------
    /**
     * Удаление заметки по id
     * Возвращает статус, удалена ли заметка
     * **/
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteNoteById(@PathVariable Integer id){
        String response = noteService.deleteNoteById(id);

        if (response == "заметка удалена"){
            return ResponseEntity.ok().body(new Response<String>(0, response));
        }
        else
            return ResponseEntity.badRequest().body(new Response<String>(1, response));
    }
    /**
     * Удаление заметки по id создателя
     * Возвращает статус, удалены ли заметки
     * **/
}
