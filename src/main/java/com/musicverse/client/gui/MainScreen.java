package com.musicverse.client.gui;

import com.musicverse.client.InitScreensFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.Objects;

public class MainScreen {

    @FXML
    private Label likedAlbumsLabel1;

    @FXML
    private Label likedArtistsLabel;

    @FXML
    private Label likedSongsLabel1;

    @FXML
    private Label loginMainScreenLabel;

    @FXML
    private Label nickLabel;

    @FXML
    private Label playlistsLabel;


    @FXML
    private Label registerMainScreenLabel;


    @FXML
    private MenuItem settingsMenuItemSettings;

    @FXML
    private MenuButton settingsDropDown;

    @FXML
    private MenuItem settingsMenuItemAA;

    private int role = -1;

    private String userName = "Guest";

    @FXML
    private GridPane rectanglesGrid;

    static String randomColor(){

        int red = (int)Math.floor(Math.random()*(255-1+1)+1);
        int green = (int)Math.floor(Math.random()*(255-1+1)+1);
        int blue = (int)Math.floor(Math.random()*(255-1+1)+1);

        String red1 = String.valueOf(red);
        String green1 = String.valueOf(green);
        String blue1 = String.valueOf(blue);

        return "-fx-background-color: rgba(" + red1 +"," + green1 + "," + blue1 + ",0.99); -fx-pref-height: 60px";
    }

    public void setRectangles(String[] items){

        int rowNum = (int) Math.ceil((double) items.length / 6);

        for (int i = 0; i < rowNum; i++) {
            RowConstraints rowConst = new RowConstraints(100);
            rowConst.setPercentHeight(100.0 / rowNum);
            rectanglesGrid.getRowConstraints().add(rowConst);
        }

        int counter = 0;
        for (int row=0; row < rowNum; row++) {
            for (int col=0; col < 6; col++){

                Label labelOfBox = new Label(items[counter]);
                labelOfBox.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 5; -fx-font-weight: bold");

                Pane pane = new Pane();
                pane.setStyle(randomColor());

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().add(labelOfBox);

                pane.setOnMouseReleased(e -> {
                    System.out.println(labelOfBox);
                });

                pane.getChildren().add(anchorPane);
                rectanglesGrid.add(pane, col, row);

                if (counter == (items.length -1)){
                    break;
                }
                counter++;
            }
            if (counter == (items.length )){
                break;
            }
        }

    }

    @FXML
    void onSettingsClick(ActionEvent event) {
        new InitScreensFunctions().initSettingsScreen(event, settingsDropDown, "Settings", "/SettingsScreen.fxml");
    }

    public void setRole(int i){

        this.role = i;
        registerMainScreenLabel.setVisible(false);
        loginMainScreenLabel.setVisible(false);
        nickLabel.setText(userName);

        if (role > 0)
            nickLabel.setText("Simon Drienik");

        switch (role) {
            case -1:
                // here comes code, when user is not verified in case he is atempting to login and he is not saved user
                break;

            case 0:
                settingsDropDown.setVisible(false);
                playlistsLabel.setText("Only for registered");
                likedAlbumsLabel1.setVisible(false);
                likedArtistsLabel.setVisible(false);
                likedSongsLabel1.setVisible(false);
                registerMainScreenLabel.setVisible(true);
                loginMainScreenLabel.setVisible(true);
                break;

            case 1:
                settingsMenuItemAA.setVisible(false);
                break;

            case 2:
                settingsMenuItemAA.setText("Artist section");

            case 3:
                settingsMenuItemAA.setText("Admin section");
            default:
                break;
        }
    }

    /*@SneakyThrows
    public void loadFxml(MouseEvent event, String fxml, String title){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setTitle(title);
        window.setScene(scene);
        window.show();
    }
    @SneakyThrows
    void loadFxml(String fxml, String title){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent root = loader.load();
        Stage window = (Stage) settingsDropDown.getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }*/

    @FXML
    void loginLabelClick(ActionEvent event) {
        new InitScreensFunctions().initLoginScreen(event);
    }

    @FXML
    void registerLabelClick(ActionEvent event) {
        new InitScreensFunctions().initRegistrationScreen(event, 1);
    }

}
