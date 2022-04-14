package com.musicverse.client.player;

import lombok.Getter;
import lombok.val;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MediaManager {
    @Getter
    private final AudioPlayer player = new AudioPlayer();
    private final Map<String, File> loadedSongs = new HashMap<>();
    private String currentSong;

    public Optional<String> currentlyPlayingSong() {
        return Optional.ofNullable(currentSong);
    }

    public void setCurrentSong(String ID) throws MusicPlayerException {
        if (!loadedSongs.containsKey(ID)) throw new IllegalStateException("Song with ID \"" + ID + "\" is not loaded!");
        val song = loadedSongs.get(ID);
        player.loadSong(song);
    }

    public void loadSong(String ID, File file) {
        if (!file.exists()) throw new IllegalStateException("Cannot load a song from a file that does not exists!");
        if (loadedSongs.containsKey(ID)) throw new IllegalStateException("Cannot load a song with the same ID twice!");
        loadedSongs.put(ID, file);
    }

    public void downloadSong(String ID, File file, String url, String requestMethod, Map<String, String> requestProperties) throws IOException {
        if (loadedSongs.containsKey(ID)) throw new IllegalStateException("Cannot download a song with the same ID twice!");
        if (file.exists()) throw new IllegalStateException("Cannot download a song to a file that already exists!");
        val connection = (HttpURLConnection) new URL(url).openConnection();
        try {
            requestProperties.forEach(connection::setRequestProperty);
            connection.setRequestMethod(requestMethod);
            val response = connection.getResponseCode();
            if (!(response == 200)) {
                connection.disconnect();
                throw new IllegalStateException("Failed to download song: Response code " + response);
            }
            val in = connection.getInputStream();
            file.mkdirs();
            val out = new FileOutputStream(file);
            val buf = new byte[1024];
            var amount = 0;
            while((amount = in.read(buf)) > 0) {
                out.write(buf, 0, amount);
            }
            out.close();
        } finally {
            connection.disconnect();
        }
        loadSong(ID, file);
    }
}
