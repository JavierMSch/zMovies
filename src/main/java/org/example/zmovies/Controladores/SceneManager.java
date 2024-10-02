package org.example.zmovies.Controladores;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage primaryStage;
    private static VideoClub videoClub;

    public static void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.setResizable(false);
    }

    public static void setVideoClub(VideoClub videoClub) {
        SceneManager.videoClub = videoClub;
    }

    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader root = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
        Scene scene = new Scene(root.load());

        Object controller = root.getController();
        if (controller instanceof Scene1Controller) {
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

    public static void closeStage() {
        primaryStage.close();
    }
}