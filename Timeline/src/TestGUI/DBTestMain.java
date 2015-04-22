package TestGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DBTestMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("/TestGUI/TESTGUI.fxml"));
			Scene scene = new Scene(root1, 1000,500);			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
