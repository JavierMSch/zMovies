package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;

/**
 * Clase base para los controladores de la aplicación que interactúan con el videoclub.
 * Proporciona métodos y propiedades comunes a todos los controladores.
 */
public abstract class Controller {
    protected VideoClub videoClub;
    @FXML
    protected Pane contentPane;

    protected abstract void onSalirClick(ActionEvent event) throws IOException;

    /**
     * Establece la instancia de VideoClub que se utilizará en el controlador.
     *
     * @param videoClub Instancia de VideoClub a asignar
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Restablece el panel de contenido a su estado predeterminado.
     * Limpia el contenido actual del panel y muestra el icono de la aplicación en el centro del panel.
     */
    protected void mostrarDefault() {
        contentPane.getChildren().clear();

        Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResourceAsStream("/images/icon.png")));
        ImageView iconItem = new ImageView(icon);
        iconItem.setFitWidth(150);
        iconItem.setFitHeight(150);
        iconItem.setPreserveRatio(true);
        double centerX = (contentPane.getWidth() - iconItem.getFitWidth()) / 2;
        double centerY = (contentPane.getHeight() - iconItem.getFitHeight()) / 2;
        iconItem.setLayoutX(centerX);
        iconItem.setLayoutY(centerY);
        contentPane.getChildren().add(iconItem);
    }

    /**
     * Formatea un RUT (Rol Único Tributario) a un formato estándar.
     *
     * @param input RUT a formatear
     * @return RUT formateado
     */
    protected String formatoRut(String input) {
        if (input == null) {
            return "";
        }
        String lastChar = "";
        if (input.endsWith("k") || input.endsWith("K")) {
            lastChar = "K";
        }
        input = input.replaceAll("\\D", "");
        input += lastChar;

        if (input.length() < 8 || input.length() > 10) {
            return input;
        }

        int offset = input.length() - 8;

        String part1 = input.substring(0, 1 + offset);
        String part2 = input.substring(1 + offset, 4 + offset);
        String part3 = input.substring(4 + offset, 7 + offset);
        String part4 = input.substring(7 + offset);
        return part1 + "." + part2 + "." + part3 + "-" + part4;
    }

    /**
     * Verifica si un campo de texto está vacío.
     *
     * @param textField Campo de texto a verificar
     * @return true si el campo de texto está vacío, false en caso contrario
     */
    protected boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    /**
     * Asigna un evento al TextField para que al presionar la tecla Enter se cambie el foco al Control recibido.
     *
     * @param currentField TextField actual
     * @param nextControl Control al que se cambiará el foco
     */
    protected void handleEnterKey(TextField currentField, Control nextControl) {
        currentField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                if (nextControl instanceof TextField) {
                    nextControl.requestFocus();
                } else if (nextControl instanceof Button) {
                    ((Button) nextControl).fire();
                }
            }
        });
    }

    /**
     * Asigna un evento al ListView para que al hacer doble click en un item se ejecute el evento del botón recibido.
     *
     * @param listView ListView en el que se hará doble click
     * @param submitButton Button que se ejecutará al hacer doble click
     */
    protected void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }
}
