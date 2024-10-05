package org.example.zmovies.Modelos;

import org.example.zmovies.Exceptions.ReportePlanillaException;
import org.example.zmovies.Exceptions.ReporteTextoException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase que representa un videoclub.
 * Contiene los gestores de clientes, películas y rentas.
 * También contiene un gestor de base de datos.
 * Tiene métodos para administrar datos del videoclub.
 */
public class VideoClub {
    private final GestorClientes gestorClientes;
    private final GestorPeliculas gestorPeliculas;
    private final GestorRentas gestorRentas;
    private final GestorBaseDatos gestorBaseDatos;
    // No se deben cambiar los gestores, por lo que no hay setters o getters para ellos.

    /**
     * Constructor de la clase VideoClub.
     * Inicializa los gestores y la conexión a la base de datos.
     */
    public VideoClub() {
        gestorClientes = new GestorClientes();
        gestorPeliculas = new GestorPeliculas();
        gestorRentas = new GestorRentas();

        // Ruta relativa del archivo de la base de datos
        File dbFile = new File("src/main/resources/sql/videoclub.sqlite");
        if (dbFile.exists()) {
            String jdbcUrl = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            gestorBaseDatos = new GestorBaseDatos(jdbcUrl);
        } else {
            gestorBaseDatos = new GestorBaseDatos("jdbc:sqlite:videoclub.sqlite");
        }
    }

    /**
     * Inicia el VideoClub, inicializando las tablas y cargando datos.
     *
     * @throws SQLException Si ocurre un error al inicializar las tablas.
     */
    public void start() throws SQLException {
        gestorBaseDatos.inicializarTablas();
        cargarDatos();
    }

    /**
     * Agrega un género al videoclub.
     *
     * @param nombre Nombre del género a agregar.
     */
    public void agregarGenero(String nombre) {
        gestorPeliculas.agregarGenero(nombre);
    }

    /**
     * Agrega una película al videoclub.
     *
     * @param titulo Título de la película.
     * @param genero Género de la película.
     * @param precioSemanal Precio semanal de la película.
     */
    public void agregarPelicula(String titulo, String genero, int precioSemanal) {
        gestorPeliculas.agregarPelicula(titulo, genero, precioSemanal);
    }

    /**
     * Obtiene una lista de String de películas.
     *
     * @return Lista de String de películas.
     */
    public List<String> obtenerListaPeliculasString() {
        return gestorPeliculas.obtenerListaPeliculasString();
    }

    /**
     * Obtiene una lista de String de películas de un género.
     *
     * @param nombreGenero Nombre del género.
     * @return Lista de String de películas de un género.
     */
    public String obtenerStringPeliculasGenero(String nombreGenero) {
        return gestorPeliculas.obtenerStringPeliculasGenero(nombreGenero);
    }

    /**
     * Obtiene una lista en formato String de las películas.
     *
     * @return Lista en formato String de las películas.
     */
    public String obtenerStringPeliculas() {
        return gestorPeliculas.obtenerStringPeliculas();
    }

    /**
     * Obtiene una lista de String de géneros.
     *
     * @return Lista de String de géneros.
     */
    public List<String> obtenerListaStringGeneros() {
        return gestorPeliculas.obtenerListaStringGeneros();
    }

    /**
     * Verifica si un cliente existe.
     *
     * @param rut RUT del cliente.
     * @return true si el cliente existe, false en caso contrario.
     */
    public boolean existeCliente(String rut) {
        return gestorClientes.existeCliente(rut);
    }

    /**
     * Agrega un cliente al videoclub.
     *
     * @param rut RUT del cliente.
     * @param nombreApellidos Nombre y apellidos del cliente.
     * @param correo Correo del cliente.
     * @param telefono Teléfono del cliente.
     */
    public void agregarCliente(String rut, String nombreApellidos, String correo, String telefono) {
        gestorClientes.agregarCliente(rut, nombreApellidos, correo, telefono);
    }

