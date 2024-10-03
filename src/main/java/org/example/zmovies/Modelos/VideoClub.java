package org.example.zmovies.Modelos;

import org.example.zmovies.Exceptions.ReportePlanillaException;
import org.example.zmovies.Exceptions.ReporteTextoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoClub {
    private GestorClientes gestorClientes;
    private GestorPeliculas gestorPeliculas;
    private GestorRentas gestorRentas;
    private GestorBaseDatos gestorBaseDatos;

    public VideoClub() {
        gestorClientes = new GestorClientes();
        gestorPeliculas = new GestorPeliculas();
        gestorRentas = new GestorRentas();
        gestorBaseDatos = new GestorBaseDatos("jdbc:sqlite:videoclub.sqlite");
    }

    public void start() throws SQLException {
        gestorBaseDatos.inicializarTablas();
        cargarDatos();
        //datosTest();
    }

    private void datosTest() {
        agregarGenero("Sin Genero");
        agregarGenero("Drama");
        agregarGenero("Romance");

        agregarPelicula("El Padrino", "Drama", 10);
        agregarPelicula("El Padrino II", "Drama", 10);
        agregarPelicula("Titanic", "Romance", 12);

        agregarCliente("11.111.111-1", "Juan Pérez", "juan@gmail.com", "12345678");
        agregarCliente("22.222.222-2", "María González", "maria@gmail.com", "87654321");
        agregarCliente("33.333.333-3", "Pedro Rodríguez", "pedro@gmail.com", "12348765");

        rentarPelicula("11.111.111-1", "El Padrino", 2);
        rentarPelicula("22.222.222-2", "Titanic", 1);
        rentarPelicula("33.333.333-3", "El Padrino II", 3);
    }

    public boolean existeGenero(String genero) {
        return gestorPeliculas.existeGenero(genero);
    }

    public void agregarGenero(String nombre) {
        gestorPeliculas.agregarGenero(nombre);
    }

    public void agregarPelicula(String titulo, String genero, int precioSemanal) {
        gestorPeliculas.agregarPelicula(titulo, genero, precioSemanal);
    }

    public String obtenerListaPeliculas() {
        return gestorPeliculas.obtenerStringListaPeliculas();
    }

    public String obtenerListaPeliculas(String nombreGenero) {
        return gestorPeliculas.obtenerStringListaPeliculas(nombreGenero);
    }

    public String obtenerListaGeneros() {
        return gestorPeliculas.obtenerStringListaGeneros();
    }

    public boolean existeCliente(String rut) {
        return gestorClientes.existeCliente(rut);
    }

    public void agregarCliente(String rut, String nombreApellidos, String correo, String telefono) {
        gestorClientes.agregarCliente(rut, nombreApellidos, correo, telefono);
    }

    public void rentarPelicula(String rut, String tituloPelicula, int semanas) {
        Renta renta = new Renta(gestorRentas.getIdSiguiente(), gestorClientes.obtenerCliente(rut),
                gestorPeliculas.obtenerPelicula(tituloPelicula), semanas);
        gestorRentas.agregarRenta(renta);
        gestorClientes.agregarRenta(rut, renta);
    }

    public int precioPelicula(String titulo, int semanas) {
        return gestorPeliculas.precioPelicula(titulo, semanas);
    }

    public int precioPelicula(String titulo) {
        return gestorPeliculas.precioPelicula(titulo);
    }

    public boolean existePelicula(String titulo) {
        return gestorPeliculas.existePelicula(titulo);
    }

    public String obtenerListaRentasPendientes() {
        return gestorRentas.obtenerListaRentasPendientes();
    }

    public String obtenerListaRentasPendientes(String rut) {
        return gestorRentas.obtenerListaRentasPendientes(rut);
    }

    public void devolverPelicula(int id) {
        gestorRentas.devolverPelicula(id);
    }

    public String recomendarPelicula(String rut) {
        String nombreGeneroFavorito = gestorClientes.obtenerNombreGeneroFavorito(rut);
        if (nombreGeneroFavorito == null) {
            return null;
        }

        Pelicula pelicula = gestorRentas.peliculaMasVendidaGenero(nombreGeneroFavorito);
        if (pelicula == null) {
            return null;
        }

        return pelicula.toString();
    }

    public String obtenerNombreCliente(String rut) {
        if (!gestorClientes.existeCliente(rut)) {
            return null;
        }
        return gestorClientes.obtenerCliente(rut).getNombreApellidos();
    }

    public String detallesCliente(String rut) {
        return gestorClientes.obtenerDetallesCliente(rut);
    }

    public List<String> obtenerNombresClientes() {
        return gestorClientes.obtenerNombresClientes();
    }

    public String detallesPelicula(String titulo) {
        return gestorPeliculas.obtenerDetallesPelicula(titulo);
    }

    public void editarPelicula(String titulo, String nuevoTitulo, String nuevoGenero, int nuevoPrecio) {
        gestorPeliculas.editarPelicula(titulo, nuevoTitulo, nuevoGenero, nuevoPrecio);
    }

    public boolean peliculaTieneRentasActivas(String titulo) {
        Pelicula pelicula = gestorPeliculas.obtenerPelicula(titulo);
        if (pelicula != null) {
            return gestorRentas.peliculaTieneRentasActivas(pelicula);
        }
        return false;
    }

    public void eliminarPelicula(String titulo) {
        gestorPeliculas.eliminarPelicula(titulo);
    }

    public void editarGenero(String genero, String nuevoNombre) {
        gestorPeliculas.editarGenero(genero, nuevoNombre);
    }

    public void eliminarGenero(String nombre) {
        gestorPeliculas.eliminarGenero(nombre);
    }

    public String obtenerListaMasRentadaGenero() {
        return gestorRentas.obtenerListaMasRentadaGenero();
    }

    public void generarReportePeliculas() throws ReporteTextoException, ReportePlanillaException {
        gestorPeliculas.generarReporteTexto();
        gestorPeliculas.generarReportePlanilla();
    }

    public void insertarDatos() throws SQLException {
        gestorBaseDatos.eliminarDatos();
        gestorBaseDatos.insertarGeneros(gestorPeliculas.obtenerListaStringGeneros());
        gestorBaseDatos.insertarPeliculas(gestorPeliculas.obtenerListaStringPeliculas());
        gestorBaseDatos.insertarClientes(gestorClientes.obtenerListaStringClientes());
        gestorBaseDatos.insertarRentas(gestorRentas.obtenerListaStringRentas());
    }

    public void cargarDatos() throws SQLException {
            gestorBaseDatos.cargarDatos(gestorPeliculas, gestorClientes, gestorRentas);
    }

    // Getter y setters
    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    public void setGestorClientes(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;
    }

    public GestorPeliculas getGestorPeliculas() {
        return gestorPeliculas;
    }

    public void setGestorPeliculas(GestorPeliculas gestorPeliculas) {
        this.gestorPeliculas = gestorPeliculas;
    }

    public GestorRentas getGestorRentas() {
        return gestorRentas;
    }

    public void setGestorRentas(GestorRentas gestorRentas) {
        this.gestorRentas = gestorRentas;
    }
}
