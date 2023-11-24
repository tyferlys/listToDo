package com.example.myapp.service.NoteService;

import com.example.myapp.model.Note;
import com.example.myapp.model.User;
import com.example.myapp.repository.NoteRepository;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String deleteNoteById(Integer id){
        try {
            try{
                Note note = noteRepository.findById(id).get();

                noteRepository.deleteById(id);
                return "заметка удалена";
            }
            catch (Exception ex){
                return "Заметки не существует";
            }
        }
        catch (Exception ex){
            return "Ошибка при удалении заметки";
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
