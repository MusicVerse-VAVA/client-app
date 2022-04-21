package com.musicverse.client;

import com.musicverse.client.collections.Utils;
import com.musicverse.client.objects.Artist;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import com.musicverse.client.api.ServerAPI;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InitScreensFunctions {

    /*@SneakyThrows
    public void initMainScreen(ActionEvent event, HashMap<Integer,String> list) throws IOException{
        PreferencesLogin preferencesLogin = new PreferencesLogin();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/MainScreen.fxml")));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        com.musicverse.client.gui.MainScreen controler = loader.getController();
        controler.setRole(PreferencesLogin.getPrefs().getRole());
        String[] items = {"Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout"};


        //Pridanie public playlistov k private playlistom -> pre guesta sa zobrazia only public playlisty
        try {
            val api = ServerAPI.getInstance();
            val playlists = api.getPublicPlaylists();
            /*for (int i = 0; i<playlists.size();i++) {
                list.put(playlists.get(i).getInt("id"),playlists.get(i).getString("name"));
            }*/
           /* val genres = api.getGenres();

            ArrayList<String> genresList = new ArrayList<String>();
            for (int i = 0; i < genres.size(); i++)
                genresList.add(genres.get(i).getString("genre"));

            controler.setRectangles(genresList);

            val response = api.getUserPlaylists(PreferencesLogin.getPrefs().getId());

        }
        catch (Exception e){
            e.printStackTrace();
        }


        controler.setPlaylists(list);
        window.setTitle("MusicVerse");
        window.setScene(scene);
        window.show();
    }*/

    public void initMainScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/MainScreen.fxml")));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        com.musicverse.client.gui.MainScreen controler = loader.getController();
        controler.setRole(PreferencesLogin.getPrefs().getRole());
        //String[] items = {"Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout","Rock", "Pop", "Metal", "Classical", "Chill", "Christmas", "Workout"};
        try {
            val api = ServerAPI.getInstance();
            val playlists = api.getPublicPlaylists();
            /*for (int i = 0; i<playlists.size();i++) {
                list.put(playlists.get(i).getInt("id"),playlists.get(i).getString("name"));
            }*/
            val genres = api.getGenres();

            ArrayList<String> genresList = new ArrayList<String>();
            for (int i = 0; i < genres.size(); i++)
                genresList.add(genres.get(i).getString("genre"));

            controler.setRectangles(genresList);

            val response = api.getUserPlaylists(PreferencesLogin.getPrefs().getId());
            controler.setPlaylists(Utils.createPlaylist(response));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        window.setTitle("MusicVerse");
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initRegistrationScreen(ActionEvent actionEvent, int role) throws IOException{
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
        controler.setLabelText(label, role);
        Scene scene = new Scene(root, 1200,600);
        window.setTitle(Title);
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initLoginScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScreen.fxml")));
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setTitle("Log in user");
        window.setScene(scene);
        window.show();
    }

    @SneakyThrows
    public void initSettingsScreen(String Title, String fxml, AnchorPane pane, int from, int shownTable) throws IOException{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent root = loader.load();
        Stage window = (Stage) pane.getScene().getWindow();
        if (Objects.equals(Title, "Artist")){
            com.musicverse.client.gui.ArtistSectionScreen controler = loader.getController();
            //controler.setSettings(from);
            controler.load(from, shownTable);
        }else if (Objects.equals(Title, "Settings")){
            com.musicverse.client.gui.SettingsScreen controler = loader.getController();
           // controler.setFrom(from);
        }else if (Objects.equals(Title, "Admin")){
            com.musicverse.client.gui.AdminSectionScreen controler = loader.getController();
            //controler.setSettings(from);
        }
        Scene scene = new Scene(root, 1200,600);
        window.setScene(scene);
        window.setTitle(Title);
        window.show();
    }


}
