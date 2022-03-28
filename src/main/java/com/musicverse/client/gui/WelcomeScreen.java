package com.musicverse.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class WelcomeScreen {

    private static Stage primaryStage;
    private static BorderPane rootLayout;

    public static VBox getHolder(){

        Button registerBtn, loginBtn, registerArtistBtn;
        Label guest;

        // Requesting the Java versions used by the application
        //(top/right/bottom/left)
        final String versionJava = System.getProperty("java.version");
        final String versionJavaFX = System.getProperty("javafx.version");

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(50));
        buttons.setHgap(10);
        buttons.setVgap(30);

        Label title = new Label("Welcome to MUSICVERSE");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 25px;");
        title.setAlignment(Pos.TOP_LEFT);
        title.setPadding(new Insets(40,10, 10, 40));

        registerBtn = new Button("Create an account");
        registerBtn.setStyle("-fx-background-color: #58A034; -fx-font-size: 18px;-fx-text-fill: white; -fx-pref-width: 300px;");
        registerBtn.setAlignment(Pos.TOP_CENTER);

        loginBtn = new Button("I already have an account");
        loginBtn.setStyle("-fx-background-color: #58A034; -fx-font-size: 18px;-fx-text-fill: white; -fx-pref-width: 300px;");

        registerArtistBtn = new Button("Create an artist account");
        registerArtistBtn.setStyle("-fx-background-color: #58A034; -fx-font-size: 18px; -fx-text-fill: white; -fx-pref-width: 300px;");

        guest = new Label("continue without registration");
        guest.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        guest.setMaxWidth(Double.MAX_VALUE);
        guest.setAlignment(Pos.CENTER);
        guest.setUnderline(true);

        Label motto = new Label("where all the music experience is indescribably unearthly");
        motto.setStyle("-fx-text-fill: white; -fx-font-size: 18px; ");
        motto.setMaxWidth(Double.MAX_VALUE);
        motto.setAlignment(Pos.BOTTOM_RIGHT);
        motto.setPadding(new Insets(0,40, 10, 0));


        buttons.add(registerBtn, 0, 0);
        buttons.add(loginBtn, 0, 1);
        buttons.add(registerArtistBtn, 0, 2);
        buttons.add(guest, 0, 3);

        VBox holder = new VBox(title, buttons, motto);
        holder.setSpacing(20);
        holder.setStyle("-fx-background-color: #3D3F3C");

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen.fxml"));
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200,600);
                window.setTitle("title");
                window.setScene(scene);
                window.show();
            }
        });

        return holder;
    }
}
