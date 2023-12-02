package com.anandhuarjunan.notesfx.helper;

import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.controls.cell.MFXNotificationCell;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotificationHelper {

	
	
	public NotificationHelper(Stage stage) {
		
			MFXNotificationSystem.instance().initOwner(stage);
			MFXNotificationCenterSystem.instance().initOwner(stage);

			MFXNotificationCenter center = MFXNotificationCenterSystem.instance().getCenter();
			center.setCellFactory(notification -> new MFXNotificationCell(center, notification) {
				{
					setPrefHeight(400);
				}
			});
	}
	
	public void showBottomRight(String content) {
		MFXNotificationSystem.instance()
				.setPosition(NotificationPos.BOTTOM_RIGHT)
				.publish(createNotification(content));
	}
	
	public INotification createNotification(String noti) {
		MFXSimpleNotification  notification = new MFXSimpleNotification(new Label(noti));
		return notification;
	}
	
	
}
