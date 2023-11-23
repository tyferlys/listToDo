package com.example.myapp.controller.NoteController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("note")
public class NoteController {

    @GetMapping("")
    public ResponseEntity test(){
        return ResponseEntity.ok("wowowowo");
    }
}
