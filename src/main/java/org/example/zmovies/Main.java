package org.example.zmovies;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.zmovies.Controladores.SceneManager;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    /**
     * Inicia la aplicación JavaFX.
     * Configura el `VideoClub`, maneja posibles errores de SQL y cambia a la escena inicial.
     *
     * @param stage escenario principal de la aplicación
     * @throws IOException si ocurre un error al cargar los recursos
     */
    @Override
    public void start(Stage stage) throws IOException {
        VideoClub videoClub = new VideoClub();
        try {
            videoClub.start();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar la base de datos");
            alert.setContentText("No se pudo iniciar la base de datos y se cerrará el programa. Por favor, consulte con los desarrolladores.");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }

        SceneManager.setVideoClub(videoClub);
        SceneManager.setStage(stage);
        stage.setTitle("zMovies - VideoClub");
        Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResourceAsStream("/images/icon.png")));
        stage.getIcons().add(icon);
        SceneManager.switchScene("/fxml/scene0-view.fxml");

        stage.show();
    }

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}