package com.musicverse.client.collections.itemActions;

import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.objects.Song;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SongActionDropDown implements ItemActionDropDown<Song>{

    private Menu menu = new javafx.scene.control.Menu("Selected song action");
    private javafx.scene.control.MenuItem deleteSong = new javafx.scene.control.MenuItem("Delete song");
    private javafx.scene.control.MenuItem addToPlaylist = new javafx.scene.control.MenuItem("Add to playlist");
    private javafx.scene.control.MenuItem playSong = new javafx.scene.control.MenuItem("Play song");

    private ServerAPI api;

    @Override
    public Menu setMenu(Song song, AnchorPane pane, int id, int selectedId, ActionEvent event) {

        menu.getItems().add(playSong);
        menu.getItems().add(addToPlaylist);
        menu.getItems().add(deleteSong);

        this.api = ServerAPI.getInstance();

        if (id != 1 || PreferencesLogin.getPrefs().getId() < 1){
            addToPlaylist.setVisible(false);
        }

        playSong.setOnAction(e -> {
                // TODO songs playing code
        });

        //only for songs reached from artist, it means id = 1
        addToPlaylist.setOnAction(e -> {
            // TODO add to playlist

        });

        deleteSong.setOnAction(e -> {
            System.out.println(song.getId());
            int songId = Integer.parseInt(song.getId());
            int playlistId = selectedId;
            if(api.deleteSong(songId, playlistId, id))
            {
                InitScreensFunctions initScreensFunctions = new InitScreensFunctions();
                try {
                    initScreensFunctions.initMainScreen(event);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                //TODO else condition in failure
            }

        });



        return menu;
    }
}
