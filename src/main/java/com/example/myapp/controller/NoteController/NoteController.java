package com.example.myapp.controller.NoteController;

import com.example.myapp.controller.Response;
import com.example.myapp.model.Note;
import com.example.myapp.service.NoteService.NoteModelRequest;
import com.example.myapp.service.NoteService.NoteService;
import com.example.myapp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity getNoteById(@PathVariable Integer id){
        try{
            Note note = noteService.getNoteById(id);
            return ResponseEntity.ok().body(new Response<Note>(0, note));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<String>(1, "Заметка не найдена"));
        }
    }

    @PostMapping("create")
    public ResponseEntity createNote(@RequestBody NoteModelRequest note){
        int response = Integer.parseInt(noteService.createNote(note));

        if (response == 0)
            return ResponseEntity.ok().body(new Response<String>(0, "Заметка создана"));
        else
            return ResponseEntity.badRequest().body(new Response<String>(1,"Ошибка при создании заметки"));
    }
}
