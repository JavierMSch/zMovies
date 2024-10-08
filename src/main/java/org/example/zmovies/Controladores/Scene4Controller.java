package org.example.zmovies.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Controlador de la escena de administración de clientes.
 */
public class Scene4Controller extends Controller {
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
     * @param event Evento de click en el botón.
     * @throws IOException Excepción de E/S.
     */
    @Override
    @FXML
    protected void onSalirClick(ActionEvent event) throws IOException {
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
                        mostrarDefault(); // Puedes definir esta función para restaurar la vista por defecto.
                        break;
                    }
            }

            // Limpiar el campo de texto para el siguiente paso
            formField1.clear();
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> mostrarDefault());
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
     * @return StackPane con los detalles del cliente.
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
        Button backButton = new Button("Volver");
        backButton.setOnAction(e -> verClientes());

        vbox.getChildren().addAll(titleLabel, detallesC, backButton);

        StackPane stackPane = new StackPane(vbox);
        stackPane.setPrefSize(contentPane.getWidth(), contentPane.getHeight());
        StackPane.setAlignment(vbox, Pos.CENTER);

        return stackPane;
    }
}
