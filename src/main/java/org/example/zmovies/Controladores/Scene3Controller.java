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
    private BorderPane rootPane;
    private Label centerLabel;
    @FXML
    private Button verDetallePeliculaButton;
    private Button okButton;
    private Button cancelButton;
    @FXML
    private Pane contentPane;
    private Label titleLabel;
    private Label titleLabel2;
    private Label formLabel1;
    private Label formLabel2;
    private Label textLabel;
    private TextField formField1;
    private TextField formField2;
    private HBox confirmLayout;
    private VBox formLayout;
    private boolean isGeneroMode = false;  // Estado inicial, comienza en modo "Películas"

    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    @FXML
    private void onVerPeliculasClick(ActionEvent actionEvent) {
        verPeliculas();
    }

    @FXML
    private void onEditarPelicula(ActionEvent actionEvent) {
        editarPelicula();
    }

    @FXML
    private void onVolverClick(ActionEvent actionEvent) throws IOException {
        SceneManager.switchScene("/fxml/scene2-view.fxml");
    }

    @FXML
    private void onEliminarPelicula(ActionEvent actionEvent) {
        EliminarPelicula();
    }
    @FXML
    private void onReporteClick(ActionEvent actionEvent) {
        // Lógica para generar reporte
    }
    @FXML
    private void onAgregarPeliculaClick(ActionEvent actionEvent) {
        agregarPelicula("Agregar Película", "Nombre de la Película");
    }

    @FXML
    private void onGenerosClick(ActionEvent actionEvent) {
        cambiarOpciones();
    }

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
                System.out.println("Género ingresado: " + genero);
                defaultPane();
            }
        });

        cancelButton.setOnAction(e -> defaultPane());
    }

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

    private void editarGenero() {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Editar Género");
        ObservableList<String> generos = FXCollections.observableArrayList(
                "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción"
        );
        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        titleLabel = new Label("Editar Genero");
        titleLabel.getStyleClass().add("opt-title");
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

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo Nombre no puede estar vacío.");
                alert.showAndWait();
            }
            /*
            else if (){
                String nuevoNombre = formField1.getText();
                System.out.println("Nuevo nombre del género: " + nuevoNombre);
                defaultPane();
            }

            */

            else{
                String nuevoNombre = formField1.getText();
                System.out.println("Nuevo nombre del género: " + nuevoNombre);
                defaultPane();
            }
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());
    }

    private void verGeneros() {
        contentPane.getChildren().clear();
        ObservableList<String> generos = FXCollections.observableArrayList(
                "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción"
        );
        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        titleLabel = new Label("Generos");
        titleLabel.getStyleClass().add("opt-title");
        vbox.getChildren().add(titleLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());
        contentPane.getChildren().add(vbox);
    }

    private void eliminarGenero() {
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Eliminar Género");
        ObservableList<String> generos = FXCollections.observableArrayList(
                "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción"
        );
        ListView<String> listView = new ListView<>(generos);
        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        titleLabel = new Label("Eliminar Género");
        titleLabel.getStyleClass().add("opt-title");
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
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Género");
                alert.setHeaderText("¿Estás seguro de que quieres eliminar el género " + listView.getSelectionModel().getSelectedItem() + "?");
                alert.showAndWait();
            }
        });
    }

    private void restoreActions() {
        // Restaurar las acciones originales de los botones (modo Películas)
        btnAgregarPelicula.setOnAction(this::onAgregarPeliculaClick);
        btnVerPeliculas.setOnAction(this::onVerPeliculasClick);
        btnEditarPelicula.setOnAction(this::onEditarPelicula);
        btnEliminarPelicula.setOnAction(this::onEliminarPelicula);

        // Restaurar el texto original de los botones
        btnAgregarPelicula.setText("Agregar Película");
        btnVerPeliculas.setText("Ver Películas");
        btnEliminarPelicula.setText("Eliminar Película");
        btnEditarPelicula.setText("Editar Película");
        btnCambiarOpciones.setText("Cambiar a Géneros");
    }
        // Método para crear el formulario de "Rentar Película"
    private GridPane createMoviesForm() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Campos del formulario
        Label titleLabel = new Label("Titulo de la Película:");
        TextField titleField = new TextField();
        Label genreLabel = new Label("Genero:");
        TextField genreField = new TextField();
        Label yearLabel = new Label("Año:");
        TextField yearField = new TextField();

        // Añadir los elementos al grid
        grid.add(titleLabel, 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(genreLabel, 0, 2);
        grid.add(genreField, 1, 2);
        grid.add(yearLabel, 0, 3);
        grid.add(yearField, 1, 3);

        return grid;
    }

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

        // Variables para el paso actual
        final String[] currentStep = {"Nombre de la Película"};

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

    private void verPeliculas() {
        contentPane.getChildren().clear();
        ListView<String> listView = new ListView<>();
        verDetallePeliculaButton = new Button("Ver Detalle Película");
        titleLabel = new Label("Películas");
        titleLabel.getStyleClass().add("opt-title");
        ObservableList<String> peliculas = FXCollections.observableArrayList(
                "Matrix Titanic", "El Padrino", "Star Wars", "El Señor de los Anillos"
        );
        listView.setItems(peliculas);
        handleDoubleClicked(listView, verDetallePeliculaButton);

        /*
        // Dividir el string en líneas usando el salto de línea como delimitador
        String[] elementosArray = VideoClub.obtenerListaPeliculas().split("\n");

        // Convertir el array en una ObservableList
        ObservableList<String> elementosList = FXCollections.observableArrayList(elementosArray);

        // Poblar el ListView con la lista de elementos
        listView.setItems(elementosList);

        */

        listView.getStyleClass().add("movie-list");
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-list-layout");
        vbox.getChildren().add(titleLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(listView);
        vbox.getChildren().add(verDetallePeliculaButton);
        vbox.setPrefWidth(contentPane.getPrefWidth());
        vbox.setPrefHeight(contentPane.getPrefHeight());
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

    private StackPane verDetallePelicula(String titulo) {
        contentPane.getChildren().clear();  // Limpiar el contentPane

        // Crear VBox con detalles de la película
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("movie-detail-layout");
        vbox.setAlignment(Pos.CENTER);  // Alinear contenido dentro del VBox

        // Crear los Labels con detalles de la película
        Label titleLabel = new Label("Título: " + titulo);
        titleLabel.getStyleClass().add("opt-title");
        Label directorLabel = new Label("Director: [Director de la película]");
        directorLabel.getStyleClass().add("detail");
        Label anioLabel = new Label("Año: [Año de la película]");
        anioLabel.getStyleClass().add("detail");
        Label generoLabel = new Label("Género: [Género de la película]");
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

    private void editarPelicula(){
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Editar Película");
        titleLabel = new Label("Editar Película");
        titleLabel.getStyleClass().add("menu-title");
        ObservableList<String> peliculas = FXCollections.observableArrayList(
                "Matrix", "Titanic", "El Padrino", "Star Wars", "El Señor de los Anillos"
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
                    editarPeliculaOpciones("Editar Película", "Titulo Actual");
                });
            }
        });
    }

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
        formField1.setPromptText(subtitulo);

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

        // Variables para el paso actual
        final String[] currentStep = {"Nuevo Titulo"};

        okButton.setOnAction(e -> {
            // Obtener el valor actual del campo de texto
            String valorActual = formField1.getText();
            System.out.println("Valor ingresado: " + valorActual);

            // Cambiar el campo según el paso actual
            switch (currentStep[0]) {
                case "Nuevo Titulo":
                    titleLabel2.setText("Nuevo Genero");
                    formField1.setPromptText("Genero actual");
                    currentStep[0] = "Nuevo Genero";
                    break;
                case "Nuevo Genero":
                    titleLabel2.setText("Nuevo Precio Semanal");
                    formField1.setPromptText("Precio Actual");
                    currentStep[0] = "Nuevo Precio Semanal";
                    break;
                case "Nuevo Precio Semanal":
                    System.out.println("Formulario completado.");
                    defaultPane(); // Puedes definir esta función para restaurar la vista por defecto.
                    break;
            }

            // Limpiar el campo de texto para el siguiente paso
            formField1.clear();
        });

        // Acción para cancelar
        cancelButton.setOnAction(e -> defaultPane());
    }

    private void EliminarPelicula(){
        contentPane.getChildren().clear();
        verDetallePeliculaButton = new Button("Eliminar Película");
        ObservableList<String> peliculas = FXCollections.observableArrayList(
                "Matrix", "Titanic", "El Padrino", "Star Wars", "El Señor de los Anillos"
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
                });
            }
        });

    }

    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }

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

