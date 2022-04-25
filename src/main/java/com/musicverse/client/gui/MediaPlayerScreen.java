package com.musicverse.client.gui;

import com.musicverse.client.objects.Song;
import com.musicverse.client.player.AudioPlayer;
import com.musicverse.client.player.MediaManager;
import com.musicverse.client.player.MusicPlayerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerScreen {

    @FXML
    private Label albumLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Slider durationSlider;

    @FXML
    private Label leftSeconds;

    @FXML
    private Button nextButton;

    @FXML
    private Button playButton;

    @FXML
    private Button previousButton;

    @FXML
    private Label songLabel;

    private int pause = 0;

    private AudioPlayer audioPlayer;

    private double timeLeft = 0;

    private double pos = 0;

    private double duration = 0;

    public MediaPlayerScreen() throws MusicPlayerException {
    }

    @FXML
    void onNextBtnClick(ActionEvent event) {

    }

    @SneakyThrows
    @FXML
    void onPlayBtnClick(ActionEvent event) {

        if (Objects.equals(playButton.getText(), "Pause")){
            playButton.setText("Resume");
            pause = 1;
            audioPlayer.pause();
        }

        else if (Objects.equals(playButton.getText(), "Resume")){
            playButton.setText("Pause");
            pause = 0;
            givenUsingTimer_whenSchedulingTaskOnce_thenCorrect();
            audioPlayer.resume();
        }

    }

    @FXML
    void onPreviousBtnClick(ActionEvent event) {

    }

    @SneakyThrows
    @FXML
    void sliderMouseClicked(MouseEvent event) {
        audioPlayer.seekTo(Duration.ofSeconds((long) durationSlider.getValue()));
    }

    public void load(Song song) throws MusicPlayerException {

        MediaManager.instance.setCurrentSong(Integer.parseInt(song.getId()));
        audioPlayer = MediaManager.instance.getPlayer();
        audioPlayer.play();

        playButton.setText("Pause");

        previousButton.setVisible(false);
        nextButton.setVisible(false);

        songLabel.setText(song.getName());
        albumLabel.setText(song.getAlbum());
        artistLabel.setText(song.getArtist());

        durationSlider.setMin(0);
        durationSlider.setMax(audioPlayer.getLength().getSeconds());
        durationSlider.setValue(audioPlayer.getCurrentPlaybackPosition().getSeconds());
        givenUsingTimer_whenSchedulingTaskOnce_thenCorrect();

        duration = audioPlayer.getLength().getSeconds();

        Thread timerThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    try {
                        leftSeconds.setText(
                                String.valueOf((audioPlayer.getLength().getSeconds() - audioPlayer.getCurrentPlaybackPosition().getSeconds()) / 60 )
                        + ":" + String.valueOf((audioPlayer.getLength().getSeconds() - audioPlayer.getCurrentPlaybackPosition().getSeconds()) % 60 ));
                    } catch (MusicPlayerException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        timerThread.start();
    }

    @SneakyThrows
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                durationSlider.setValue(audioPlayer.getCurrentPlaybackPosition().getSeconds());
                if (durationSlider.getValue() >= duration)
                    return;

                if (pause != 1)
                    givenUsingTimer_whenSchedulingTaskOnce_thenCorrect();
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        timer.schedule(task, delay);
    }
}
