package todolist;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
//import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import todolist.datamodel.TodoData;
import todolist.datamodel.TodoItem;


public class Controller {
	
//	private List<TodoItem> todoItems;
	
	@FXML
	private ListView<TodoItem> todoListView;
	
	@FXML
	private TextArea itemDetailsTextArea;
	
	@FXML
	private Label deadLineLabel;
	
	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private ContextMenu listContextMenu;
	
	@FXML
	private ToggleButton filteredToggleButton;
	
	private FilteredList<TodoItem> filteredList;
	
	private Predicate<TodoItem> wantAllItems;
	private Predicate<TodoItem> wantTodayItems;
	
	public void initialize() {
		
		listContextMenu = new ContextMenu();
		MenuItem deleteMenuItem = new MenuItem("Delete"); 
		deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TodoItem item = todoListView.getSelectionModel().getSelectedItem();
				deleteItem(item);
			}
		});
		
		listContextMenu.getItems().addAll(deleteMenuItem);
		
		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {

			@Override
			public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
				if (newValue!=null) {
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					DateTimeFormatter df = DateTimeFormatter.ISO_DATE;//ofPattern(DateTimeFormatter.ISO_DATE);
					itemDetailsTextArea.setText(item.getDetails());
					deadLineLabel.setText(df.format(item.getDeadline()));
				}
			}
		});
		
		wantAllItems = new Predicate<TodoItem>() {
			@Override
			public boolean test(TodoItem todoItem) {
				return true;
			}
		};
		
		wantTodayItems = new Predicate<TodoItem>() {
			@Override
			public boolean test(TodoItem todoItem) {
				return (todoItem.getDeadline().equals(LocalDate.now() ));
			}
		};
		
		filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), wantAllItems);
		
		SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList , new Comparator<TodoItem>() {
			@Override
			public int compare(TodoItem o1, TodoItem o2) {
				return o1.getDeadline().compareTo(o2.getDeadline());
			}
		} );
		
		todoListView.setItems(sortedList);
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();
		
		todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
			
			@Override
			public ListCell<TodoItem> call(ListView<TodoItem> param) {
				ListCell<TodoItem> cell = new ListCell<TodoItem>() {
					 
					@Override
					protected void updateItem(TodoItem item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(item.getShortDescription());
							if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
								setTextFill(Color.RED);
							} else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
								setTextFill(Color.BROWN);
							}
						}
					}
				};
				
				cell.emptyProperty().addListener(
					(obs, wasEmpty, isNowEmpty) -> {
						if (isNowEmpty) {
							cell.setContextMenu(null);
						} else {
							cell.setContextMenu(listContextMenu);
						}
				});
				return cell;
			}
		});
	}
	
	@FXML
	public void showNewItemDialog() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add new todo item");
		dialog.setHeaderText("Use this dialog to create new todo item");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
		
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the dialog");
			e.printStackTrace();
			return;
		}
		
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			DialogController controller = fxmlLoader.getController();
		    TodoItem newItem = controller.processResults();
			todoListView.getSelectionModel().select(newItem);
		} 
				
	}
	
	@FXML
	public void handleKeyPressed(KeyEvent keyEvent) {
		TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null ) {
			if(keyEvent.getCode().equals(KeyCode.DELETE)) {
				deleteItem(selectedItem);
			}
		}
	}
	
	@FXML
	public void handleClickListView() {
		TodoItem item = (TodoItem) todoListView.getSelectionModel().getSelectedItem();
		itemDetailsTextArea.setText(item.getDetails());
		deadLineLabel.setText(item.getDeadline().toString());
		
	}
	
	public void deleteItem(TodoItem item){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete todo item");
		alert.setHeaderText("Delete item: " + item.getShortDescription());
		alert.setContentText("Are You sure? Press ok to comfirm or cancel to Back out.");
		Optional<ButtonType> result = alert.showAndWait();
				
		if(result.isPresent() && result.get() == ButtonType.OK) {
			TodoData.getInstance().deleteTodoItem(item);
		}
	}
	
	public void handleFilteredButton() {
		TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
		
		if (filteredToggleButton.isSelected()) {
			filteredList.setPredicate(wantTodayItems);
			if(filteredList.isEmpty()) {
				itemDetailsTextArea.clear();
				deadLineLabel.setText("");
			} else if (filteredList.contains(selectedItem)) {
				todoListView.getSelectionModel().select(selectedItem);
			} else {
				todoListView.getSelectionModel().selectFirst(); 
			}
		}else {
			filteredList.setPredicate(wantAllItems);
			todoListView.getSelectionModel().select(selectedItem);
		}
	}
	
	@FXML
	public void handleExit() {
		Platform.exit();
	}
}

