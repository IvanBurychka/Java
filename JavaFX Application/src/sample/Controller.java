package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

	import java.io.File;

import javafx.fxml.*;

public class Controller {

	@FXML
	private Label label;
	
	@FXML
	private Button button4;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private WebView webView;
	
	
	public void initialize() {
		button4.setEffect(new DropShadow());
	}

	@FXML
	public void handleMouseEnter() {
		label.setScaleX(2.0);
		label.setScaleY(2.0);
		label.setEffect(new DropShadow());
	}
	
	@FXML
	public void handleMouseExit() {
		label.setScaleX(1.0);
		label.setScaleY(1.0);
	}
	
	@FXML
	public void handleClick() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save my Aplication file");
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MyText", "*.txt"),
				new FileChooser.ExtensionFilter("My PDF", "*.pdf"));
		File file = chooser.showOpenDialog(gridPane.getScene().getWindow());
		if(file != null) {
			System.out.println(file.getPath());
			System.out.println(file.getName());
			System.out.println(file.getTotalSpace());
		}
	}
	
	@FXML
	public void handleLInkClick() {
		WebEngine engine = webView.getEngine();
		engine.load("http://www.javafx.com");
		
		
//		try {
//			Desktop.getDesktop().browse(new URI("http://www.javafx.com"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("The link was clicked");
	}
	
	
}

