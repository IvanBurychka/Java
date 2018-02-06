package todolist;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todolist.datamodel.TodoData;
 
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
		primaryStage.setTitle("ToDo List");
		primaryStage.setScene(new Scene(root, 900, 500));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		try {
			TodoData.getInstance().storeTodoItems();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void init() throws Exception {
		try {
			TodoData.getInstance().loadTodoItems();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
