package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.entities.Note;
import com.anandhuarjunan.notesfx.helper.DatabaseController;
import com.anandhuarjunan.notesfx.helper.DialogHelper;
import com.anandhuarjunan.notesfx.helper.NotificationHelper;
import com.anandhuarjunan.notesfx.helper.ViewSwitcher;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NoteViewPresenter implements Initializable{
	
	@Inject
	private ViewSwitcher viewSwitcher;
	
	@Inject
	private NotesPresenter notesPresenter;
	
	@Inject
	private Note note;
	
	@Inject
	private Stage stage;
	
    @FXML
    private MFXButton back;

	 @FXML
	 private MFXFontIcon close;

    @FXML
    private TextArea textContent;
    
    @FXML
    private MFXButton editBtn;
    
    @FXML
    private HBox toolBar;
    
    private MFXButton saveBtn = null;
    
    private MFXButton viewBtn = null;
    
    private MFXButton deleteBtn = null;
    
    @FXML
    private MFXTextField titleContent;
    
    public NoteViewPresenter() {
    	saveBtn = new MFXButton("Save");
    	saveBtn.setGraphic(new MFXFontIcon("mfx-check-circle"));
    	viewBtn = new MFXButton("View Mode");
    	viewBtn.setGraphic(new MFXFontIcon("mfx-eye"));
    	deleteBtn = new MFXButton("Delete");
    	deleteBtn.setGraphic(new MFXFontIcon("mfx-delete"));
    	
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		back.setGraphic(new MFXFontIcon("mfx-home"));
    	editBtn.setGraphic(new MFXFontIcon("mfx-restore"));
		toolBar.getChildren().add( deleteBtn);
		textContent.setEditable(false);
		titleContent.setEditable(false);

		back.setOnMouseClicked(ev->{
			viewSwitcher.switchTo("tabs");
			notesPresenter.fetchNotes();
		});
		textContent.setText(note.getNote());
		titleContent.setText(note.getTitle());
		editBtn.setOnAction(ev->{
			textContent.setEditable(true);
			titleContent.setEditable(true);

			toolBar.getChildren().clear();
			toolBar.getChildren().add(viewBtn);
			toolBar.getChildren().add(saveBtn);
			toolBar.getChildren().add(deleteBtn);

		});
		
		viewBtn.setOnAction(ev->{
			textContent.setEditable(false);
			titleContent.setEditable(false);
			toolBar.getChildren().clear();
			toolBar.getChildren().add(editBtn);
			toolBar.getChildren().add(deleteBtn);
		});
		
		saveBtn.setOnAction(ev->{
			note.setNote(textContent.getText());
			note.setTitle(titleContent.getText());
			new DialogHelper(stage).openInfo("Successfully Saved !");

			try {
				DatabaseController.updateNote(note);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		deleteBtn.setOnAction(ev->{
			
			new DialogHelper(stage).openWarning(()->{
				try {
					DatabaseController.deleteNote(note);
					viewSwitcher.switchTo("tabs");
					notesPresenter.fetchNotes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			},"DELETE ? ", "Are you sure you want to delete");

		});
	}

}
