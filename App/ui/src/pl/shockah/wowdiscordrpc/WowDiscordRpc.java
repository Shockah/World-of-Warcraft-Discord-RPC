package pl.shockah.wowdiscordrpc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WowDiscordRpc extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
		primaryStage.setTitle("WoW Discord RPC");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(Layouts.app.load().getRoot()));
		primaryStage.show();
	}
}