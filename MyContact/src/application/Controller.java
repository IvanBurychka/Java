package application;

import java.io.IOException;
import java.util.Optional;

import datamodel.Contact;
import datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Controller {

	@FXML
	private BorderPane mainPanel;
	
	private ContactData data;
	
	@FXML
	private TableView<Contact> contactsTable;
	
	public void initialize() {
		data = new ContactData();
		data.loadContacts();
		contactsTable.setItems(data.getContacts());
	}
	
	@FXML
	public void showAddContactDialog() {
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPanel.getScene().getWindow());
		dialog.setTitle("Add New Contact");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Could not load Add contact windows");
			e.printStackTrace();
			return; 
		}
		
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			ContactController contactController = fxmlLoader.getController();
			Contact newContact = contactController.getNewContact();
			data.addContact(newContact);
			data.saveContacts();
		}
	}
	
	@FXML
	public void showEditCOntactDialog() {
		Contact selectContact = contactsTable.getSelectionModel().getSelectedItem();
		if (selectContact == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No Comtact Selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select contact you want to edit.");
			alert.showAndWait();
			return;
		}
		
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainPanel.getScene().getWindow());
		dialog.setTitle("Add New Contact");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException   e) {
			System.out.println("Could'n load the dialog for editing");
			e.printStackTrace();
		}
		
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		ContactController contactController = fxmlLoader.getController();
		contactController.editContact(selectContact);
		
		Optional<ButtonType> result = dialog.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			contactController.updateContact(selectContact);
			data.saveContacts();
		}
	}
	
	
	public void deleteContact() {
		Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
		if (selectedContact == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No Comtact Selected to delete");
			alert.setHeaderText(null);
			alert.setContentText("Please select contact you want delete.");
			alert.showAndWait();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Contact?");
		alert.setHeaderText("Header text");
		alert.setContentText("Are you shure to delete the selected contact?\n" + selectedContact.getFirstName() + " " + selectedContact.getLastName());
				
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			data.deleteContact(selectedContact);
			data.saveContacts();
		}
		
	}
	
	
}
