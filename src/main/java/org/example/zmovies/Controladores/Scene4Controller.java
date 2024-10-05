package org.example.zmovies.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;

/**
 * Controlador de la escena de administración de clientes.
 */
public class Scene4Controller {
    private VideoClub videoClub;
    @FXML
    private Pane contentPane;
    private Button okButton;
    private Button cancelButton;
    @FXML
    private Label titleLabel;
    private Button verDetalleClienteButton;
    private Label formLabel1;
    private TextField formField1;
    private HBox confirmLayout;
    private VBox formLayout;

    /**
     * Establece la instancia de VideoClub que se utilizará en este controlador.
     *
     * @param videoClub Instancia de VideoClub a asignar
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Llama a la función para agregar un cliente en respuesta a un clic en el botón.
     * @param event Evento de clic en el botón.
     */
    @FXML
    private void onAgregarClienteClick(ActionEvent event) {
        agregarCliente("Agregar Cliente", "Rut del cliente");
    }

    /**
     * Llama a la función para mostrar la lista de clientes en respuesta a un clic en el botón.
     * @param event Evento de clic en el botón.
     */
    @FXML
    private void onVerClienteClick(ActionEvent event) {
        verClientes();
    }

    /**
     * Maneja el evento de clic en el botón de volver.
     * @param event Evento de clic en el botón.
     * @throws IOException Excepción de E/S.
     */
    @FXML
    private void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    /**
     * Muestra un formulario para agregar un cliente.
     * @param title Título del formulario.
     * @param promptTxt Texto de la etiqueta del campo de texto.
     */
    private void agregarCliente(String title, String promptTxt) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");

        formLabel1 = new Label(promptTxt);
        formLabel1.getStyleClass().add("opt-title");

        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String input = formField1.getText();
                String rutFormateado = formatoRut(input);
                formField1.setText(rutFormateado);
            }
        });

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");
        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("confirm-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);

        formLayout = new VBox(10);
        formLayout.getStyleClass().add("form-layout");
        formLayout.getChildren().addAll(titleLabel, formLabel1, formField1, confirmLayout);

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(formLayout.getPrefWidth());
        confirmLayout.setPrefWidth(formLayout.getPrefWidth());
        spacer.setMinWidth(confirmLayout.getMinWidth() * 0.6);

        handleEnterKey(formField1, okButton);

        contentPane.getChildren().addAll(formLayout);

        // Variables para el paso actual
        String[] currentStep = {"Rut del cliente"};
        String[] rut = {""};
        String[] nombre = {""};
        String[] correo = {""};
        String[] telefono = {""};

        okButton.setOnAction(e -> {
            // Obtener el valor actual del campo de texto
            String valorActual = formField1.getText();

            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Rut del cliente":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Rut del cliente' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    } else {
                        formLabel1.setText("Nombre completo");
                        rut[0] = formatoRut(valorActual);  // Se usa el array
                        currentStep[0] = "Nombre completo";
                        break;
                    }
                case "Nombre completo":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Nombre completo' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    } else {
                        formLabel1.setText("Correo electrónico");
                        nombre[0] = valorActual;  // Se usa el array
                        currentStep[0] = "Correo electrónico";
                        break;
                    }
                case "Correo electrónico":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Correo electrónico' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    } else {
                        formLabel1.setText("Numero telefónico");
                        correo[0] = valorActual;  // Se usa el array
                        currentStep[0] = "Número telefónico";
                        break;
                    }
                case "Número telefónico":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Número telefónico' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    } else {
                        telefono[0] = valorActual;  // Se usa el array
                        videoClub.agregarCliente(rut[0], nombre[0], correo[0], telefono[0]);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Operación exitosa");
                        alert.setHeaderText(null);
                        alert.setContentText("El cliente ha sido agregado exitosamente.");
                        alert.showAndWait();
                        defaultPane(); // Puedes definir esta función para restaurar la vista por defecto.
                        break;
                    }
            }

            // Limpiar el campo de texto para el siguiente paso
            formField1.clear();
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());

    }

    /**
     * Muestra una lista de clientes en un ListView.
     * Permite al usuario seleccionar un cliente y ver sus detalles.
     */
    private void verClientes(){
        contentPane.getChildren().clear();
        verDetalleClienteButton = new Button("Ver Detalle Cliente");
        ObservableList<String> clientes = FXCollections.observableArrayList(
                videoClub.obtenerListaRutNombreClientes()
        );

        if (clientes.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No hay clientes");
            alert.setHeaderText(null);
            alert.setContentText("No hay clientes registrados en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(clientes);
        listView.getStyleClass().add("movie-list");

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetalleClienteButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        handleDoubleClicked(listView, verDetalleClienteButton);

        verDetalleClienteButton.getStyleClass().add("movie-list-button");
        verDetalleClienteButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar un cliente para ver sus detalles.");
                alert.showAndWait();
            }
            else {
                String input = listView.getSelectionModel().getSelectedItem();
                String[] partes = input.split("-");
                String rutSinGuion = partes[0].trim();
                String digitoVerificador = partes[1].split(" ")[0].trim(); // Separar con espacio y tomar el primer elemento
                String rutCompleto = rutSinGuion + "-" + digitoVerificador;
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                vbox.getStyleClass().remove("movie-list-layout");
                vbox.getStyleClass().add("movie-detail-layout");
                vbox.getChildren().add(verDetallesCLiente(rutCompleto));
                contentPane.getChildren().add(vbox);
            }
        });

        contentPane.getChildren().add(vbox);
    }

    /**
     * Muestra los detalles de un cliente en un StackPane.
     * @param rut Rut del cliente.
     * @return
     */
    private StackPane verDetallesCLiente(String rut){
        contentPane.getChildren().clear();
        String detalles = videoClub.detallesCliente(rut);
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-detail-layout");
        vbox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Detalles del cliente");
        titleLabel.getStyleClass().add("opt-title");
        Label detallesC = new Label(detalles);
        detallesC.getStyleClass().add("detail");

        vbox.getChildren().addAll(titleLabel, detallesC);

        StackPane stackPane = new StackPane(vbox);
        stackPane.setPrefSize(contentPane.getWidth(), contentPane.getHeight());
        StackPane.setAlignment(vbox, Pos.CENTER);

        return stackPane;
    }

    /**
     * Maneja el evento de doble clic en un ListView.
     * @param listView ListView actual.
     * @param submitButton Botón a presionar al hacer doble clic.
     */
    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }

    /**
     * Maneja el evento de presionar la tecla Enter en un campo de texto.
     * @param currentField Campo de texto actual.
     * @param nextControl Control al que se moverá el foco.
     */
    private void handleEnterKey(TextField currentField, Control nextControl) {
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
     * Formatea un RUT en el formato XXX.XXX.XXX-X.
     * @param input RUT a formatear.
     * @return
     */
    private String formatoRut(String input) {
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
     * @param textField
     * @return
     */
    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    /**
     * Restaura la vista por defecto.
     */
    private void defaultPane() {
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
}
