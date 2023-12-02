package com.anandhuarjunan.notesfx.helper;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ViewSwitcher {

	private BorderPane borderPane;
	private Map<String,Node> views = new HashMap<String,Node>();	;

	
	public ViewSwitcher(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

	public void add(String name,Node node) {
        views.put(name, node);
	}
	
	
	public void switchTo(String name) {
		borderPane.setCenter(views.get(name));
	}

	public void addAndSwitch(String string, Parent view) {
		add(string, view);
		switchTo(string);
		
	}
	
	
}
