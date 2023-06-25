package com.anandhuarjunan.notesfx.views;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.entities.NoteBook;
import com.anandhuarjunan.notesfx.helper.DatabaseController;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class MainPresenter implements Initializable {

	private double xOffset;

	private double yOffset;


	private ToggleGroup toggleGroup;

	@FXML
	private MFXFontIcon close;

	@FXML
	private MFXFontIcon maximize;

	@FXML
	private MFXFontIcon minimize;

	@FXML
	private VBox navBar;

	@FXML
	private HBox wHeader;

	@FXML
	private MFXScrollPane scrollPane;

	@Inject
	private Stage stage;

    @FXML
    private BorderPane root;

    @FXML
    private BorderPane contentPane;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private MenuItem newNoteBook;
     

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addWindowDragAndCloseEvents();
		loadNoteBooks();
	
		
		
		newNoteBook.setOnAction(ev->{
			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("mainPresenter", this);
            Scene dialogScene = new Scene(new NewNoteBookView(customProperties::get).getView(), 600, 400);
            dialog.setScene(dialogScene);
            dialog.show();
		});
	
	}
	
	
	public void loadNoteBooks() {
		tabPane.getTabs().clear();
		List<NoteBook> notebooks;
		try {
			notebooks = DatabaseController.getNoteBooks();
			if(Objects.nonNull(notebooks) && !notebooks.isEmpty()) {
				notebooks.forEach(nb->{
					Map<String, Object> customProperties = new HashMap<>();
				    customProperties.put("noteBook", nb);
					Tab tab = new Tab(nb.getName());
					tab.setContextMenu(setContextMenuForNoteBook(nb));
					tab.setClosable(false);
					tab.setContent(new NotesView(customProperties::get).getView());
					tabPane.getTabs().add(tab);
					
				});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ContextMenu setContextMenuForNoteBook(NoteBook noteBook) {
        ContextMenu contextMenu = new ContextMenu();

		MenuItem rename = new MenuItem("Rename Notebook");
		MenuItem delete = new MenuItem("Delete Notebook");

		
		rename.setOnAction(ev->{
			//renameBook(noteBook);
			loadNoteBooks();
		});
		
		delete.setOnAction(ev->{
			deleteNoteBook(noteBook);
			loadNoteBooks();
		});
		
        // add menu items to menu
        contextMenu.getItems().add(rename);
        contextMenu.getItems().add(delete);

		return contextMenu;
		
	}
	
	private void deleteNoteBook(NoteBook noteBook) {
		try {
			DatabaseController.deleteNoteBook(noteBook);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void renameBook(NoteBook noteBook) {
		//noteBook.setName();
		try {
			DatabaseController.renameNoteBook(noteBook);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void addWindowDragAndCloseEvents() {
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
		maximize.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.setMaximized(!stage.isMaximized());
		});
		minimize.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.setIconified(true);
		});
		wHeader.setOnMousePressed(event -> {
			xOffset = stage.getX() - event.getScreenX();
			yOffset = stage.getY() - event.getScreenY();
		});
		wHeader.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() + xOffset);
			stage.setY(event.getScreenY() + yOffset);
		});

	}


}
