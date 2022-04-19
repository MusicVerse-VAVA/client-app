package com.musicverse.client.gui;

import com.falsepattern.json.node.JsonNode;
import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;

import com.musicverse.client.objects.User;
import com.musicverse.client.sessionManagement.PreferencesLogin;
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
import lombok.val;

import java.io.IOException;
import java.util.HashMap;

public class LoginScreen {
	
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
	    	val api = ServerAPI.getInstance();
	    	String password = this.pswdField.getText();
	    	String email = this.emailField.getText();

			val userJson = api.authenticate(email, password);
	    	if(userJson == null) {
	    		loginAlert.setAlertType(AlertType.ERROR);
	   		 	loginAlert.setContentText("Nesprávne prihlasovacie údaje");
	            loginAlert.show();
	            return;
	    	}
	    	else {
	    		loginAlert.setAlertType(AlertType.INFORMATION);
	   		 	loginAlert.setContentText("PRIHLASENY POUZIVATEL : " + userJson.getString("username"));
	            loginAlert.show();

				//Session management
				PreferencesLogin prefLogin = new PreferencesLogin();
				User user = new User(userJson.getString("username"), userJson.getString("email"), userJson.getInt("accessLevel")
				, userJson.getInt("id"), "000000");
				prefLogin.setPreference(user);
				System.out.println(PreferencesLogin.getPrefs().getEmail());

				//zmena obrazovky
				val response = api.getUserPlaylists(PreferencesLogin.getPrefs().getId());
				try {
					new InitScreensFunctions().initMainScreen(event , createPlaylist(response));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    	}
	    	
   		 	//this.goBack(event);

    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}

    }

	private HashMap<Integer,String> createPlaylist(JsonNode playlists){
		HashMap<Integer,String> list = new HashMap<>();
		for (int i = 0; i<playlists.size();i++) {
			list.put(playlists.get(i).getInt("id"),playlists.get(i).getString("name"));
		}
		return list;
	}


}
