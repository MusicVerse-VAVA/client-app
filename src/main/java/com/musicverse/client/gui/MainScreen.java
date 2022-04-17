package com.musicverse.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
public class MainScreen {


    @FXML
    public AnchorPane mainPane;

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
    private ListView listOfPlaylists;


    @FXML
    private Label registerMainScreenLabel;


    @FXML
    private MenuItem settingsMenuItemSettings;

    @FXML
    private MenuButton settingsDropDown;

    @FXML
    private MenuBar menuBarTest;

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

    public void setRectangles(String [] items){

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

    public void setRole(int i){

        this.role = i;

        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarTest.getMenus().add(settingsDropDownImported.menu(role, mainPane, "main"));

        registerMainScreenLabel.setVisible(false);
        loginMainScreenLabel.setVisible(false);
        nickLabel.setText(userName);

        if (role > 0)
            nickLabel.setText(PreferencesLogin.getPrefs().getNickName());

        switch (role) {
            case -1:
                // here comes code, when user is not verified in case he is atempting to login and he is not saved user
                break;

            case 0:
                playlistsLabel.setText("Only for registered");
                likedAlbumsLabel1.setVisible(false);
                likedArtistsLabel.setVisible(false);
                likedSongsLabel1.setVisible(false);
                registerMainScreenLabel.setVisible(true);
                loginMainScreenLabel.setVisible(true);
                break;

            case 1:
                break;

            case 2:
                break;
            case 3:
            default:
                break;
        }
    }

    public void setPlaylists(HashMap<Integer,String> list){
        for (String name : list.values()){
            listOfPlaylists.getItems().add(name);
        }
    }



    @FXML
    void loginLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initLoginScreen(event);
    }

    @FXML
    void registerLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initRegistrationScreen(event, 1);
    }

}
