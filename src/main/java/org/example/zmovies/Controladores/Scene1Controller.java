package org.example.zmovies.Controladores;

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
import java.util.List;
import java.util.Objects;

/**
 * El controlador de la escena de atención al cliente del video club. Permite rentar, devolver y recomendar películas,
 * ver las películas no devueltas y la película más rentada de cada género.
 */
public class Scene1Controller {
    private VideoClub videoClub;
    @FXML
    private Pane contentPane;
    private Label titleLabel;
    private Label textLabel1;
    private Label textLabel2;
    private Label textLabel3;
    private TextField formField1;
    private TextField formField2;
    private TextField formField3;
    private ListView<String> listView;
    private Button okButton;
    private Button cancelButton;
    private HBox horizontalLayout;
    private VBox verticalLayout;

    /**
     * Establece la instancia de VideoClub que se utilizará en este controlador.
     *
     * @param videoClub la instancia de VideoClub a asignar
     */
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    /**
     * Maneja el evento de hacer click en el botón "Rentar".
     * Actualiza el panel con los campos necesarios para rentar una película.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onRentarClick(ActionEvent event) {
        mostrarRutForm("Rentar Película");

        formField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String input = formField1.getText();
                String rutFormateado = formatoRut(input);
                formField1.setText(rutFormateado);
            }
        });

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo RUT no puede estar vacío.");
                alert.showAndWait();
            } else {
                handleClienteRenta();
            }
        });

        cancelButton.setOnAction(e -> mostrarDefault());
    }

    /**
     * Maneja el evento de hacer click en el botón "Devolver".
     * Actualiza el panel con los campos necesarios para devolver una película.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onDevolverClick(ActionEvent event) {
        mostrarRutForm("Devolver Película");

        formField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String input = formField1.getText();
                String rutFormateado = formatoRut(input);
                formField1.setText(rutFormateado);
            }
        });

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo RUT no puede estar vacío.");
                alert.showAndWait();
            } else {
                handleClienteDevolucion();
            }
        });

        cancelButton.setOnAction(e -> mostrarDefault());
    }

    /**
     * Maneja el evento de hacer click en el botón "Recomendar".
     * Actualiza el panel con los campos necesarios para recomendar una película.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onRecomendarClick(ActionEvent event) {
        mostrarRutForm("Recomendar Película");

        formField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String input = formField1.getText();
                String rutFormateado = formatoRut(input);
                formField1.setText(rutFormateado);
            }
        });

        okButton.setOnAction(event1 -> {
            if (isFieldEmpty(formField1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("El campo RUT no puede estar vacío.");
                alert.showAndWait();
            } else {
                handleRecomendar();
            }
        });

        cancelButton.setOnAction(event1 -> mostrarDefault());
    }

    /**
     * Maneja el evento de hacer click en el botón "No Devueltas".
     * Actualiza el panel con las rentas no devueltas.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onNoDevueltasClick(ActionEvent event) {
        handleNoDevueltas();
    }

    /**
     * Maneja el evento de hacer click en el botón "Pelicula Mas Rentada".
     * Actualiza el panel con la lista de géneros y el botón para ver la película más rentada de cada género.
     *
     * @param event el evento de acción generado por el click
     */
    @FXML
    private void onPeliculaMasRentadaClick(ActionEvent event) {
        handleMasRentada();
    }

