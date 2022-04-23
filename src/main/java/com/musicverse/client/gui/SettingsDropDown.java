package com.musicverse.client.gui;
import com.musicverse.client.Localozator;

import java.io.IOException;

import com.musicverse.client.InitScreensFunctions;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

//This class can be used for dynamic Settings bar
// in header of almost every screen
// instead of static adding settings bar every time screen is added.

public class SettingsDropDown {

    private Menu settingsMenu = new Menu(Localozator.getResourceBundle().getString("SETTINGS"));
    private MenuItem settingsItem = new MenuItem(Localozator.getResourceBundle().getString("SETTINGS"));
    private MenuItem AAItem = new MenuItem("AA section");
    private MenuItem logOutItem = new MenuItem(Localozator.getResourceBundle().getString("LOG OUT"));

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
            AAItem.setText(Localozator.getResourceBundle().getString("ARTIST SECTION"));
        }
        else if (role == 3){
            AAItem.setText(Localozator.getResourceBundle().getString("ADMIN SECTION"));
        }

        settingsItem.setOnAction(e -> {
            try {
				new InitScreensFunctions().initSettingsScreen("Settings", "/SettingsScreen.fxml", pane, 0, 0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        AAItem.setOnAction(e -> {
            if (role == 2)
				try {
					new InitScreensFunctions().initSettingsScreen("Artist", "/ArtistSectionScreen.fxml", pane, 0, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else if (role == 3)
				try {
					new InitScreensFunctions().initSettingsScreen("Admin", "/AdminSectionScreen.fxml", pane, 0, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

        });

        logOutItem.setOnAction(e -> {
            System.out.println(Localozator.getResourceBundle().getString("MENU ITEM 3 SELECTED"));
        });

        return settingsMenu;

    }

}
