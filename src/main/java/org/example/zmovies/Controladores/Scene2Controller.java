package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controlador de la escena de administración del video club.
 * Permite navegar a las vistas de administración de películas y administración de clientes, y volver a la vista inicial.
 */
public class Scene2Controller {

    /**
     * Maneja el evento de click en el botón de películas.
     * Cambia la escena a la vista de películas.
     *
     * @param event el evento de acción que desencadenó el método
     * @throws IOException si ocurre un error al cargar la nueva escena
     */
    @FXML
    private void onPeliculasClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene3-view.fxml");
    }

    /**
     * Maneja el evento de click en el botón de clientes.
     * Cambia la escena a la vista de clientes.
     *
     * @param event el evento de acción que desencadenó el método
     * @throws IOException si ocurre un error al cargar la nueva escena
     */
    @FXML
    private void onClientesClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene4-view.fxml");
    }

    /**
     * Maneja el evento de click en el botón de volver.
     * Cambia la escena a la vista inicial.
     *
     * @param event el evento de acción que desencadenó el método
     * @throws IOException si ocurre un error al cargar la nueva escena
     */
    @FXML
    private void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene0-view.fxml");
    }
}