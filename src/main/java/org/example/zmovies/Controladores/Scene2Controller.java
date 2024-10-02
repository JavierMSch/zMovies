package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Scene2Controller {

    @FXML
    private void onPeliculasClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene3-view.fxml");
    }

    @FXML
    private void onClientesClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene4-view.fxml");
    }

    @FXML
    private void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene0-view.fxml");
    }
}
