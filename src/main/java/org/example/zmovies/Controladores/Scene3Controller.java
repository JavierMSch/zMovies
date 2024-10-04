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
    private TextField formField1;
    private HBox confirmLayout;
    private VBox formLayout;
    private boolean isGeneroMode = false;  // Estado inicial, comienza en modo "Películas"

    /**
     * Establece el VideoClub para el controlador.
     *
     * @param videoClub El objeto VideoClub a establecer.
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Maneja el evento de clic para ver películas.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    private void onVerPeliculasClick(ActionEvent actionEvent) {
        verPeliculas("");
    }

    /**
     * Maneja el evento de clic para editar una película.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    private void onEditarPeliculaClick(ActionEvent actionEvent) {
        editarPelicula();
    }

    /**
     * Maneja el evento de clic para volver a la escena anterior.
     *
     * @param actionEvent El evento de acción.
     * @throws IOException Si ocurre un error al cambiar de escena.
     */
    @FXML
    private void onVolverClick(ActionEvent actionEvent) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    /**
     * Maneja el evento de clic para eliminar una película.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    private void onEliminarPeliculaClick(ActionEvent actionEvent) {
        EliminarPelicula();
    }

    /**
     * Maneja el evento de clic para generar un reporte de películas.
     *
     * @param actionEvent El evento de acción.
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
                alert.setTitle("Éxito");
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
     * @param actionEvent El evento de acción.
     */
    @FXML
    private void onAgregarPeliculaClick(ActionEvent actionEvent) {
        agregarPelicula("Agregar Película", "Nombre de la Película");
    }

    /**
     * Maneja el evento de clic para cambiar entre las opciones de "Películas" y "Géneros".
     *
     * @param actionEvent El evento de acción.
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
                videoClub.obtenerListaGeneros()
        );
        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        VBox titulos = new VBox(10);
        titulos.getStyleClass().add("titulos");
        titulos.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("movie-list-layout");

        titleLabel = new Label("Generos");
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
     * @param title El título del formulario.
     * @param promptTxt El texto de solicitud para el campo de entrada.
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
                alert.setTitle("Éxito");
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
                videoClub.obtenerListaGeneros()
        );
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
     * @param titulo El título actual del género.
     */
    private void editarGeneroOpciones(String titulo) {
        contentPane.getChildren().clear();

        // Crear los elementos visuales
        titleLabel = new Label("Editar Género");
        titleLabel.getStyleClass().add("opt-title");

        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText("Nuevo Nombre del Género");

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
        vbox.getChildren().addAll(titleLabel, formField1, confirmLayout);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(vbox.getPrefWidth());

        contentPane.getChildren().add(vbox);

        handleEnterKey(formField1, okButton);

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo Nombre no puede estar vacío.");
                alert.showAndWait();
            }
            else{
                String nuevoNombre = formField1.getText();
                videoClub.editarGenero(titulo, nuevoNombre);
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
                videoClub.obtenerListaGeneros()
        );
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
                alert.setHeaderText("Operación no Permitida");
                alert.setContentText("No es posible eliminar SIN GENERO ya que es un genero especial");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Género");
                alert.setHeaderText("¿Estás seguro de que quieres eliminar el género " + listView.getSelectionModel().getSelectedItem() + "?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    videoClub.eliminarGenero(listView.getSelectionModel().getSelectedItem());
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
     * @param genero El género de las películas a mostrar.
     */
    private void verPeliculas(String genero) {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Ver Detalle Película");

        titleLabel = new Label("Películas");
        titleLabel.getStyleClass().add("opt-title");
        textLabel = new Label("Seleccione una película para ver detalles");
        textLabel.getStyleClass().add("disclaimer");

        ObservableList<String> peliculas = FXCollections.observableArrayList();

        if (genero.isEmpty()) {
            peliculas = FXCollections.observableArrayList(
                    videoClub.obtenerListaPeliculas()
            );
        }
        else{
            if (videoClub.obtenerListaPeliculas(genero) == null || videoClub.obtenerListaPeliculas(genero).isEmpty()) {
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
                        videoClub.obtenerListaPeliculas(genero).lines().toList()
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
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem()));
                contentPane.getChildren().add(vbox);
            }
        });
        contentPane.getChildren().add(vbox);
    }

    /**
     * Muestra un formulario para agregar una película.
     *
     *
     * @param title El título de la película.
     * @param promptTxt El texto de solicitud para el campo de entrada.
     */
    private void agregarPelicula(String title, String promptTxt) {
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

        String[] currentStep = {"Nombre de la Película"};
        String[] nombre = {""};
        String[] genero = {""};
        String[] precio = {""};

        okButton.setOnAction(e -> {
            // Obtener el valor actual del campo de texto
            String valorActual = formField1.getText();
            System.out.println("Valor ingresado: " + valorActual);

            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Nombre de la Película":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Nombre no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formField1.setPromptText("Genero");
                        nombre[0] = formField1.getText();
                        if (videoClub.existePelicula(nombre[0])) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Película ya existe");
                            alert.setContentText("La película ya existe en la base de datos.");
                            alert.showAndWait();
                            break;
                        }
                        currentStep[0] = "Genero";
                        break;
                    }
                case "Genero":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Genero no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        formField1.setPromptText("Precio Semanal");
                        genero[0] = formField1.getText();
                        currentStep[0] = "Precio Semanal";
                        break;
                    }
                case "Precio Semanal":
                    if (isFieldEmpty(formField1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Campo vacío");
                        alert.setHeaderText(null);
                        alert.setContentText("El campo Precio no puede estar vacío.");
                        alert.showAndWait();
                        break;
                    }
                    else {
                        precio[0] = formField1.getText();
                        if (precio[0].matches("[0-9]+")) {
                            videoClub.agregarPelicula(nombre[0], genero[0], Integer.parseInt(precio[0]));
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Éxito");
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
                            alert.setContentText("El precio debe ser un número entero.");
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
     * @param titulo El título de la película.
     * @return Un StackPane que contiene los detalles de la película.
     */
    private StackPane verDetallePelicula(String titulo) {
        contentPane.getChildren().clear();  // Limpiar el contentPane

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
        vbox.getChildren().addAll(titleLabel, datos);

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
                videoClub.obtenerListaPeliculas()
        );

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
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem()));
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
     * @param titulo El título actual de la película.
     * @param subtitulo El subtítulo para el formulario.
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
            // Obtener el valor actual del campo de texto
            String valorActual = formField1.getText();
            System.out.println("Valor ingresado: " + valorActual);


            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Nuevo Titulo":
                    titleLabel2.setText("Nuevo Genero");
                    formField1.setPromptText(videoClub.obtenerGeneroPelicula(titulo).toUpperCase());
                    if (!isFieldEmpty(formField1)) {
                        nombre[0] = formField1.getText().toUpperCase();
                    }
                    currentStep[0] = "Nuevo Genero";
                    break;
                case "Nuevo Genero":
                    titleLabel2.setText("Nuevo Precio Semanal");
                    formField1.setPromptText(videoClub.obtenerPrecioPelicula(titulo));
                    if (!isFieldEmpty(formField1)) {
                        genero[0] = formField1.getText().toUpperCase();
                    }
                    currentStep[0] = "Nuevo Precio Semanal";
                    break;
                case "Nuevo Precio Semanal":
                    System.out.println("Formulario completado.");
                    if (!isFieldEmpty(formField1)) {
                        precio[0] = formField1.getText();
                    }
                    videoClub.editarPelicula(titulo, nombre[0], genero[0], Integer.parseInt(precio[0]));
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
    private void EliminarPelicula(){
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Eliminar Película");
        ObservableList<String> peliculas = FXCollections.observableArrayList(
                videoClub.obtenerListaPeliculas()
        );
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
                vbox.getChildren().add(verDetallePelicula(listView.getSelectionModel().getSelectedItem()));
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
     * Verifica si un campo de texto está vacío.
     *
     * @param textField El campo de texto a verificar.
     * @return true si el campo de texto está vacío, false en caso contrario.
     */
    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }
}