    /**
     * Maneja el evento de hacer click en el botón "Volver".
     * Cambia la escena a la escena del menú principal.
     *
     * @param event el evento de acción generado por el click
     * @throws IOException si hay un error al cambiar la escena
     */
    @FXML
    private void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene0-view.fxml");
    }

    /**
     * Verifica si el cliente existe en el sistema. Si existe, se procede a rentar una película. Si no existe, se procede a agregar los datos del cliente.
     */
    private void handleClienteRenta() {
        String rut = formatoRut(formField1.getText());

        boolean existeCliente = videoClub.existeCliente(rut);
        if (existeCliente) {
            handlePeliculaRenta(rut);
        } else {
            handleNuevoCliente(rut);
        }
    }

    /**
     * Actualiza el panel con los campos necesarios para agregar un nuevo cliente al sistema. Se agrega el cliente al sistema y se procede a rentar una película.
     *
     * @param rut el RUT del cliente
     */
    private void handleNuevoCliente(String rut) {
        String title = "El cliente no existe. Se agregarán los datos del cliente.";
        String[] labels = {"Nombre cliente:", "ej.: Juan Pérez", "Teléfono:", "ej.: 958646803", "Correo:", "ej.: mail@mail.com"};
        mostrarFormulario(title, labels);

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1) || isFieldEmpty(formField2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Los campos no pueden estar vacíos.");
                alert.showAndWait();
            } else {
                videoClub.agregarCliente(rut, formField1.getText(), formField3.getText(), formField2.getText());
                handlePeliculaRenta(rut);
            }
        });

        cancelButton.setOnAction(e -> mostrarDefault() );
    }

    /**
     * Actualiza el panel con los campos necesarios para rentar una película al cliente. Se muestra el formulario para rentar una película al cliente y verifica que se llenen los campos necesarios.
     *
     * @param rut el RUT del cliente
     */
    private void handlePeliculaRenta(String rut) {
        String title = "Cliente: " + videoClub.obtenerNombreCliente(rut);
        String[] labels = {"Nombre pelicula:", "ej.: El Padrino", "Semanas renta:", "ej.: 2"};
        mostrarFormulario(title, labels);

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1) || isFieldEmpty(formField2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Los campos no pueden estar vacíos.");
                alert.showAndWait();
            } else {
                handleRenta(rut);
            }
        } );

        cancelButton.setOnAction(e -> mostrarDefault() );
    }

    /**
     * Maneja la renta de una película para un cliente.
     * Verifica si la película existe en el sistema y procede con la renta.
     * Muestra mensajes de éxito o error según corresponda.
     *
     * @param rut el RUT del cliente
     */
    private void handleRenta(String rut) {
        String movieName = formField1.getText().toUpperCase();
        int weeks = Integer.parseInt(formField2.getText());
        boolean existePelicula = videoClub.existePelicula(movieName);

        if (isFieldEmpty(formField1) || isFieldEmpty(formField2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("Los campos no pueden estar vacíos.");
            alert.showAndWait();
        } else {
            if (existePelicula) {
                videoClub.rentarPelicula(rut, movieName, weeks);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Operación exitosa");
                successAlert.setHeaderText(null);
                successAlert.setContentText("La película '" + movieName + "' fue rentada con éxito.");
                successAlert.showAndWait();
                mostrarDefault();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Operación fallida");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("La película " + movieName + " no se encuentra en el sistema.");
                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Maneja la devolución de una película por parte de un cliente.
     * Verifica si el cliente existe en el sistema y procede con la devolución.
     * Muestra mensajes de éxito o error según corresponda.
     */
    private void handleClienteDevolucion() {
        String rut = formatoRut(formField1.getText());
        boolean existeCliente = videoClub.existeCliente(rut);
        if (existeCliente) {
            handleDevolucion(rut);
        } else {
            mostrarDefault();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Operación fallida");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("El cliente no existe en el sistema.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Maneja la devolución de una película específica para un cliente.
     * Actualiza el panel con la lista de películas no devueltas y permite seleccionar una para devolver.
     * Muestra mensajes de confirmación y éxito según corresponda.
     *
     * @param rut el RUT del cliente
     */
    private void handleDevolucion(String rut) {
        String pendientes = videoClub.obtenerListaRentasPendientes(rut);
        if (pendientes == null) {
            mostrarDefault();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("El cliente no tiene películas pendientes a devolver.");
            alert.showAndWait();
        } else {
            String title = "Cliente: " + videoClub.obtenerNombreCliente(rut);
            mostrarNoDevueltas(title, "Seleccione la película a devolver:", pendientes);

            handleDoubleClicked(listView, okButton);

            okButton.setOnAction(e -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Operación fallida");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe seleccionar una película para devolver.");
                    alert.showAndWait();
                } else {
                    String selectedId = selectedItem.substring(0, 10).replaceAll("\\D", "").trim();
                    int id = Integer.parseInt(selectedId);
                    String movieName = videoClub.obtenerPeliculaRenta(id);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar devolución");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro de devolver la película '" + movieName + "'?");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        videoClub.devolverPelicula(id);
                        mostrarDefault();
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Operación exitosa");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La película '" + movieName + "' fue devuelta con éxito.");
                        successAlert.showAndWait();
                    }
                }
            });

            cancelButton.setOnAction(e -> mostrarDefault() );
        }
    }

    /**
     * Maneja la recomendación de una película para un cliente.
     * Verifica si el cliente existe en el sistema y muestra la película recomendada.
     * Muestra mensajes de éxito o error según corresponda.
     */
    private void handleRecomendar() {
        String rut = formatoRut(formField1.getText());
        String recomendacion = videoClub.recomendarPelicula(rut);
        boolean existeCliente = videoClub.existeCliente(rut);
        Alert alert;
        if (existeCliente && recomendacion != null) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recomendación");
            alert.setHeaderText(null);
            alert.setContentText("Se recomienda la película '" + recomendacion + "' de acuerdo a las rentas previas del cliente.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Operación fallida");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo recomendar una película para el cliente.");
            alert.showAndWait();
        }
        mostrarDefault();
    }

    /**
     * Maneja la visualización de las rentas no devueltas.
     * Actualiza el panel con la lista de rentas no devueltas y permite seleccionar una renta para ver sus detalles.
     * Muestra mensajes de error si no se selecciona ninguna renta.
     */
    private void handleNoDevueltas() {
        String pendientes = videoClub.obtenerListaRentasPendientes();
        mostrarNoDevueltas(null, "Películas no devueltas:", pendientes);
        
        handleDoubleClicked(listView, okButton);
        
        okButton.setOnAction(e -> {
            // Obtiene el ítem seleccionado de la lista
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Operación fallida");
                alert.setHeaderText(null);
                alert.setContentText("Debe seleccionar una renta para ver sus detalles.");
                alert.showAndWait();
            } else {
                // Obtiene el ID de la renta seleccionada
                String selectedId = selectedItem.substring(0, 10).replaceAll("\\D", "").trim();
                int id = Integer.parseInt(selectedId);
                // Obtiene los detalles de la renta seleccionada
                String[] items = videoClub.detallesRenta(id).split(" - ");
                Label title = new Label("Detalles de renta " + items[0]);
                Label rut = new Label(items[1]);
                Label movie = new Label(items[2]);
                Label cost = new Label(items[3]);
                Label date = new Label(items[4]);
                Label date2 = new Label(items[5]);

                Label[] labels = {title, rut, movie, cost, date, date2};

                // Actualiza el panel con los detalles de la renta seleccionada
                mostrarDetalleRenta(labels);
                
                okButton.setOnAction(e1 -> handleNoDevueltas());
            }
        });
    }

    /**
     * Maneja la visualización de la película más rentada por género.
     * Actualiza el panel con la lista de géneros y el botón para ver la película más rentada de cada género.
     * Al seleccionar un género, se muestra la película más rentada del género seleccionado.
     */
    private void handleMasRentada() {
        Label genreLabel = new Label("Género: NO SELECCIONADO");
        Label titleLabel = new Label("Título: N/A");
        Label costLabel = new Label("Costo Semanal: N/A");
        Label[] labels = {genreLabel, titleLabel, costLabel};
        mostrarMasRentada(labels);

        handleDoubleClicked(listView, okButton);

        okButton.setOnAction(e -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Operación fallida");
                alert.setHeaderText(null);
                alert.setContentText("Debe seleccionar un género para continuar.");
                alert.showAndWait();
            } else {
                String pelicula = videoClub.obtenerMasRentadaGenero(selectedItem);
                if (pelicula == null) {
                    pelicula = "N/A - N/A";
                }
                String movieDetails = selectedItem + " - " + pelicula;
                String[] items = movieDetails.split(" - ");
                labels[0].setText("Género: " + items[0]);
                labels[1].setText("Título: " + items[1]);
                if (items[2].equals("N/A")) {
                    labels[2].setText("Costo Semanal: " + items[2]);
                } else {
                    labels[2].setText("Costo Semanal: " + items[2] + "$");
                }
            }
        });
    }

    /**
     * Restablece el panel de contenido a su estado predeterminado.
     * Limpia el contenido actual del panel y muestra el icono de la aplicación en el centro del panel.
     */
    private void mostrarDefault() {
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
     * Muestra un formulario para ingresar el RUT del cliente en el panel de contenido.
     * Limpia el contenido actual del panel y lo actualiza con el formulario de RUT.
     *
     * @param title el título a mostrar en el panel
     */
    private void mostrarRutForm(String title) {
        // Limpia el contenido actual del panel
        contentPane.getChildren().clear();

        // Configura el título del formulario
        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");

        // Configura la etiqueta y el campo de texto para el RUT
        Label formTitle = new Label("RUT del cliente:");
        formTitle.getStyleClass().add("opt-title");
        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText("ej.: 12.345.678-9");

        // Configura los botones OK y Cancelar
        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");
        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Asigna el evento de tecla Enter al campo de texto del RUT, para que al presionar Enter se presione el botón OK
        handleEnterKey(formField1, okButton);

        // Configura el posicionamiento de los botones OK y Cancelar
        horizontalLayout = new HBox(10);
        horizontalLayout.getStyleClass().add("confirm-layout");
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.getChildren().addAll(cancelButton, spacer, okButton);

        // Configura el diseño del formulario
        verticalLayout = new VBox(10);
        verticalLayout.getStyleClass().add("form-layout");
        verticalLayout.getChildren().addAll(titleLabel, formTitle, formField1, horizontalLayout);

        // Ajusta el tamaño del diseño del formulario
        verticalLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(verticalLayout.getPrefWidth());
        horizontalLayout.setPrefWidth(verticalLayout.getPrefWidth());
        spacer.setMinWidth(horizontalLayout.getMinWidth() * 0.6);

        // Añade el formulario al panel de contenido
        contentPane.getChildren().addAll(verticalLayout);
    }

    /**
     * Muestra un formulario con los campos especificados en el panel de contenido.
     * Limpia el contenido actual del panel y lo actualiza con el formulario.
     *
     * @param title el título a mostrar en el panel
     * @param labels un arreglo de etiquetas que contienen los textos de los campos del formulario
     */
    private void mostrarFormulario(String title, String[] labels) {
        // Limpia el contenido actual del panel
        contentPane.getChildren().clear();

        // Crea y configura el diseño del formulario
        verticalLayout = new VBox(10);
        verticalLayout.getStyleClass().add("form-layout");

        // Configura el título del formulario
        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("opt-title");

        // Configura la primera etiqueta y campo de texto del formulario
        textLabel1 = new Label(labels[0]);
        textLabel1.getStyleClass().add("form-title");
        formField1 = new TextField();
        formField1.setPromptText(labels[1]);
        formField1.getStyleClass().add("form-field");

        // Configura la segunda etiqueta y campo de texto del formulario
        textLabel2 = new Label(labels[2]);
        textLabel2.getStyleClass().add("form-title");
        formField2 = new TextField();
        formField2.setPromptText(labels[3]);
        formField2.getStyleClass().add("form-field");

        // Añade los componentes al diseño del formulario
        verticalLayout.getChildren().addAll(titleLabel, textLabel1, formField1, textLabel2, formField2);

        // Configura los botones OK y Cancelar
        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");
        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Configura el tercer campo de texto si existe un tercer par de etiquetas
        if (labels.length > 4) {
            textLabel3 = new Label(labels[4]);
            textLabel3.getStyleClass().add("form-title");
            formField3 = new TextField();
            formField3.setPromptText(labels[5]);
            formField3.getStyleClass().add("form-field");
            verticalLayout.getChildren().addAll(textLabel3, formField3);

            // Asigna el evento de tecla Enter a los campos de texto
            handleEnterKey(formField1, formField2);
            handleEnterKey(formField2, formField3);
            handleEnterKey(formField3, okButton);
        } else {
            handleEnterKey(formField1, formField2);
            handleEnterKey(formField2, okButton);
        }

        // Configura el posicionamiento de los botones OK y Cancelar
        HBox horizontalLayout = new HBox(10);
        horizontalLayout.getStyleClass().add("confirm-layout");
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.getChildren().addAll(cancelButton, spacer, okButton);

        // Añade los botones al diseño del formulario
        verticalLayout.getChildren().add(horizontalLayout);
        verticalLayout.setAlignment(Pos.CENTER);

        // Ajusta el tamaño del diseño del formulario
        verticalLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(verticalLayout.getPrefWidth());
        textLabel1.setPrefWidth(verticalLayout.getPrefWidth());
        textLabel2.setPrefWidth(verticalLayout.getPrefWidth());
        horizontalLayout.setPrefWidth(verticalLayout.getPrefWidth());
        spacer.setMinWidth(horizontalLayout.getMinWidth() * 0.6);

        // Añade el diseño del formulario al panel de contenido
        contentPane.getChildren().addAll(verticalLayout);
    }

    /**
     * Muestra las películas no devueltas en el panel de contenido.
     * Limpia el contenido actual del panel y lo actualiza con la lista de películas no devueltas.
     *
     * @param cliente el nombre del cliente (puede ser null)
     * @param title el título a mostrar en el panel
     * @param data los datos de las películas no devueltas
     */
    private void mostrarNoDevueltas(String cliente, String title, String data) {
        // Limpia el contenido actual del panel
        contentPane.getChildren().clear();
        listView = new ListView<>();
        listView.getStyleClass().add("waiting-movies");
        List<String> pendientesList;

        // Crea y configura el diseño del formulario
        verticalLayout = new VBox(10);
        verticalLayout.getStyleClass().add("form-layout");
        verticalLayout.setAlignment(Pos.CENTER);

        // Configura el botón OK
        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        if (cliente != null) {
            if (data != null) {
                // Divide los datos en una lista de pendientes
                pendientesList = data.lines().toList();
                String[] rentDetails;
                for (String pendiente : pendientesList) {
                    rentDetails = pendiente.split(" - ");
                    String formattedItem = String.format("%10s %-80s\t%s", rentDetails[0], rentDetails[2], rentDetails[5]);
                    listView.getItems().add(formattedItem);
                }
            }
            // Configura las etiquetas del título y del formulario
            titleLabel = new Label(cliente);
            textLabel1 = new Label(title);
            textLabel1.getStyleClass().add("form-title");

            // Configura el espaciador y el botón de cancelar
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            cancelButton = new Button("Cancelar");
            cancelButton.getStyleClass().add("cancel-button");

            // Configura el diseño de confirmación
            horizontalLayout = new HBox(10);
            horizontalLayout.getStyleClass().add("confirm-layout");
            horizontalLayout.setAlignment(Pos.CENTER);
            horizontalLayout.getChildren().addAll(cancelButton, spacer, okButton);
            spacer.setMinWidth(contentPane.getPrefWidth() * 0.6);

            // Añade los componentes al diseño del formulario
            verticalLayout.getChildren().addAll(titleLabel, textLabel1, listView, horizontalLayout);
        } else {
            if (data != null) {
                // Divide los datos en una lista de pendientes
                pendientesList = data.lines().toList();
                String[] rentDetails;
                for (String pendiente : pendientesList) {
                    rentDetails = pendiente.split(" - ");
                    String formattedItem = String.format("%10s %-24s\t%s", rentDetails[0], rentDetails[1], rentDetails[2]);
                    listView.getItems().add(formattedItem);
                }
            }
            // Configura la etiqueta del título
            titleLabel = new Label(title);
            verticalLayout.getChildren().addAll(titleLabel, listView, okButton);
        }
        titleLabel.getStyleClass().add("opt-title");

        // Ajusta el tamaño del diseño del formulario
        verticalLayout.setPrefWidth(contentPane.getPrefWidth());
        verticalLayout.setPrefHeight(contentPane.getPrefHeight());
        titleLabel.setPrefWidth(verticalLayout.getPrefWidth());
        listView.setPrefWidth(verticalLayout.getPrefWidth());
        listView.setPrefHeight(verticalLayout.getPrefHeight() * 0.6);

        // Añade el diseño del formulario al panel de contenido
        contentPane.getChildren().add(verticalLayout);
    }

    /**
     * Muestra la película más rentada por género en el panel de contenido.
     * Limpia el contenido actual del panel y lo actualiza con la lista de géneros y los detalles de la película más rentada.
     *
     * @param detailLabels un arreglo de etiquetas que contienen los detalles de la película más rentada
     */
    private void mostrarMasRentada(Label[] detailLabels) {
        // Limpia el contenido actual del panel
        contentPane.getChildren().clear();

        // Configura el título del panel
        titleLabel = new Label("Película más rentada para género: ");
        titleLabel.getStyleClass().add("opt-title");
        titleLabel.paddingProperty().setValue(new javafx.geometry.Insets(20, 0, 20, 0));

        // Obtiene los géneros del video club
        String data = videoClub.obtenerNombresGeneros();
        if (data != null) {
            // Separa los géneros en una lista
            List<String> items = data.lines().toList();
            listView = new ListView<>();
            for (String item : items) {
                listView.getItems().add(item);
            }
        }

        // Configura el botón OK
        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        // Configura el diseño de la lista de géneros
        VBox genreLayout = new VBox(10);
        genreLayout.getStyleClass().add("genre-list-layout");
        genreLayout.setAlignment(Pos.CENTER);
        genreLayout.getChildren().addAll(listView, okButton);

        // Configura el diseño de los detalles de la película
        VBox movieDetails = new VBox(10);
        movieDetails.getStyleClass().add("movie-details-layout");
        movieDetails.setAlignment(Pos.CENTER_LEFT);
        for (Label detail : detailLabels) {
            detail.getStyleClass().add("detail");
            movieDetails.getChildren().add(detail);
        }

        // Configura el espaciador
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Configura el posicionamiento de los géneros y los detalles de la película
        horizontalLayout = new HBox(10);
        horizontalLayout.getStyleClass().add("best-seller-layout");
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.getChildren().addAll(genreLayout, movieDetails);

        // Configura el diseño del formulario
        verticalLayout = new VBox(10);
        verticalLayout.getStyleClass().add("form-layout");
        verticalLayout.setAlignment(Pos.CENTER);
        verticalLayout.paddingProperty().setValue(new javafx.geometry.Insets(20, 20, 20, 20));
        verticalLayout.getChildren().addAll(titleLabel, horizontalLayout);

        // Ajusta el tamaño del diseño del formulario
        verticalLayout.setPrefWidth(contentPane.getPrefWidth());
        horizontalLayout.setPrefWidth(verticalLayout.getPrefWidth());
        genreLayout.setPrefWidth(horizontalLayout.getPrefWidth() * 0.3);
        movieDetails.setPrefSize(horizontalLayout.getPrefWidth() * 0.4, horizontalLayout.getPrefHeight() * 0.4);
        movieDetails.setMaxHeight(horizontalLayout.getPrefHeight() * 0.4);

        // Añade el diseño del formulario al panel de contenido
        contentPane.getChildren().add(verticalLayout);
    }

    /**
     * Muestra los detalles de una renta en el panel de contenido.
     * Limpia el contenido actual del panel y lo actualiza con los detalles de la renta.
     *
     * @param labels un arreglo de etiquetas que contienen los detalles de la renta
     */
    private void mostrarDetalleRenta(Label[] labels) {
        // Limpia el contenido actual del panel
        contentPane.getChildren().clear();

        // Crea y configura el diseño del formulario
        verticalLayout = new VBox(10);
        verticalLayout.getStyleClass().add("form-layout");
        verticalLayout.setAlignment(Pos.CENTER);

        // Configura las etiquetas de detalles
        labels[0].getStyleClass().add("opt-title");
        labels[1].getStyleClass().add("detail");
        labels[2].getStyleClass().add("detail");
        labels[3].getStyleClass().add("detail");
        labels[4].getStyleClass().add("detail");
        labels[5].getStyleClass().add("detail");

        // Crea y configura el botón de volver
        okButton = new Button("Volver");
        okButton.getStyleClass().add("back-button");

        // Añade las etiquetas al diseño del formulario
        verticalLayout.getChildren().addAll(labels);
        verticalLayout.setAlignment(Pos.CENTER);

        // Crea y configura el contenedor principal
        VBox container = new VBox(10);
        container.getStyleClass().add("form-layout");
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(verticalLayout, okButton);

        // Crea y configura un panel para centrar el contenedor principal
        StackPane stackPane = new StackPane(container);
        stackPane.setPrefSize(contentPane.getPrefWidth(), contentPane.getPrefHeight());

        // Añade el panel de pila al panel de contenido
        contentPane.getChildren().add(stackPane);
    }

    /**
     * Formatea un RUT (Rol Único Tributario) a un formato estándar.
     *
     * @param input el RUT a formatear
     * @return el RUT formateado
     */
    private String formatoRut(String input) {
        input = input.replaceAll("\\D", "");

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
     * @param textField el campo de texto a verificar
     * @return true si el campo de texto está vacío, false en caso contrario
     */
    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    /**
     * Asigna un evento al TextField para que al presionar la tecla Enter se cambie el foco al Control recibido.
     *
     * @param currentField el TextField actual
     * @param nextControl el Control al que se cambiará el foco
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
     * Asigna un evento al ListView para que al hacer doble click en un item se ejecute el evento del botón recibido.
     *
     * @param listView el ListView en el que se hará doble click
     * @param submitButton el Button que se ejecutará al hacer doble click
     */
    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }
}
