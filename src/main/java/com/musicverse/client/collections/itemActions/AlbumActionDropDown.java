package com.musicverse.client.collections.itemActions;

import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.objects.Album;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AlbumActionDropDown implements ItemActionDropDown<Album>{

    private ServerAPI api;

    private Menu menu = new javafx.scene.control.Menu("Selected album action");
    private javafx.scene.control.MenuItem deleteAlbum = new javafx.scene.control.MenuItem("Delete album");
    private javafx.scene.control.MenuItem addToAlbum = new javafx.scene.control.MenuItem("Add to album");
    private javafx.scene.control.MenuItem openAlbum = new javafx.scene.control.MenuItem("Open album");
    private javafx.scene.control.MenuItem albumDescription = new javafx.scene.control.MenuItem("Album description");
    @Override
    public Menu setMenu(Album item, AnchorPane pane, int id, int selectedId, ActionEvent event) {

        menu.getItems().add(openAlbum);
        menu.getItems().add(albumDescription);
        menu.getItems().add(addToAlbum);
        menu.getItems().add(deleteAlbum);

        this.api = ServerAPI.getInstance();

        if (id > 0){
            addToAlbum.setVisible(false);
            deleteAlbum.setVisible(false);
        }

        openAlbum.setOnAction(e -> {

            InitScreensFunctions initScreensFunctions = new InitScreensFunctions();

            try {
                initScreensFunctions.initSettingsScreen("Artist", "/ArtistSectionScreen.fxml",
                        pane, Integer.parseInt(item.getArtist_id()), Integer.parseInt(item.getId()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        albumDescription.setOnAction(e -> {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialog.setHeight(400);

            Button buttonCancel = new Button("Cancel");

            dialogVbox.getChildren().add(new Text("Album description"));

            dialogVbox.getChildren().add(new Text(item.getDescription()));

            dialogVbox.getChildren().add(buttonCancel);

            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();

            buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });
        });

        addToAlbum.setOnAction(e -> {
            // TODO add song to db with album_id: item.getId() and artist_id: item.getArtistId() and genre_id of this artist
        });

        deleteAlbum.setOnAction(e -> {
            // TODO with this method will be deleted all related songs to album and album itself but also is necesary to delete that songs in folder, for that you can use IDs of songs in Album collection "item"
            api.deleteCollection(Integer.parseInt(item.getId()), 1);
        });

        return menu;
    }
}
