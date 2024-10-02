package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Optional;

public class Scene0Controller {
    private VideoClub videoClub;

    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    @FXML
    private void onVideoClubClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene1-view.fxml");
    }

    @FXML
    private void onAdminClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    @FXML
    private void onSalirClick(ActionEvent event) {
        SceneManager.handleQuit(videoClub);
    }
}