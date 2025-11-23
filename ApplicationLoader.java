package main;

import controller.ModuleSelectorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleSelectorRootPane;

public class ApplicationLoader extends Application {
	@Override
	public void start(Stage stage) {
		ModuleSelectorRootPane view = new ModuleSelectorRootPane();
		StudentProfile model = new StudentProfile();
		new ModuleSelectorController(view, model);

		Scene scene = new Scene(view, 900, 600);
		stage.setTitle("Final Year Module Selection Tool");
		stage.setMinWidth(800);
		stage.setMinHeight(500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


