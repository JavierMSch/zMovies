package org.example.zmovies;

//import java.util.Scanner;
//import org.example.zmovies.Modelos.*;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        VideoClub videoClub = new VideoClub();
//        InterfazUsuario ui = new InterfazUsuario(scanner, videoClub);
//
//        ui.start();
//
//        scanner.close();
//    }
//}


import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.zmovies.Controladores.SceneManager;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VideoClub videoClub = new VideoClub();
        try {
            videoClub.start();
        } catch (Exception e) {
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
        stage.setTitle("VideoClub Manager");
        Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResourceAsStream("/images/icon.png")));
        stage.getIcons().add(icon);
        SceneManager.switchScene("/fxml/scene0-view.fxml");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}