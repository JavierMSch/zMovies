package org.example.zmovies.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.zmovies.Exceptions.ReportePlanillaException;
import org.example.zmovies.Exceptions.ReporteTextoException;
import org.example.zmovies.Modelos.VideoClub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Scene3Controller {
    private VideoClub videoClub;
    public Button btnCambiarOpciones;
    public Button btnVerPeliculas;
    @FXML
    private Button btnEditarPelicula;
    @FXML
    private Button btnEliminarPelicula;
    @FXML
    private Button btnAgregarPelicula;
    @FXML
    private Button verDetallePeliculaButton;
    private Button okButton;
    private Button cancelButton;
    @FXML
    private Pane contentPane;
    private Label titleLabel;
    private Label titleLabel2;
    private Label textLabel;
    private Label formLabel1;
    private TextField formField1;
    private HBox confirmLayout;
    private VBox formLayout;
    private boolean isGeneroMode = false;  // Estado inicial, comienza en modo "Películas"

    /**
     * Establece el VideoClub para el controlador.
     *
     * @param videoClub Objeto VideoClub a establecer.
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Maneja el evento de clic para ver películas.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onVerPeliculasClick(ActionEvent actionEvent) {
        verPeliculas("");
    }

    /**
     * Maneja el evento de clic para editar una película.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onEditarPeliculaClick(ActionEvent actionEvent) {
        editarPelicula();
    }

    /**
     * Maneja el evento de clic para volver a la escena anterior.
     *
     * @param actionEvent Evento de acción.
     * @throws IOException Si ocurre un error al cambiar de escena.
     */
    @FXML
    private void onVolverClick(ActionEvent actionEvent) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    /**
     * Maneja el evento de clic para eliminar una película.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onEliminarPeliculaClick(ActionEvent actionEvent) {
        eliminarPelicula();

    }

    /**
     * Maneja el evento de clic para generar un reporte de películas.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onReporteClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Crear un DirectoryChooser para seleccionar la carpeta de destino
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta para Guardar Reportes");

        File carpetaDestino = directoryChooser.showDialog(stage);

        if (carpetaDestino != null) {
            // Construir las rutas para los archivos de reporte
            String ruta = carpetaDestino.getAbsolutePath();

            try {
                videoClub.generarReportePeliculas(ruta);

                // Mostrar mensaje de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Reportes generados exitosamente!");
                alert.showAndWait();
            } catch (ReporteTextoException | ReportePlanillaException e) {
                // Manejar excepciones
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al generar los reportes");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * Maneja el evento de clic para agregar una nueva película.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onAgregarPeliculaClick(ActionEvent actionEvent) {
        agregarPelicula("Agregar Película", "Nombre de la Película");
    }

    /**
     * Maneja el evento de clic para cambiar entre las opciones de "Películas" y "Géneros".
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    private void onGenerosClick(ActionEvent actionEvent) {
        cambiarOpciones();
    }

    /**
     * Muestra una lista de géneros disponibles.
     */
    private void verGeneros() {
        contentPane.getChildren().clear();

        ObservableList<String> generos = FXCollections.observableArrayList(
                videoClub.obtenerListaStringGeneros()
        );

        if (generos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin Géneros");
            alert.setHeaderText(null);
            alert.setContentText("No hay géneros en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        VBox titulos = new VBox(10);
        titulos.getStyleClass().add("titulos");
        titulos.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("movie-list-layout");

        titleLabel = new Label("Géneros");
        titleLabel.getStyleClass().add("opt-title");
        textLabel = new Label("Seleccione un género para ver las películas");
        textLabel.getStyleClass().add("disclaimer");
        titulos.getChildren().addAll(titleLabel, textLabel);

        verDetallePeliculaButton = new Button("Ver Películas");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titulos, listView, verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        contentPane.getChildren().add(vbox);

        handleDoubleClicked(listView, verDetallePeliculaButton);

        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar un género para ver las películas.");
                alert.showAndWait();
            }
            else {
                verPeliculas(listView.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Agrega un nuevo género al sistema.
     *
     * @param title Título del formulario.
     * @param promptTxt Texto de solicitud para el campo de entrada.
     */
    private void agregarGenero(String title, String promptTxt) {
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

        handleEnterKey(formField1, okButton);

        okButton.setOnAction(e -> {
            String genero = formField1.getText();
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo Nombre no puede estar vacío.");
                alert.showAndWait();
            }
            else {
                videoClub.agregarGenero(genero);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Género agregado exitosamente!");
                alert.showAndWait();
                defaultPane();
            }
        });

        cancelButton.setOnAction(e -> defaultPane());
    }

    /**
     * Muestra un formulario para editar un género existente.
     */
    private void editarGenero() {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Editar Género");

        ObservableList<String> generos = FXCollections.observableArrayList(
                videoClub.obtenerListaStringGeneros()
        );

        if (generos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin Géneros");
            alert.setHeaderText(null);
            alert.setContentText("No hay géneros en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");

        titleLabel = new Label("Editar Genero");
        titleLabel.getStyleClass().add("opt-title");

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.getChildren().add(titleLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        contentPane.getChildren().add(vbox);
        handleDoubleClicked(listView, verDetallePeliculaButton);

        verDetallePeliculaButton.getStyleClass().add("genre-list-button");
        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar un género para editar.");
                alert.showAndWait();
            }
            else {
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                editarGeneroOpciones(listView.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Muestra un formulario para editar las opciones de un género existente.
     *
     * @param titulo Título actual del género.
     */
    private void editarGeneroOpciones(String titulo) {
        contentPane.getChildren().clear();

        // Crear los elementos visuales
        titleLabel = new Label("Editar Género");
        titleLabel.getStyleClass().add("menu-title");

        formLabel1 = new Label("Nuevo Nombre del Género");
        formLabel1.getStyleClass().add("opt-title");

        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText(titulo);

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");

        VBox vbox = new VBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("confirm-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);

        vbox.getStyleClass().add("form-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, formLabel1, formField1, confirmLayout);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(vbox.getPrefWidth());

        contentPane.getChildren().add(vbox);

        handleEnterKey(formField1, okButton);

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo 'Nombre' no puede estar vacío.");
                alert.showAndWait();
            }
            else{
                String nuevoNombre = formField1.getText();
                videoClub.editarGenero(titulo, nuevoNombre);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Género editado exitosamente!");
                alert.showAndWait();
                defaultPane();
            }
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());
    }

    /**
     * Muestra un formulario para eliminar un género existente.
     */
    private void eliminarGenero() {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Eliminar Género");

        ObservableList<String> generos = FXCollections.observableArrayList(
                videoClub.obtenerListaStringGeneros()
        );

        if (generos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin Géneros");
            alert.setHeaderText(null);
            alert.setContentText("No hay géneros en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");

        titleLabel = new Label("Eliminar Género");
        titleLabel.getStyleClass().add("opt-title");

        VBox vbox = new VBox(10);
        vbox.getChildren().add(titleLabel);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        contentPane.getChildren().add(vbox);

        handleDoubleClicked(listView, verDetallePeliculaButton);

        verDetallePeliculaButton.getStyleClass().add("genre-list-button");
        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar una pelicula para eliminar.");
                alert.showAndWait();
            }
            else if(listView.getSelectionModel().getSelectedItem().equals("SIN GENERO")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Operación No Permitida");
                alert.setContentText("No es posible eliminar SIN GÉNERO ya que es un género especial.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Género");
                alert.setHeaderText("¿Estás seguro de que quieres eliminar el género " + listView.getSelectionModel().getSelectedItem() + "?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    videoClub.eliminarGenero(listView.getSelectionModel().getSelectedItem());
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Operación exitosa");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Género eliminado exitosamente!");
                    alert2.showAndWait();
                    defaultPane();
                }
            }
        });
    }

    /**
     * Cambia las opciones entre el modo de "Películas" y "Géneros".
     */
    private void cambiarOpciones() {
        if (!isGeneroMode) {
            // Cambiar al modo de Géneros
            btnAgregarPelicula.setOnAction(e -> {
                agregarGenero("Agregar Género", "Nombre del Género");
                // Lógica para agregar género
            });

            btnVerPeliculas.setOnAction(e -> {
                verGeneros();
                // Lógica para ver géneros
            });

            btnEditarPelicula.setOnAction(e -> {
                editarGenero();
                // Lógica para editar género
            });

            btnEliminarPelicula.setOnAction(e -> {
                eliminarGenero();
                // Lógica para eliminar género
            });

            // Cambiar textos
            btnAgregarPelicula.setText("Agregar Género");
            btnVerPeliculas.setText("Ver Géneros");
            btnEditarPelicula.setText("Editar Género");
            btnEliminarPelicula.setText("Eliminar Género");
            btnCambiarOpciones.setText("Cambiar a Películas");

            isGeneroMode = true;  // Actualizamos el estado
        } else {
            // Restaurar las acciones originales de los botones (modo Películas)
            restoreActions();
            isGeneroMode = false;
        }
    }

    /**
     * Muestra una lista de películas disponibles en un género específico.
     *
     * @param genero Género de las películas a mostrar.
     */
    private void verPeliculas(String genero) {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Ver Detalle Película");

        titleLabel = new Label("Películas");
        titleLabel.getStyleClass().add("opt-title");
        textLabel = new Label("Seleccione una película para ver detalles");
        textLabel.getStyleClass().add("disclaimer");

        ObservableList<String> peliculas;

        if (genero.isEmpty()) {
            List<String> posiblesPeliculas = videoClub.obtenerListaPeliculasString();
            if (posiblesPeliculas == null || posiblesPeliculas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sin Películas");
                alert.setHeaderText(null);
                alert.setContentText("No hay películas en el sistema.");
                alert.showAndWait();
                return;
            }
            peliculas = FXCollections.observableArrayList(posiblesPeliculas);
        }
        else{
            if (videoClub.obtenerStringPeliculasGenero(genero) == null || videoClub.obtenerStringPeliculasGenero(genero).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sin Películas");
                alert.setHeaderText(null);
                alert.setContentText("No hay películas en el género seleccionado.");
                alert.showAndWait();
                verGeneros();
                return;
            }
            else {
                peliculas = FXCollections.observableArrayList(
                        videoClub.obtenerStringPeliculasGenero(genero).lines().toList()
                );
            }
        }

        ListView<String> listView = new ListView<>();
        listView.setItems(peliculas);
        listView.getStyleClass().add("movie-list");

        VBox vbox = new VBox(10);
        VBox titulos = new VBox(10);
        titulos.getStyleClass().add("titulos");
        titulos.getChildren().addAll(titleLabel, textLabel);
        titulos.setAlignment(Pos.CENTER);

        vbox.getStyleClass().add("movie-list-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titulos, listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        handleDoubleClicked(listView, verDetallePeliculaButton);

        verDetallePeliculaButton.getStyleClass().add("movie-list-button");
        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar una pelicula para ver detalles.");
                alert.showAndWait();
            }
            else {
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                vbox.getStyleClass().remove("movie-list-layout");
                vbox.getStyleClass().add("movie-detail-layout");
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem(), "verPeliculas"));
                contentPane.getChildren().add(vbox);
            }
        });
        contentPane.getChildren().add(vbox);
    }

    /**
     * Muestra un formulario para agregar una película.
     *
     *
     * @param title Título de la película.
     * @param promptTxt Texto de solicitud para el campo de entrada.
     */
    private void agregarPelicula(String title, String promptTxt) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");
        formLabel1 = new Label(promptTxt);
        formLabel1.getStyleClass().add("opt-title");
        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        okButton = new Button("Siguiente");
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

        contentPane.getChildren().addAll(formLayout);

        handleEnterKey(formField1, okButton);

        String[] currentStep = {"Nombre de la Película"};
        String[] nombre = {""};
        String[] genero = {""};
        String[] precio = {""};

        okButton.setOnAction(e -> {

            if (currentStep[0].equals("Género") && !isFieldEmpty(formField1)) {
                okButton.setText("Agregar");
            }

            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Nombre de la Película":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Nombre' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formLabel1.setText("Género");
                        nombre[0] = formField1.getText();

                        if (videoClub.existePelicula(nombre[0])) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Película ya existe");
                            alert.setContentText("La película ya existe en la base de datos.");
                            alert.showAndWait();
                            break;
                        }
                        currentStep[0] = "Género";
                        break;
                    }
                case "Género":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Género' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formLabel1.setText("Precio Semanal");
                        genero[0] = formField1.getText();
                        currentStep[0] = "Precio Semanal";
                        break;
                    }
                case "Precio Semanal":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo 'Precio Semanal' no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        precio[0] = formField1.getText();
                        if (precio[0].matches("[0-9]+")) {
                            videoClub.agregarPelicula(nombre[0], genero[0], Integer.parseInt(precio[0]));
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Operación exitosa");
                            alert.setHeaderText(null);
                            alert.setContentText("Película agregada exitosamente!");
                            alert.showAndWait();
                            defaultPane();
                            break;
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Precio no válido");
                            alert.setContentText("El precio no puede ser un número negativo.");
                            alert.showAndWait();
                        }
                    }
            }

            // Limpiar el campo de texto para el siguiente paso
            formField1.clear();
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());

    }

    /**
     * Muestra los detalles de una película en un StackPane.
     *
     * @param titulo Título de la película.
     * @return StackPane que contiene los detalles de la película.
     */
    private StackPane verDetallePelicula(String titulo, String caller) {
        contentPane.getChildren().clear();

        VBox vboxC = new VBox(10);
        // Limpiar el contentPane
        if (caller.equals("verPeliculas")) {
            cancelButton = new Button("Volver");
            cancelButton.getStyleClass().add("cancel-button");
            cancelButton.setOnAction(e -> returnToCaller(caller));
            vboxC.getChildren().add(cancelButton);
            vboxC.getStyleClass().add("movie-detail-layout");
            vboxC.setAlignment(Pos.CENTER);  // Alinear contenido dentro del VBox
        }
        // Crear VBox con detalles de la película
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-detail-layout");
        vbox.setAlignment(Pos.CENTER);  // Alinear contenido dentro del VBox

        // Crear los Labels con detalles de la película
        Label titleLabel = new Label("Detalles de la Película");
        titleLabel.getStyleClass().add("opt-title");
        Label datos = new Label(videoClub.detallesPelicula(titulo));
        datos.getStyleClass().add("detail");

        // Añadir los Labels al VBox
        vbox.getChildren().addAll(titleLabel, datos, vboxC);

        // Usar un StackPane para centrar el VBox dentro del contentPane
        StackPane stackPane = new StackPane(vbox);
        stackPane.setPrefSize(contentPane.getWidth(), contentPane.getHeight());
        StackPane.setAlignment(vbox, Pos.CENTER);  // Asegurar que el VBox esté centrado

        // Añadir StackPane al contentPane
        return stackPane;
    }

    /**
     * Muestra un formulario para seleccionar una película existente para editar.
     */
    private void editarPelicula(){
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Editar Película");

        titleLabel = new Label("Editar Película");
        titleLabel.getStyleClass().add("menu-title");

        ObservableList<String> peliculas = FXCollections.observableArrayList(
                videoClub.obtenerListaPeliculasString()
        );

        if (peliculas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin Películas");
            alert.setHeaderText(null);
            alert.setContentText("No hay películas en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(peliculas);
        listView.getStyleClass().add("movie-list");

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.getChildren().add(titleLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        handleDoubleClicked(listView, verDetallePeliculaButton);

        contentPane.getChildren().add(vbox);

        verDetallePeliculaButton.getStyleClass().add("movie-list-button");
        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar una pelicula para editar.");
                alert.showAndWait();
            }
            else {
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                vbox.getStyleClass().remove("movie-list-layout");
                vbox.getStyleClass().add("movie-detail-layout");
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem(), "editarPelicula"));
                verDetallePeliculaButton = new Button("Editar Película");
                verDetallePeliculaButton.getStyleClass().add("movie-list-button");
                vbox.getChildren().add(verDetallePeliculaButton);
                contentPane.getChildren().add(vbox);
                verDetallePeliculaButton.setOnAction(e2 -> {
                    editarPeliculaOpciones(listView.getSelectionModel().getSelectedItem(), "Titulo Actual");
                });
            }
        });
    }

    /**
     * Muestra un formulario para editar las opciones de una película existente.
     *
     * @param titulo Título actual de la película.
     * @param subtitulo Subtítulo para el formulario.
     */
    private void editarPeliculaOpciones(String titulo, String subtitulo) {
        contentPane.getChildren().clear();

        // Crear los elementos visuales
        titleLabel = new Label(titulo);
        titleLabel.getStyleClass().add("menu-title");
        titleLabel2 = new Label("Nuevo Título");
        titleLabel2.getStyleClass().add("opt-title");
        textLabel = new Label("Si deja el espacio en blanco se mantiene el valor actual");
        textLabel.getStyleClass().add("disclaimer");

        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText(titulo);

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");

        VBox vbox = new VBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("confirm-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);

        vbox.getStyleClass().add("form-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, titleLabel2, formField1, confirmLayout, textLabel);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(vbox.getPrefWidth());

        contentPane.getChildren().add(vbox);
        handleEnterKey(formField1, okButton);

        // Variables para el paso actual
        final String[] currentStep = {"Nuevo Titulo"};
        String[] nombre = {""};
        String[] genero = {""};
        String[] precio = {"-1"};

        okButton.setOnAction(e -> {
            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Nuevo Titulo":
                    titleLabel2.setText("Nuevo Género");
                    formField1.setPromptText(videoClub.obtenerGeneroPelicula(titulo).toUpperCase());
                    if (!isFieldEmpty(formField1)) {
                        nombre[0] = formField1.getText().toUpperCase();
                    }
                    currentStep[0] = "Nuevo Género";
                    break;
                case "Nuevo Género":
                    titleLabel2.setText("Nuevo Precio Semanal");
                    formField1.setPromptText(videoClub.obtenerPrecioPelicula(titulo));
                    if (!isFieldEmpty(formField1)) {
                        genero[0] = formField1.getText().toUpperCase();
                    }
                    currentStep[0] = "Nuevo Precio Semanal";
                    break;
                case "Nuevo Precio Semanal":
                    if (!isFieldEmpty(formField1)) {
                        precio[0] = formField1.getText();
                    }
                    videoClub.editarPelicula(titulo, nombre[0], genero[0], Integer.parseInt(precio[0]));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operación exitosa");
                    alert.setHeaderText(null);
                    alert.setContentText("Película editada exitosamente!");
                    alert.showAndWait();
                    defaultPane();
                    break;
            }

            // Limpiar el campo de texto para el siguiente paso
            formField1.clear();
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());
    }

    /**
     * Metodo para eliminar una pelicula
     */
    private void eliminarPelicula(){
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Eliminar Película");

        ObservableList<String> peliculas = FXCollections.observableArrayList(
                videoClub.obtenerListaPeliculasString()
        );

        if (peliculas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin Películas");
            alert.setHeaderText(null);
            alert.setContentText("No hay películas en el sistema.");
            alert.showAndWait();
            return;
        }

        ListView<String> listView = new ListView<>(peliculas);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        titleLabel = new Label("Eliminar Película");
        titleLabel.getStyleClass().add("menu-title");
        vbox.getChildren().add(titleLabel);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());

        contentPane.getChildren().add(vbox);

        handleDoubleClicked(listView, verDetallePeliculaButton);

        verDetallePeliculaButton.getStyleClass().add("movie-list-button");
        verDetallePeliculaButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Necesita seleccionar una pelicula para eliminar.");
                alert.showAndWait();
            }
            else {
                contentPane.getChildren().clear();
                vbox.getChildren().clear();
                vbox.getStyleClass().remove("movie-list-layout");
                vbox.getStyleClass().add("movie-detail-layout");
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem(), "eliminarPelicula"));
                verDetallePeliculaButton = new Button("Eliminar Película");
                verDetallePeliculaButton.getStyleClass().add("movie-list-button");
                vbox.getChildren().add(verDetallePeliculaButton);
                contentPane.getChildren().add(vbox);
                verDetallePeliculaButton.setOnAction(e2 -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Eliminar Película");
                    alert.setHeaderText("¿Estás seguro de que quieres eliminar la película " + listView.getSelectionModel().getSelectedItem() + "?");
                    alert.showAndWait();
                    if (alert.getResult().getButtonData().isDefaultButton()) {
                        videoClub.eliminarPelicula(listView.getSelectionModel().getSelectedItem());
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Operación exitosa");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Película eliminada exitosamente!");
                        alert2.showAndWait();
                        defaultPane();
                    }
                });
            }
        });

    }

    /**
     * Restaura las acciones y el texto original de los botones al modo "Películas".
     */
    private void restoreActions() {
        // Restaurar las acciones originales de los botones (modo Películas)
        btnAgregarPelicula.setOnAction(this::onAgregarPeliculaClick);
        btnVerPeliculas.setOnAction(this::onVerPeliculasClick);
        btnEditarPelicula.setOnAction(this::onEditarPeliculaClick);
        btnEliminarPelicula.setOnAction(this::onEliminarPeliculaClick);

        // Restaurar el texto original de los botones
        btnAgregarPelicula.setText("Agregar Película");
        btnVerPeliculas.setText("Ver Películas");
        btnEliminarPelicula.setText("Eliminar Película");
        btnEditarPelicula.setText("Editar Película");
        btnCambiarOpciones.setText("Cambiar a Géneros");
    }

    /**
     * Manejar doble clic en un elemento de la lista
     * @param listView
     * @param submitButton
     */
    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }

    /**
     * Metodo para manejar la tecla Enter en un campo de texto
     * @param currentField
     * @param nextControl
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
     * Metodo para mostrar la pantalla por defecto
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

    /**
     * Vuelve a la función que llamó.
     */
    private void returnToCaller(String lastCaller) {
        if (lastCaller.equals("verPeliculas")) {
            verPeliculas("");
        } else if (lastCaller.equals("verGeneros")) {
            verGeneros();
        } else if (lastCaller.equals("editarPelicula")) {
            editarPelicula();
        } else if (lastCaller.equals("eliminarPelicula")) {
            eliminarPelicula();
        }
    }

    /**
     * Verifica si un campo de texto está vacío.
     *
     * @param textField Campo de texto a verificar.
     * @return true si el campo de texto está vacío, false en caso contrario.
     */
    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }
}

