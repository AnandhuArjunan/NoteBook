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
import com.anandhuarjunan.notesfx.helper.PopupHelper;
import com.anandhuarjunan.notesfx.helper.ViewSwitcher;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
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
    
    @FXML
    private StackPane stackPane;
    
    @FXML
    private MFXScrollPane scrollpane;

    @Inject
    private ViewSwitcher viewSwitcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchNotes();
		
		addBtn.setOnMouseClicked(ev->{
            Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("notesPresenter", this);
		    customProperties.put("noteBook", noteBook);
		    PopupHelper helper = new PopupHelper(stage,"Add Note");
		    helper.setContent(new NewNoteView(customProperties::get).getView());
		    helper.show();
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
			    customProperties.put("notesPresenter", this);
				flowPane.getChildren().add(new NoteView(customProperties::get).getView());
				scrollpane.setContent(flowPane);		
			});
		}else {
			scrollpane.setContent(new IconTextView(new MFXFontIcon("mfx-info-circle"),"No notes found"));		

		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void openViewMode(Note note) {
		Map<String, Object> customProperties = new HashMap<>();
	    customProperties.put("notesPresenter", this);
	    customProperties.put("viewSwitcher", viewSwitcher);
	    customProperties.put("note", note);
	    Parent view = new NoteViewView(customProperties::get).getView();
	    viewSwitcher.addAndSwitch("noteview",view);
	}

}