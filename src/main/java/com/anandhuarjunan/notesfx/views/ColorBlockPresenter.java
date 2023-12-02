package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.constants.Color;

import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ColorBlockPresenter implements Initializable{

	
	
	  @FXML
	    private Pane colorPane;

	    @FXML
	    private Label colorname;
	    
	    private String id = null;
	    
	    @Inject
	    ThemeChooserPresenter themeChooserPresenter;
	    	    
	    @FXML
	    private MFXFontIcon selectedIcon;
	    
	    @FXML
	    private HBox bottombar;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			
			colorPane.setOnMouseClicked(ev->{
				themeChooserPresenter.setColor(Color.valueOf(id));
				addStatus() ;

			});
			
		}
	    
		public void setColor(String id,String name , String color) {
			this.id = id;
			colorPane.setStyle("-fx-background-color : "+color+"");
			colorname.setText(name);
		}

		public String getId() {
			return id;
		}
		
		public void addStatus() {
			bottombar.getChildren().add(0, selectedIcon);
		}
		
		public void removeStatus() {
			bottombar.getChildren().remove(selectedIcon);

		}
	    
}
