/**
 * Módulo principal de la aplicación zMovies
 * <p>
 * Este módulo contiene la configuración de los módulos que se requieren para la ejecución de la aplicación ZMovies.
 * <p>
 * Se requieren los siguientes módulos:
 * <ul>
 *     <li>org.apache.poi.ooxml</li>
 *     <li>org.apache.poi.poi</li>
 *     <li>javafx.controls</li>
 *     <li>javafx.fxml</li>
 *     <li>java.desktop</li>
 *     <li>java.sql</li>
 * </ul>
 * <p>
 * Se abren los paquetes:
 * <ul>
 *     <li>org.example.zmovies</li>
 *     <li>org.example.zmovies.Controladores</li>
 * </ul>
 * <p>
 * Se exportan los paquetes:
 * <ul>
 *     <li>org.example.zmovies</li>
 *     <li>org.example.zmovies.Modelos</li>
 *     <li>org.example.zmovies.Vistas</li>
 *     <li>org.example.zmovies.Controladores</li>
 *     <li>org.example.zmovies.Exceptions</li>
 * </ul>
 */
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
    exports org.example.zmovies.Vistas;
    exports org.example.zmovies.Controladores;
    exports org.example.zmovies.Exceptions;
}