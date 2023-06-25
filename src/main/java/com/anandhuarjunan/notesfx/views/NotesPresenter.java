package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.entities.Note;
import com.anandhuarjunan.notesfx.entities.NoteBook;
import com.anandhuarjunan.notesfx.helper.DatabaseController;

import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class NotesPresenter implements Initializable {

    @FXML
    private FlowPane flowPane;
    
    @Inject
    private NoteBook noteBook;
    
    @Inject
    private Stage stage;

    @FXML
    private MFXFontIcon addBtn;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		fetchNotes();
		
		addBtn.setOnMouseClicked(ev->{
			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("notesPresenter", this);
		    customProperties.put("noteBook", noteBook);
            Scene dialogScene = new Scene(new NewNoteView(customProperties::get).getView(), 600, 400);
            dialog.setScene(dialogScene);
            dialog.show();
		});
	}
	
	public void fetchNotes() {
		flowPane.getChildren().clear();
		List<Note> notes;
		try {
			notes = DatabaseController.getNotesFromNoteBook(noteBook.getId());
		
		if(Objects.nonNull(notes) && !notes.isEmpty()) {
			notes.forEach(n->{
				Map<String, Object> customProperties = new HashMap<>();
			    customProperties.put("note", n);
				flowPane.getChildren().add(new NoteView(customProperties::get).getView());
			});
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}