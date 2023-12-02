package com.anandhuarjunan.notesfx.views;

import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IconTextView extends HBox {

	public IconTextView(MFXFontIcon icon,String text){
		super(15);
		this.getStyleClass().add("icon-text");
		icon.setSize(80);
		setAlignment(Pos.CENTER);
		Label label = new Label(text);
		
		label.getStyleClass().add("icon-text-label");

		this.getChildren().addAll(icon,label);
	}
	
	
}
