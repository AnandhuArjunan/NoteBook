package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AbstractPopupPresenter implements Initializable {

    @FXML
    private MFXFontIcon close;

    @FXML
    private HBox header;
    
    @FXML
    private Label nameLbl;
    
    @Inject
    private String name;
    
    @Inject
    private Node content;
    
    @FXML
    private BorderPane container;
    
    @FXML
    private VBox vbox;
    
    @Inject
    private Runnable popupClose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameLbl.setText(name);
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> popupClose.run());
	VBox.setVgrow(content, Priority.ALWAYS);
		vbox.getChildren().add(1, content);
	}
    
    
    
    

}
