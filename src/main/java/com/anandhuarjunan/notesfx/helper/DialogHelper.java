package com.anandhuarjunan.notesfx.helper;

import java.util.Map;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogHelper {
	private MFXGenericDialog dialogContent;
	private MFXStageDialog dialog;
	public DialogHelper(Stage stage) {

			this.dialogContent = MFXGenericDialogBuilder.build()
					.makeScrollable(true)
					.get();
			this.dialog = MFXGenericDialogBuilder.build(dialogContent)
					.toStageDialogBuilder()
					.initOwner(stage)
					.initModality(Modality.APPLICATION_MODAL)
					.setDraggable(true)
					.setScrimPriority(ScrimPriority.WINDOW)
					.setScrimOwner(true)
					.get();
			
		

		

			dialogContent.setMaxSize(400, 200);
	
	}
	
	
	public void openWarning(Runnable msevent,String head,String msg) {
		MFXFontIcon warnIcon = new MFXFontIcon("mfx-do-not-enter-circle", 18);
		dialogContent.setHeaderIcon(warnIcon);
		dialogContent.setHeaderText(head);
		dialogContent.setContentText(msg);
		dialogContent.addActions(
				Map.entry(new MFXButton("Confirm"), event->{
					msevent.run();
					dialog.close();
				}),
				Map.entry(new MFXButton("Cancel"), event -> dialog.close())
		);
		
		dialog.showDialog();
	}
	
	public void openInfo(String msg) {
		MFXFontIcon info = new MFXFontIcon("mfx-info-circle", 18);
		dialogContent.setHeaderIcon(info);
		dialogContent.setContentText(msg);
		dialogContent.setHeaderText("Information");

		dialog.showDialog();
	}

}
