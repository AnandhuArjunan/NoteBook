package com.anandhuarjunan.notesfx.helper;

import java.util.HashMap;
import java.util.Map;

import com.anandhuarjunan.notesfx.views.AbstractPopupView;

import javafx.scene.Node;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupHelper {

	private Stage primaryStage = null;
	private Popup popup = null;
	private Node popupContent = null;
	private String title = null;

	
	public PopupHelper(Stage primaryStage,String title) {
		this.primaryStage = primaryStage;
		this.title = title;
		popup = new Popup();
	}
	
	public void setContent(Node parent) {
		Map<Object, Object> customProperties = new HashMap<>();
		Runnable runnable = ()->popup.hide();
	    customProperties.put("name", title);
	    customProperties.put("content", parent);
	    customProperties.put("popupClose", runnable);
		AbstractPopupView abstractPopupPresenter = new AbstractPopupView(customProperties::get);
		popupContent = abstractPopupPresenter.getView();
		popup.getContent().add(popupContent);
	     // Listen for the showing event of the popup
        popup.setOnShowing(e -> {
            // Calculate the center coordinates of the primary stage
            double centerX = primaryStage.getX() + primaryStage.getWidth() / 2;
            double centerY = primaryStage.getY() + primaryStage.getHeight() / 2;

            // Set the popup position to center it on the primary stage
            popup.setX(centerX - popup.getWidth() / 2);
            popup.setY(centerY - popup.getHeight() / 2);
        });
	}
	
	public void show() {
		popup.show(primaryStage);
		popup.hide();
		popup.show(primaryStage);
    }
	
}
