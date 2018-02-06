package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private TextField nameField;
	@FXML
	private Button helloButton;
	@FXML
	private Button byeButton;
	@FXML
	private Label label;
	@FXML
	private CheckBox ourCheckBox;
	@FXML
	private Label ourLabel;
	
	
	
	@FXML
	public void onButtonClickeded(ActionEvent e) {
		if (e.getSource().equals(helloButton)) {
//			System.out.println("Hello " + nameField.getText());
			label.setText("Hello " + nameField.getText());
		} else if (e.getSource().equals(byeButton)) { 
//			System.out.println("Good bye " + nameField.getText());
			label.setText("Good bye " + nameField.getText());
		}
		
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ourLabel.setText("We did something!!!");
						}
					});
					
				} catch (InterruptedException event) {
					// TODO: handle exception
				}
				
			}
		};
		
		new Thread(task).start();
		
		if (ourCheckBox.isSelected()) {
			nameField.clear();
			helloButton.setDisable(true);
			byeButton.setDisable(true);
		}
	
	}
	
	@FXML
	public void initialize() {
		helloButton.setDisable(true);
		byeButton.setDisable(true);
	}
	
	@FXML
	public void handleKeyReleased() {
		String text = nameField.getText();
		boolean disableButton = text.isEmpty() || text.trim().isEmpty();
		helloButton.setDisable(disableButton);
		byeButton.setDisable(disableButton);
	}
	
	public void handleCheck() {
		System.out.println("The check Box was " + (ourCheckBox.isSelected() ? ("is checked") : ("not checed")));
	}
	
}

