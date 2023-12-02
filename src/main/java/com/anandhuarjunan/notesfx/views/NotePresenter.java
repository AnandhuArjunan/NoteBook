package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.entities.Note;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class NotePresenter implements Initializable {

	@Inject
	private Note note;

	@FXML
	private Label content;

	@FXML
	private Label title;
	
    @FXML
    private BorderPane notePane;
	
	@Inject
	private NotesPresenter notesPresenter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		notePane.setOnMouseClicked(ev->{
			notesPresenter.openViewMode(note);
		});
		content.setText(note.getNote());
		title.setText(note.getTitle());
	}

}
