package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;

/**
 * El controlador de la escena inicial del video club. Permite navegar hacia la pantalla de atención al cliente
 * o la pantalla de administración, y salir de la aplicación.
 */
public class Scene0Controller {
    private VideoClub videoClub;

    /**
     * Establece la instancia de VideoClub que se utilizará en este controlador.
     *
     * @param videoClub la instancia de VideoClub a asignar
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Maneja el evento de hacer clic en el botón "Atender Cliente". Cambia a la escena de atención al cliente.
     *
     * @param event el evento de acción generado por el click
     * @throws IOException si hay un error al cambiar la escena
     */
    @FXML
    private void onAtenderClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene1-view.fxml");
    }

    /**
     * Maneja el evento de hacer clic en el botón "Administrador". Cambia a la escena de administración.
     *
     * @param event el evento de acción generado por el click
     * @throws IOException si hay un error al cambiar la escena
     */
    @FXML
    private void onAdminClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    /**
     * Maneja el evento de hacer clic en el botón "Salir". Finaliza la aplicación de forma segura.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onSalirClick(ActionEvent event) {
        SceneManager.handleQuit(videoClub);
    }
}