package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.helper.DatabaseController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NewNoteBookPresenter implements Initializable {

    @FXML
    private MFXButton addBtn;

    @FXML
    private MFXTextField name;

    @Inject
    private MainPresenter mainPresenter;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		addBtn.setOnAction(ev->{	
			try {
				DatabaseController.registerNoteBook(name.getText());
				mainPresenter.loadNoteBooks();
				addBtn.getScene().getWindow().hide();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		});
		
	}

}
