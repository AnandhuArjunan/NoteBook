package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.entities.Note;
import com.anandhuarjunan.notesfx.entities.NoteBook;
import com.anandhuarjunan.notesfx.helper.DatabaseController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class NewNotePresenter implements Initializable{

	@Inject
	private NoteBook noteBook;
	
	@Inject
	private NotesPresenter notesPresenter;
	
    @FXML
    private TextArea content;

    @FXML
    private MFXTextField title;
    
    @FXML
    private MFXButton saveBtn;
    
   


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveBtn.setOnAction(ev->{
			Note note = new Note(title.getText(), content.getText(), noteBook);
			try {
				DatabaseController.insertNote(note);
				notesPresenter.fetchNotes();
				saveBtn.getScene().getWindow().hide();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
    
    
}
