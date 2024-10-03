package org.example.zmovies.Controladores;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SceneManager {
    private static Stage primaryStage;
    private static VideoClub videoClub;

    public static void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.setResizable(false);

        stage.setOnCloseRequest(event -> {
            event.consume();
            handleQuit(videoClub);
        });
    }

    public static void setVideoClub(VideoClub videoClub) {
        SceneManager.videoClub = videoClub;
    }

    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader root = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
        Scene scene = new Scene(root.load());

        Object controller = root.getController();
        if (controller instanceof Scene0Controller) {
            ((Scene0Controller) controller).setVideoClub(videoClub);
        } else if (controller instanceof Scene1Controller) {
            ((Scene1Controller) controller).setVideoClub(videoClub);
        } else if (controller instanceof Scene3Controller) {
            ((Scene3Controller) controller).setVideoClub(videoClub);
        } else if (controller instanceof Scene4Controller) {
            ((Scene4Controller) controller).setVideoClub(videoClub);
        }

        String css = Objects.requireNonNull(SceneManager.class.getResource("/styles/style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
    }

    public static void handleQuit(VideoClub videoClub) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Estás seguro de que quieres salir?");
        alert.setContentText("Se guardarán tus cambios antes de salir.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                videoClub.insertarDatos();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Error al guardar datos la base de datos");
                errorAlert.setContentText("Se cerrará el programa y se perderán los cambios realizados. Por favor, consulte con los desarrolladores.");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
            closeStage();
        }
    }

    private static void closeStage() {
        primaryStage.close();
    }
}