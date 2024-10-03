module org.example.zmovies {
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens org.example.zmovies to javafx.fxml;
    opens org.example.zmovies.Controladores to javafx.fxml;

    exports org.example.zmovies;
    exports org.example.zmovies.Modelos;
//    exports org.example.zmovies.Vistas;
    exports org.example.zmovies.Controladores;
    exports org.example.zmovies.Exceptions;
}