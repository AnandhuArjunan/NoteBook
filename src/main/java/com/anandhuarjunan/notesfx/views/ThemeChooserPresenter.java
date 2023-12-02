package com.anandhuarjunan.notesfx.views;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.anandhuarjunan.notesfx.constants.Color;
import com.anandhuarjunan.notesfx.helper.MaterialColor;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ThemeChooserPresenter implements Initializable {
	
	@FXML
	private MFXScrollPane scrollPane;

    
    private GridPane colorGrid = new GridPane();
    
    @Inject
    private MainPresenter mainPresenter;
        
    private Color currentColor = null;
    
    @FXML
    private MFXButton apply;
    
    List<ColorBlockPresenter> blockPresenters = new ArrayList<>();
    

    @FXML
    private HBox header;

    
    @FXML
    private MFXToggleButton darkMode;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		scrollPane.setContent(colorGrid);
		colorGrid.setPadding(new Insets(15, 15, 15, 15)); 
		colorGrid.setHgap(10); 
		colorGrid.setVgap(10);	
		
		colorGrid.setAlignment(Pos.CENTER);
		colorGrid.getChildren().clear();
		
		
		List<Color> result = new ArrayList<>(EnumSet.allOf(Color.class));
		
		
		int c = 0,r=0;

	
		  Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("themeChooserPresenter", this);
		
		for(int i=0;i<result.size();i++) {
			ColorBlockView blockView = new  ColorBlockView(customProperties::get);
			ColorBlockPresenter blockPresenter = (ColorBlockPresenter)blockView.getPresenter(); 
			blockPresenters.add(blockPresenter);
			Color color = result.get(i);
			blockPresenter.setColor(color.name(),color.getName(), color.getColour());
			colorGrid.add( blockView.getView(), c, r);

			if(c==4) {
				r++;
				c = 0;
				continue;
			}

			c++;
		}
		removeStatusFromAllColors(blockPresenters);
		

	 apply.setOnAction(ev->{
		 System.out.println(currentColor);
		 
		 
	        try {
	   		 Method testMethod = MaterialColor.class.getMethod(currentColor.getShadeMethod(), int.class);

				String s50 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 50))));
				String s100 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 100))));
				String s200= String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 200))));
				String s300 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 300))));
				
				
				String s900 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 900))));
				String s800 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 800))));
				String s700= String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 700))));
				String s600 = String.format("#%06X", (0xFFFFFF & ((Integer) testMethod.invoke(new MaterialColor(), 600))));

				List<String> colours = new ArrayList<>();
				if(!darkMode.isSelected()) {
					colours.add("-bgcolor1:"+s50+";");
					colours.add("-bgcolor2:"+s100+";");
					colours.add("-bgcolor3:"+s200+";");
					colours.add("-bgcolor4:"+s300+";");
					colours.add("-txtcolor:black;");
				}else {
					colours.add("-bgcolor1:"+s900+";");
					colours.add("-bgcolor2:"+s800+";");
					colours.add("-bgcolor3:"+s700+";");
					colours.add("-bgcolor4:"+s600+";");
					colours.add("-txtcolor:white;");
				}
				
				
				mainPresenter.changeTheme(colours);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 });
	}


	public void removeStatusFromAllColors(List<ColorBlockPresenter> blockPresenters) {
		blockPresenters.forEach(bp->{
			bp.removeStatus();
		});
	}
	
	public void setColor(Color currentColor) {
		this.currentColor = currentColor;
		removeStatusFromAllColors(blockPresenters);
	}
	
}
