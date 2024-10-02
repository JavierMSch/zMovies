package org.example.zmovies.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.Objects;

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
    private Label formLabel2;
    private TextField formField1;
    private TextField formField2;
    private HBox confirmLayout;
    private VBox formLayout;
    private boolean isGeneroMode = false;

    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    @FXML
    protected void onAgregarClienteClick(ActionEvent event) {
        agregarCliente("Agregar Cliente", "Rut del cliente");
    }

    @FXML
    protected void onVerClienteClick(ActionEvent event) {
        verClientes();
    }

    @FXML
    protected void onVerRentasClick(ActionEvent event) {
        System.out.println("Ver Cliente");
    }

    private void agregarCliente(String title, String promptTxt) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("opt-title");
        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText(promptTxt);
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
        formLayout.getChildren().addAll(titleLabel, formField1, confirmLayout);

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(formLayout.getPrefWidth());
        confirmLayout.setPrefWidth(formLayout.getPrefWidth());
        spacer.setMinWidth(confirmLayout.getMinWidth() * 0.6);

        contentPane.getChildren().addAll(formLayout);

        // Variables para el paso actual
        final String[] currentStep = {"Rut del cliente"};

        okButton.setOnAction(e -> {
            // Obtener el valor actual del campo de texto
            String valorActual = formField1.getText();
            System.out.println("Valor ingresado: " + valorActual);

            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Rut del cliente":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Rut no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formField1.setPromptText("Nombre completo");
                        currentStep[0] = "Nombre completo";
                        break;
                    }
                case "Nombre completo":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Nombre no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formField1.setPromptText("Correo electronico");
                        currentStep[0] = "Correo electronico";
                        break;
                    }
                case "Correo electronico":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Correo no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formField1.setPromptText("Numero telefonico");
                        currentStep[0] = "Numero telefonico";
                        break;
                    }
                case "Numero telefonico":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Numero no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        System.out.println("Formulario completado.");
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

    private void verClientes(){
        contentPane.getChildren().clear();
        verDetalleClienteButton = new Button("Ver Detalle Cliente");
        ObservableList<String> peliculas = FXCollections.observableArrayList(
                videoClub.obtenerListaClientes()
        );

        ListView<String> listView = new ListView<>(peliculas);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetalleClienteButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());
        verDetalleClienteButton.getStyleClass().add("movie-list-button");
        handleDoubleClicked(listView, verDetalleClienteButton);
        verDetalleClienteButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar un cliente para ver sus detalles.");
                alert.showAndWait();
            }
            else {
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                vbox.getChildren().add(verDetallesCLiente(listView.getSelectionModel().getSelectedItem()));
                contentPane.getChildren().add(vbox);
            }
        });
        contentPane.getChildren().add(vbox);
    }

    private StackPane verDetallesCLiente(String nombre){
        contentPane.getChildren().clear();  // Limpiar el contentPane

        // Crear VBox con detalles de la película
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-detail-layout");
        vbox.setAlignment(Pos.CENTER);  // Alinear contenido dentro del VBox

        // Crear los Labels con detalles de la película
        Label titleLabel = new Label("Nombre: " + nombre);
        titleLabel.getStyleClass().add("opt-title");
        Label directorLabel = new Label("Rut: [rut del cliente]");
        directorLabel.getStyleClass().add("detail");
        Label anioLabel = new Label("Correo Electronico : [Correo del cliente]");
        anioLabel.getStyleClass().add("detail");
        Label generoLabel = new Label("Numero Telefonico: [Numero de telefono]");
        generoLabel.getStyleClass().add("detail");

        // Añadir los Labels al VBox
        vbox.getChildren().addAll(titleLabel, directorLabel, anioLabel, generoLabel);

        // Usar un StackPane para centrar el VBox dentro del contentPane
        StackPane stackPane = new StackPane(vbox);
        stackPane.setPrefSize(contentPane.getWidth(), contentPane.getHeight());
        StackPane.setAlignment(vbox, Pos.CENTER);  // Asegurar que el VBox esté centrado

        // Añadir StackPane al contentPane
        return stackPane;
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    private void defaultPane() {
        contentPane.getChildren().clear();

        Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResourceAsStream("/images/icon.png")));
        ImageView iconItem = new ImageView(icon);
        iconItem.setFitWidth(75);
        iconItem.setFitHeight(75);
        iconItem.setPreserveRatio(true);
        double centerX = (contentPane.getWidth() - iconItem.getFitWidth()) / 2;
        double centerY = (contentPane.getHeight() - iconItem.getFitHeight()) / 2;
        iconItem.setLayoutX(centerX);
        iconItem.setLayoutY(centerY);
        contentPane.getChildren().add(iconItem);
    }

    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }

    @FXML
    protected void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }
}
