package com.musicverse.client;

import com.musicverse.client.gui.MainScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.Objects;

public class InitScreensFunctions {

    @SneakyThrows
    public void initMainScreen(MouseEvent event){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/MainScreen.fxml")));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        com.musicverse.client.gui.MainScreen controler = loader.getController();
        controler.setRole(1);
        String[] items = {"Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout"};
        controler.setRectangles(items);
        window.setTitle("MusicVerse");
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initRegistrationScreen(ActionEvent actionEvent, int role){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/RegisterScreen.fxml")));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        com.musicverse.client.gui.RegisterScreen controler = loader.getController();
        String label = "", Title = "";
        if (role == 1){
            label = "Register";
            Title = "Register new user";
        } else if (role == 2) {
            label = "Request to be an artist";
            Title = "Register new artist";
        }
        controler.setLabelText(label);
        Scene scene = new Scene(root, 1200,600);
        window.setTitle(Title);
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initLoginScreen(ActionEvent actionEvent){
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScreen.fxml")));
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setTitle("Log in user");
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initSettingsScreen(ActionEvent event, MenuButton settingsDropDown, String Title, String fxml){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent root = loader.load();
        Stage window = (Stage) settingsDropDown.getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setScene(scene);
        window.setTitle(Title);
        window.show();
    }
}
