package todolist;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import todolist.datamodel.TodoData;
import todolist.datamodel.TodoItem;

import java.time.LocalDate;

import javafx.fxml.FXML;

public class DialogController {

	@FXML
	private TextField shortDescriptionField;
	
	@FXML
	private TextArea detailsArea;
	
	@FXML
	private DatePicker deadlinePicker;
	
	public TodoItem processResults() {
		String shortDeshortDescription = shortDescriptionField.getText().trim();
		String details = detailsArea.getText().trim();
		LocalDate deadlineValue = deadlinePicker.getValue();
		TodoItem newItem = new TodoItem(shortDeshortDescription, details, deadlineValue);
		TodoData.getInstance().addTodoItem(newItem);
		return newItem;
	}
	
}
