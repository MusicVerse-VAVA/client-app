package com.musicverse.client.gui;

import java.sql.ResultSet;

import com.musicverse.client.Database;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginScreen {
	
	private Database db;

    @FXML
    private Label backBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Button logInBtn;

    @FXML
    private PasswordField pswdField;
    
    private Alert loginAlert = new Alert(AlertType.NONE);

    @FXML
    void onBackBtnClick(MouseEvent event) {
        this.goBack(event);
    }
    
    private void goBack(Event event) {
    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(WelcomeScreen.getHolder(), 1200, 600);
        window.setTitle("Music Verse");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void onLogInBtnClick(ActionEvent event) {
    	try {
	    	db = new Database();
	    	String password = this.pswdField.getText();
	    	String email = this.emailField.getText();
	    	
	    	if(!db.emailExists(email)) {
	    		loginAlert.setAlertType(AlertType.ERROR);
	   		 	loginAlert.setContentText("Používate¾ s týmto e-mailom neexistuje");
	            loginAlert.show();
	            return;
	    	}
	    	ResultSet result = db.authenticate(email, password);
	    	if(result == null) {
	    		loginAlert.setAlertType(AlertType.ERROR);
	   		 	loginAlert.setContentText("Nesprávne prihlasovacie údaje");
	            loginAlert.show();
	            return;
	    	}
	    	else {
	    		loginAlert.setAlertType(AlertType.INFORMATION);
	   		 	loginAlert.setContentText("PRIHLASENY POUZIVATEL : "+result.getString("nickname"));
	            loginAlert.show();
	    	}
	    	
   		 	this.goBack(event);
    	}
    	catch(Exception e){
    		//...
    	}

    }

}
