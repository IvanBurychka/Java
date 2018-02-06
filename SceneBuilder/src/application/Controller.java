package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;


public class Controller {

	@FXML
	private Label label;
	
	@FXML
	private Label label2;
	
	@FXML
	private Button ok1;
	
	
	@FXML
	public void handleAction() {
		label.setText("OK button pressed");
		label2.setText("OK button pressed");
//		
//		System.out.println("Button clicked");
	}
	
	@FXML
	public void handleMouseEntered() {
		ok1.setText("CANCEL");
	}
	
	@FXML
	public void handleMouseExited() {
		ok1.setText("OK");
	}
	
	
	
	
}
