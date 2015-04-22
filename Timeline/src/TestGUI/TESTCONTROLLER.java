package TestGUI;

import java.net.URL;
import java.util.ResourceBundle;
import controller.DAOTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TESTCONTROLLER implements Initializable{

	@FXML
	private TextArea outputText;

	@FXML
	private TextField myTextfield;

	private DAOTest database;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void addText(ActionEvent event) {
		database = new DAOTest();
		database.createText(myTextfield.getText());
		String str = database.readData(myTextfield.getText());
		outputText.setText(str);
	}

	@FXML
	void getText(ActionEvent event) {
		
	}

}
