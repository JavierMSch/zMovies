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

/**
 * Clase principal de la aplicación.
 * Inicia la aplicación JavaFX y maneja la configuración inicial.
 */
public class Main extends Application {
    /**
     * Inicia la aplicación JavaFX.
     * Configura el `VideoClub`, maneja posibles errores de SQL y cambia a la escena inicial.
     *
     * @param stage Escenario principal de la aplicación
     * @throws IOException si ocurre un error al cargar los recursos
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            VideoClub videoClub = new VideoClub();
            videoClub.start();

            SceneManager.setVideoClub(videoClub);
            SceneManager.setStage(stage);
            stage.setTitle("zMovies - VideoClub");
            Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResourceAsStream("/images/icon.png")));
            stage.getIcons().add(icon);
            SceneManager.switchScene("/fxml/scene0-view.fxml");

            stage.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar la base de datos");
            alert.setContentText("No se pudo iniciar la base de datos y se cerrará el programa. Por favor, consulte con los desarrolladores.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Lanza la aplicación.
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}



// Main alternativo con interfaz de usuario por consola (versión avance)
// Para usarla, comentar la versión con ventanas (excepto el package) y descomentar la versión con consola

//import java.util.Scanner;
//import org.example.zmovies.Modelos.*;
//import org.example.zmovies.Vistas.*;
//
///**
// * Clase principal de la aplicación.
// * Inicia la aplicación con una interfaz de usuario por consola.
// */
//public class Main {
//    /**
//     * Inicia la aplicación con una interfaz de usuario por consola.
//     *
//     * @param args Argumentos de la línea de comandos
//     */
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        try {
//              VideoClub videoClub = new VideoClub();
//              InterfazUsuarioConsola ui = new InterfazUsuarioConsola(scanner, videoClub);
//
//              ui.start();
//
//              scanner.close();
//        } catch (UnsupportedEncodingException e) {
//            System.err.println("Error al iniciar la base de datos");
//            e.printStackTrace();
//        }
//    }
//}