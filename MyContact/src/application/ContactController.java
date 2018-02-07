package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

import datamodel.Contact;
import javafx.fxml.FXML;

public class ContactController {
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField phoneNumberField; 
	
	@FXML
	private TextField notesField;
	
	public Contact getNewContact() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String phoneNumber = phoneNumberField.getText();
		String notes = notesField.getText();
		Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);
		return newContact;
	}
	
	public void editContact(Contact contact) {
		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		phoneNumberField.setText(contact.getPhoneNumber());
		notesField.setText(contact.getNotes());
		
	}
	
	public void updateContact(Contact contact) {
		contact.setFirstName(firstNameField.getText());
		contact.setLastName(lastNameField.getText());
		contact.setPhoneNumber(phoneNumberField.getText());
		contact.setNotes(notesField.getText());
	}
	
}