    /**
     * Renta una película a un cliente.
     *
     * @param rut RUT del cliente.
     * @param tituloPelicula Título de la película.
     * @param semanas Semanas de renta.
     */
    public void rentarPelicula(String rut, String tituloPelicula, int semanas) {
        Renta renta = new Renta(gestorRentas.getIdSiguiente(), gestorClientes.obtenerCliente(rut),
                gestorPeliculas.obtenerPelicula(tituloPelicula), semanas);
        gestorRentas.agregarRenta(renta);
        gestorClientes.agregarRenta(rut, renta);
    }

    /**
     * Obtiene el género de una película.
     *
     * @param titulo Título de la película.
     * @return Nombre del género de la película.
     */
    public String obtenerGeneroPelicula(String titulo) {
        return gestorPeliculas.obtenerGeneroPelicula(titulo);
    }

    /**
     * Verifica si un género existe.
     *
     * @param genero Nombre del género.
     * @return true si el género existe, false en caso contrario.
     */
    public boolean existeGenero(String genero) {
        return gestorPeliculas.existeGenero(genero);
    }

    /**
     * Obtiene el precio de una película.
     *
     * @param titulo Título de la película.
     * @return Precio de la película.
     */
    public int precioPelicula(String titulo) {
        return gestorPeliculas.precioPelicula(titulo);
    }

    /**
     * Obtiene el precio de una película en base a las semanas de renta.
     *
     * @param titulo Título de la película.
     * @param semanas Semanas de renta.
     * @return Precio de la película.
     */
    public int precioPelicula(String titulo, int semanas) {
        return gestorPeliculas.precioPelicula(titulo, semanas);
    }

    /**
     * Obtiene el precio de una película en formato String.
     *
     * @param titulo Título de la película.
     * @return Precio de la película en formato String.
     */
    public String obtenerPrecioPelicula(String titulo) {
        return String.valueOf(precioPelicula(titulo));
    }

    /**
     * Verifica si una película existe.
     *
     * @param titulo Título de la película a verificar.
     * @return true si la película existe, false en caso contrario.
     */
    public boolean existePelicula(String titulo) {
        return gestorPeliculas.existePelicula(titulo);
    }

    /**
     * Obtiene una lista en formato String de rentas pendientes.
     *
     * @return Lista en formato String de rentas pendientes.
     */
    public String obtenerListaRentasPendientes() {
        return gestorRentas.obtenerListaRentasPendientes();
    }

    /**
     * Obtiene una lista en formato String de rentas pendientes de un cliente.
     *
     * @param rut RUT del cliente.
     * @return Lista en formato String de rentas pendientes de un cliente.
     */
    public String obtenerListaRentasPendientes(String rut) {
        return gestorRentas.obtenerListaRentasPendientes(rut);
    }

    /**
     * Devuelve una película rentada.
     *
     * @param id ID de la renta.
     */
    public void devolverPelicula(int id) {
        gestorRentas.devolverPelicula(id);
    }

    /**
     * Recomienda una película basada en el género favorito de un cliente.
     *
     * @param rut RUT del cliente.
     * @return Título de la película recomendada o null si no hay recomendación.
     */
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

    /**
     * Obtiene el nombre de un cliente.
     *
     * @param rut RUT del cliente.
     * @return Nombre del cliente.
     */
    public String obtenerNombreCliente(String rut) {
        if (!gestorClientes.existeCliente(rut)) {
            return null;
        }
        return gestorClientes.obtenerCliente(rut).getNombreApellidos();
    }

    /**
     * Obtiene detalles de un cliente.
     *
     * @param rut RUT del cliente.
     * @return Detalles del cliente.
     */
    public String detallesCliente(String rut) {
        return gestorClientes.obtenerDetallesCliente(rut);
    }

    /**
     * Obtiene una lista de String de rut y nombre de clientes.
     *
     * @return Lista de String de rut y nombre de clientes.
     */
    public List<String> obtenerListaRutNombreClientes() {
        return gestorClientes.obtenerListaRutNombreClientes();
    }

    /**
     * Obtiene detalles de una película.
     * @param titulo Título de la película.
     * @return Detalles de la película.
     */
    public String detallesPelicula(String titulo) {
        return gestorPeliculas.obtenerDetallesPelicula(titulo);
    }

