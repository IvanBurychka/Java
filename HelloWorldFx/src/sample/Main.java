package sample;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		
//		GridPane root = new GridPane();
//		root.setAlignment(Pos.CENTER);
//		root.setVgap(10);
//		root.setHgap(10);
		
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 500, 275));
		primaryStage.show();
		
//		Label greeting = new Label("Welcome to JavaFX");
//		greeting.setTextFill(Color.GREEN);
//		greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
//		
//		root.getChildren().add(greeting);
		
	}
	
	
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("Hello World");
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
