package org.example.zmovies.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.zmovies.Modelos.VideoClub;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Scene1Controller {
    private VideoClub videoClub;
    @FXML
    private Pane contentPane;
    private Label titleLabel;
    private Label formLabel1;
    private Label formLabel2;
    private TextField formField1;
    private TextField formField2;
    private ListView<String> listView;
    private Button okButton;
    private Button cancelButton;
    private HBox confirmLayout;
    private VBox formLayout;

    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    @FXML
    private void onRentarClick(ActionEvent event) {
        updatePane("Rentar Película", "ej.: 12.345.678-9");

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
        } );

        cancelButton.setOnAction(e -> defaultPane() );
    }

    @FXML
    private void onDevolverClick(ActionEvent event) {
        updatePane("Devolver Película", "ej.: 12.345.678-9");

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
                handleClienteDev();
            }
        } );

        cancelButton.setOnAction(e -> defaultPane() );
    }

    @FXML
    private void onRecomendarClick(ActionEvent event) {
        updatePane("Recomendar Película", "ej.: 12.345.678-9");

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

        cancelButton.setOnAction(event1 -> defaultPane());
    }

    @FXML
    private void onNoDevueltasClick(ActionEvent event) {
        handleNoDevueltas();
    }

    @FXML
    private void onPeliculaMasRentadaClick(ActionEvent event) {
        handleMasRentada();
    }

    @FXML
    private void onVolverClick(ActionEvent event) throws IOException {
        SceneManager.switchScene("/fxml/scene0-view.fxml");
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

    private void updatePane(String title, String lbl1, String prompTxt1, String lbl2, String prompTxt2) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("opt-title");

        formLabel1 = new Label(lbl1);
        formLabel1.getStyleClass().add("form-title");
        formField1 = new TextField();
        formField1.setPromptText(prompTxt1);
        formField1.getStyleClass().add("form-field");

        formLabel2 = new Label(lbl2);
        formLabel2.getStyleClass().add("form-title");
        formField2 = new TextField();
        formField2.setPromptText(prompTxt2);
        formField2.getStyleClass().add("form-field");

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");
        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        handleEnterKey(formField1, formField2);
        handleEnterKey(formField2, okButton);

        HBox confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("confirm-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);

        VBox formLayout = new VBox(10);
        formLayout.getStyleClass().add("form-layout");
        formLayout.getChildren().addAll(titleLabel, formLabel1, formField1, formLabel2, formField2, confirmLayout);

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(formLayout.getPrefWidth());
        formLabel1.setPrefWidth(formLayout.getPrefWidth());
        formLabel2.setPrefWidth(formLayout.getPrefWidth());
        confirmLayout.setPrefWidth(formLayout.getPrefWidth());
        spacer.setMinWidth(confirmLayout.getMinWidth() * 0.6);

        contentPane.getChildren().addAll(formLayout);
    }

    private void updatePane(String title, String promptTxt) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");

        Label formTitle = new Label("RUT del cliente:");
        formTitle.getStyleClass().add("opt-title");
        formField1 = new TextField();
        formField1.getStyleClass().add("form-field");
        formField1.setPromptText(promptTxt);

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");
        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("cancel-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        handleEnterKey(formField1, okButton);

        confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("confirm-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);

        formLayout = new VBox(10);
        formLayout.getStyleClass().add("form-layout");
        formLayout.getChildren().addAll(titleLabel, formTitle, formField1, confirmLayout);

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        titleLabel.setPrefWidth(formLayout.getPrefWidth());
        confirmLayout.setPrefWidth(formLayout.getPrefWidth());
        spacer.setMinWidth(confirmLayout.getMinWidth() * 0.6);

        contentPane.getChildren().addAll(formLayout);
    }

    private void updatePane(String cliente, String title, String data) {
        contentPane.getChildren().clear();
        listView = new ListView<>();
        List<String> pendientesList;

        if (data != null) {
            pendientesList = data.lines().toList();
            for (String pendiente : pendientesList) {
                listView.getItems().add(pendiente);
            }

        }
        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        formLayout = new VBox(10);
        formLayout.getStyleClass().add("form-layout");
        formLayout.setAlignment(Pos.CENTER);
        if (cliente != null) {
            titleLabel = new Label(cliente);
            formLabel1 = new Label(title);
            formLabel1.getStyleClass().add("form-title");

            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            cancelButton = new Button("Cancelar");
            cancelButton.getStyleClass().add("cancel-button");

            confirmLayout = new HBox(10);
            confirmLayout.getStyleClass().add("confirm-layout");
            confirmLayout.setAlignment(Pos.CENTER);
            confirmLayout.getChildren().addAll(cancelButton, spacer, okButton);
            spacer.setMinWidth(contentPane.getPrefWidth() * 0.6);

            formLayout.getChildren().addAll(titleLabel, formLabel1, listView, confirmLayout);
        } else {
            titleLabel = new Label(title);
            formLayout.getChildren().addAll(titleLabel, listView, okButton);
        }
        titleLabel.getStyleClass().add("opt-title");

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        formLayout.setPrefHeight(contentPane.getPrefHeight());
        titleLabel.setPrefWidth(formLayout.getPrefWidth());
        listView.setPrefWidth(formLayout.getPrefWidth());
        listView.setPrefHeight(formLayout.getPrefHeight() * 0.6);

        contentPane.getChildren().add(formLayout);
    }

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

    private void handleDoubleClicked(ListView<String> listView, Button submitButton) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                submitButton.fire();
            }
        });
    }

    private void handleClienteRenta() {
        String rut = formatoRut(formField1.getText());

        boolean existeCliente = videoClub.existeCliente(rut);
        if (existeCliente) {
            handlePeliculaRenta(rut);
        } else {
            handleNewCliente(rut);
        }
    }

    private void handleNewCliente(String rut) {
        String title = "El cliente no existe. Se agregarán los datos del cliente.";
        updatePane(title, "Nombre cliente:", "ej.: Juan Pérez", "Teléfono:", "ej.: 958646803");

        okButton.setOnAction(e -> {
            if (isFieldEmpty(formField1) || isFieldEmpty(formField2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("Los campos no pueden estar vacíos.");
                alert.showAndWait();
            } else {
                videoClub.agregarCliente(rut, formField1.getText(), "UBUNTU", formField2.getText());
                handlePeliculaRenta(rut);
            }
        });

        cancelButton.setOnAction(e -> defaultPane() );
    }

    private void handlePeliculaRenta(String rut) {
        String title = "Cliente: " + videoClub.obtenerNombreCliente(rut);
        updatePane(title, "Nombre pelicula:", "ej.: El Padrino", "Semanas renta:", "ej.: 2");

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

        cancelButton.setOnAction(e -> defaultPane() );
    }

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
            defaultPane();
            if (existePelicula) {
                videoClub.rentarPelicula(rut, movieName, weeks);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Operación exitosa");
                successAlert.setHeaderText(null);
                successAlert.setContentText("La película '" + movieName + "' fue rentada con éxito.");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Operación fallida");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("La película " + movieName + " no se encuentra en el sistema.");
            }
        }
    }

    private void handleClienteDev() {
        String rut = formatoRut(formField1.getText());
        boolean existeCliente = videoClub.existeCliente(rut);
        if (existeCliente) {
            handleDevolucion(rut);
        } else {
            defaultPane();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Operación fallida");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("El cliente no existe en el sistema.");
            errorAlert.showAndWait();
        }
    }

    private void handleDevolucion(String rut) {
        String pendientes = videoClub.obtenerListaRentasPendientes(rut);
        if (pendientes == null) {
            defaultPane();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("El cliente no tiene películas pendientes a devolver.");
            alert.showAndWait();
        } else {
            String title = "Cliente: " + videoClub.obtenerNombreCliente(rut);
            updatePane(title, "Seleccione la película a devolver:", pendientes);

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
                    String[] items = selectedItem.split(", ");
                    String movieName = items[1].substring(10);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar devolución");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro de devolver la película '" + movieName + "'?");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        videoClub.devolverPelicula(Integer.parseInt(items[0].substring(4)));
                        defaultPane();
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Operación exitosa");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La película '" + movieName + "' fue devuelta con éxito.");
                        successAlert.showAndWait();
                    }
                }
            });

            cancelButton.setOnAction(e -> defaultPane() );
        }
    }

    private void handleRecomendar() {
        String rut = formatoRut(formField1.getText());
        String recomendacion = videoClub.recomendarPelicula(rut);
        boolean existeCliente = videoClub.existeCliente(rut);
        if (existeCliente && recomendacion != null) {
            defaultPane();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recomendación");
            alert.setHeaderText(null);
            alert.setContentText("Se recomienda la película '" + recomendacion + "' de acuerdo a las rentas previas del cliente.");
            alert.showAndWait();
        } else {
            defaultPane();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Operación fallida");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo recomendar una película para el cliente.");
            alert.showAndWait();
        }
    }

    private void handleNoDevueltas() {
        String pendientes = videoClub.obtenerListaRentasPendientes();
        updatePane(null, "Películas no devueltas:", pendientes);

        okButton.setOnAction(e -> defaultPane() );
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    private void updatePane(String title, Label[] detailLabels) {
        contentPane.getChildren().clear();

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("opt-title");
        titleLabel.paddingProperty().setValue(new javafx.geometry.Insets(20, 0, 20, 0));

        String data = videoClub.obtenerListaGeneros();
        if (data != null) {
            List<String> items = data.lines().toList();
            listView = new ListView<>();
            for (String item : items) {
                listView.getItems().add(item);
            }
        }

        okButton = new Button("OK");
        okButton.getStyleClass().add("ok-button");

        VBox genreLayout = new VBox(10);
        genreLayout.getStyleClass().add("genre-list-layout");
        genreLayout.setAlignment(Pos.CENTER);
        genreLayout.getChildren().addAll(listView, okButton);

        VBox movieDetails = new VBox(10);
        movieDetails.getStyleClass().add("movie-details-layout");
        movieDetails.setAlignment(Pos.CENTER_LEFT);
        for (Label detail : detailLabels) {
            detail.getStyleClass().add("detail");
            movieDetails.getChildren().add(detail);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        confirmLayout = new HBox(10);
        confirmLayout.getStyleClass().add("best-seller-layout");
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.getChildren().addAll(genreLayout, movieDetails);

        formLayout = new VBox(10);
        formLayout.getStyleClass().add("form-layout");

        formLayout.setAlignment(Pos.CENTER);
        formLayout.paddingProperty().setValue(new javafx.geometry.Insets(20, 20, 20, 20));
        formLayout.getChildren().addAll(titleLabel, confirmLayout);

        formLayout.setPrefWidth(contentPane.getPrefWidth());
        confirmLayout.setPrefWidth(formLayout.getPrefWidth());
        genreLayout.setPrefWidth(confirmLayout.getPrefWidth() * 0.3);
        movieDetails.setPrefSize(confirmLayout.getPrefWidth() * 0.4, confirmLayout.getPrefHeight() * 0.4);
        movieDetails.setMaxHeight(confirmLayout.getPrefHeight() * 0.4);

        contentPane.getChildren().add(formLayout);
    }

    private void handleMasRentada() {
        Label genreLabel = new Label("Género: NO SELECCIONADO");
        Label titleLabel = new Label("Título: N/A");
        Label costLabel = new Label("Costo Semanal: N/A");
        Label[] labels = {genreLabel, titleLabel, costLabel};
        updatePane("Película más rentada para género: ", labels);

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
                String movieDetails = selectedItem + " - " + videoClub.obtenerMasRentadaGenero(selectedItem);
                String[] items = movieDetails.split(" - ");
                labels[0].setText("Género: " + items[0]);
                labels[1].setText("Título: " + items[1]);
                labels[2].setText("Costo Semanal: " + items[2]);
            }
        } );
    }

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
}