    /**
     * Obtiene detalles de una renta.
     * @param id ID de la renta.
     * @return Detalles de la renta.
     */
    public String detallesRenta(int id) {
        return gestorRentas.obtenerRenta(id).toString();
    }

    /**
     * Obtiene el título de la película de una renta.
     * @param id ID de la renta.
     * @return Título de la película.
     */
    public String obtenerPeliculaRenta(int id) {
        return gestorRentas.obtenerRenta(id).getPelicula().getTitulo();
    }

    /**
     * Edita los detalles de una película.
     * @param titulo Título actual de la película.
     * @param nuevoTitulo Nuevo título.
     * @param nuevoGenero Nuevo género.
     * @param nuevoPrecio Nuevo precio.
     */
    public void editarPelicula(String titulo, String nuevoTitulo, String nuevoGenero, int nuevoPrecio) {
        gestorPeliculas.editarPelicula(titulo, nuevoTitulo, nuevoGenero, nuevoPrecio);
    }

    /**
     * Elimina una película del gestor de películas.
     * @param titulo Título de la película a eliminar.
     */
    public void eliminarPelicula(String titulo) {
        gestorPeliculas.eliminarPelicula(titulo);
    }

    /**
     * Edita un género existente.
     * @param genero Nombre del género actual.
     * @param nuevoNombre Nuevo nombre del género.
     */
    public  void editarGenero(String genero, String nuevoNombre) {
        gestorPeliculas.editarGenero(genero, nuevoNombre);
    }
    /**
     * Elimina un género del sistema.
     * @param nombre Nombre del género a eliminar.
     */
    public void eliminarGenero(String nombre) {
        gestorPeliculas.eliminarGenero(nombre);
    }

    /**
     * Obtiene todos los nombres de los géneros en formato String.
     * @return String que contiene los nombres de los géneros.
     */
    public String obtenerNombresGeneros() {
        return gestorPeliculas.obtenerNombresGeneros();
    }

    /**
     * Obtiene la película más rentada de un género específico.
     * @param genero Nombre del género.
     * @return Título y precio de la película más rentada o null si no hay.
     */
    public String obtenerMasRentadaGenero(String genero) {
        Pelicula masRentada = gestorRentas.peliculaMasVendidaGenero(genero);
        if (masRentada == null) {
            return null;
        }
        return masRentada.getTitulo() + " - $" + masRentada.getPrecioSemanal();
    }

    /**
     * Genera reportes de películas en formato texto y planilla.
     * @param ruta Ruta donde se guardarán los reportes.
     * @throws ReporteTextoException Si ocurre un error al generar el reporte de texto.
     * @throws ReportePlanillaException Si ocurre un error al generar el reporte de planilla.
     */
    public void generarReportePeliculas(String ruta) throws ReporteTextoException, ReportePlanillaException {
        gestorPeliculas.generarReporteTexto(ruta);
        gestorPeliculas.generarReportePlanilla(ruta);
    }

    /**
     * Inserta datos en la base de datos desde los gestores.
     * @throws SQLException Si ocurre un error al insertar los datos.
     */
    public void insertarDatos() throws SQLException {
        gestorBaseDatos.eliminarDatos();
        gestorBaseDatos.insertarGeneros(gestorPeliculas.obtenerListaStringGeneros());
        gestorBaseDatos.insertarPeliculas(gestorPeliculas.obtenerListaStringPeliculas());
        gestorBaseDatos.insertarClientes(gestorClientes.obtenerListaStringClientes());
        gestorBaseDatos.insertarRentas(gestorRentas.obtenerListaStringRentas());
    }

    /**
     * Carga los datos desde la base de datos a los gestores.
     * @throws SQLException Si ocurre un error al cargar los datos.
     */
    public void cargarDatos() throws SQLException {
            gestorBaseDatos.cargarDatos(gestorPeliculas, gestorClientes, gestorRentas);
            if (!existeGenero("SIN GENERO")) {
                agregarGenero("SIN GENERO");
            }
    }
}
