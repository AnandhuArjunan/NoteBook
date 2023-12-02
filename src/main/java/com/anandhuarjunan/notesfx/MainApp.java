package com.anandhuarjunan.notesfx;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.airhacks.afterburner.injection.Injector;
import com.anandhuarjunan.notesfx.helper.ResizeHelper;
import com.anandhuarjunan.notesfx.views.IconTextView;
import com.anandhuarjunan.notesfx.views.MainView;

import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.jshell.*;


public class MainApp extends Application {

	ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    public void start(Stage stage) throws Exception {
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("stage", stage);
        customProperties.put("executorService", executorService);

        Injector.setConfigurationSource(customProperties::get);
        MainView mainView = new MainView();
       	Scene scene = new Scene(mainView.getView());
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add(getClass().getResource("style/app.css").toString());
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		//stage.setAlwaysOnTop(true);
		stage.setTitle("NotesFx");
		ResizeHelper.addResizeListener(stage);
		
		
		stage.show();
    	
    	

    }
 


    @Override
    public void stop() throws Exception {
    	executorService.shutdownNow();
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
