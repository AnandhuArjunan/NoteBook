package com.anandhuarjunan.notesfx.views;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
     
    @FXML
    private MenuItem theme;
    
    
    private ViewSwitcher viewSwitcher;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewSwitcher = new ViewSwitcher(contentPane);
		loadViews();

		addWindowDragAndCloseEvents();
		loadNoteBooks();

		initThemePopupMenu();
		
		newNoteBook.setOnAction(ev->{
            Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("mainPresenter", this);
            PopupHelper helper = new PopupHelper(stage, "New Note Book");
            helper.setContent(new NewNoteBookView(customProperties::get).getView());
            helper.show();
		});
	}
	
	public void initThemePopupMenu() {
		Map<String, Object> customProperties = new HashMap<>();
		customProperties.put("mainPresenter", this);
		PopupHelper popup = new PopupHelper(stage,"Theme Loader");
		popup.setContent(new ThemeChooserView(customProperties::get).getView());
		theme.setOnAction(ev->{
            popup.show();
        });
	}
	
	public void loadViews() {
		viewSwitcher.add("tabs", tabPane);
		viewSwitcher.add("notebooksnotfound", new IconTextView(new MFXFontIcon("mfx-info-circle"), "No Notebooks found"));

	}
	
	public void loadNoteBooks() {
		tabPane.getTabs().clear();
		List<NoteBook> notebooks;
		try {
			notebooks = DatabaseController.getNoteBooks();
			if(Objects.nonNull(notebooks) && !notebooks.isEmpty()) {
				viewSwitcher.switchTo("tabs");
				notebooks.forEach(nb->{
					Map<String, Object> customProperties = new HashMap<>();
				    customProperties.put("noteBook", nb);
				    customProperties.put("viewSwitcher", viewSwitcher);
					Tab tab = new Tab(nb.getName());
					tab.setContextMenu(setContextMenuForNoteBook(nb));
					tab.setClosable(false);
					tab.setContent(new NotesView(customProperties::get).getView());
					tabPane.getTabs().add(tab);
					
				});
			}else {
				viewSwitcher.switchTo("notebooksnotfound");
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

	public void changeTheme(List<String> colours) {
		 try {
		        // Create a new tempfile that will be removed as the application exits
		        File tempStyleClass = File.createTempFile("temp", ".css");
		        tempStyleClass.deleteOnExit();

		        // Write the stlye-class inside
		        try (PrintWriter printWriter = new PrintWriter(tempStyleClass)) {
		            printWriter.println("*{"+String.join("", colours)+"}");
		        }
		        stage.getScene().getStylesheets().add(tempStyleClass.toURI().toString());
		        stage.getScene().getRoot().getStyleClass().add("*");

		    } catch (IOException e1) {
		        e1.printStackTrace();
		    }	
	}
}
