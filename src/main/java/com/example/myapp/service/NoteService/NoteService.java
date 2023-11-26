package com.example.myapp.service.NoteService;

import com.example.myapp.model.Note;
import com.example.myapp.model.User;
import com.example.myapp.repository.NoteRepository;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private UserRepository userRepository;
    private NoteRepository noteRepository;


    @Autowired
    public NoteService(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public Note getNoteById(Integer id){
        Note note = noteRepository.findById(id).get();
        return note;
    }
    public List<Note> getNoteByUserName(String userName, Integer limit){
        List<Note> notes = noteRepository.findAll().stream().limit(limit).filter(note -> note.getUser().getUserName().equals(userName)).toList();
        return notes;
    }
    public List<Note> getNoteByUserId(Integer id, Integer limit){
        List<Note> notes = noteRepository.findAll().stream().limit(limit).filter(note -> note.getUser().getId() == id).toList();
        return notes;
    }
    public String deleteNoteById(Integer id){
        try{
            Note note = noteRepository.findById(id).get();

            noteRepository.deleteById(id);
            return "заметка удалена";
        }
        catch (Exception ex){
            return "Ошибка при удалении заметки";
        }
    }

    public String deleteNoteByUserId(Integer id){
        try{
            noteRepository.deleteByUserId(id);
            return "заметка удалена";
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }

    public String createNote(NoteModelRequest noteModel){

        if (noteModel.getTitle().trim() != ""){

            try{
                User user = userRepository.findByUserName(noteModel.getUserName()).get(0);

                Note note = new Note();
                note.setUser(user);
                note.setTitle(noteModel.getTitle());

                noteRepository.save(note);
                return "0";
            }
            catch (Exception ex){
                return "1";
            }
        }
        else
            return "1";

    }
}
