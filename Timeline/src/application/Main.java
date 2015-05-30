package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

/**
 * Entry point of the program..
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/* Loads fxml, necessary for the design of the main window*/
			Parent root1 = FXMLLoader.load(getClass().getResource("/view/FirstTemplate.fxml"));
			Scene scene = new Scene(root1, 1200,650);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);

			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
