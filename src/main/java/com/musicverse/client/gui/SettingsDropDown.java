package com.musicverse.client.gui;

import com.musicverse.client.InitScreensFunctions;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

//This class can be used for dynamic Settings bar
// in header of almost every screen
// instead of static adding settings bar every time screen is added.

public class SettingsDropDown {

    private Menu settingsMenu = new Menu("Settings");
    private MenuItem settingsItem = new MenuItem("Settings");
    private MenuItem AAItem = new MenuItem("AA section");
    private MenuItem logOutItem = new MenuItem("Log out");

    public Menu menu(int role, AnchorPane pane, String from){

        settingsMenu.getItems().add(settingsItem);
        settingsMenu.getItems().add(AAItem);
        settingsMenu.getItems().add(logOutItem);

        if (role == 0){
            settingsMenu.setVisible(false);
        }
        else if (role == 1){
            AAItem.setVisible(false);
        }
        else if (role == 2){
            AAItem.setText("Artist section");
        }
        else if (role == 3){
            AAItem.setText("Admin section");
        }

        settingsItem.setOnAction(e -> {
            new InitScreensFunctions().initSettingsScreen("Settings", "/SettingsScreen.fxml", pane, from);
        });

        AAItem.setOnAction(e -> {
            if (role == 2)
                new InitScreensFunctions().initSettingsScreen("Artist", "/ArtistSectionScreen.fxml", pane, from);
            else if (role == 3)
                new InitScreensFunctions().initSettingsScreen("Admin", "/AdminSectionScreen.fxml", pane, from);

        });

        logOutItem.setOnAction(e -> {
            System.out.println("Menu Item 3 Selected");
        });

        return settingsMenu;

    }

}
