package org.example.zmovies.Modelos;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que se encarga de la gestión de la base de datos.
 * Se encarga de crear las tablas, insertar datos, eliminar datos y cargar datos.
 */
public class GestorBaseDatos {
    private String url; // URL de la base de datos

    /**
     * Constructor de la clase.
     * @param url URL de la base de datos.
     */
    public GestorBaseDatos(String url) {
        this.url = url;
    }

    /**
     * Establece una conexión a la base de datos.
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al conectar.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Inicializa las tablas en la base de datos.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void inicializarTablas() throws SQLException {
        String sqlClientes = """
                CREATE TABLE IF NOT EXISTS cliente (
                rut TEXT NOT NULL PRIMARY KEY,
                nombre TEXT NOT NULL,
                correo TEXT NOT NULL,
                telefono TEXT NOT NULL
                );
        """;

        String sqlGeneros = """
                CREATE TABLE IF NOT EXISTS genero (
                nombre TEXT NOT NULL PRIMARY KEY
                );
        """;

        String sqlPeliculas = """
                CREATE TABLE IF NOT EXISTS pelicula (
                titulo TEXT NOT NULL PRIMARY KEY,
                nombre_genero TEXT NOT NULL,
                precio_semanal INTEGER NOT NULL,
                activa INTEGER NOT NULL,
                FOREIGN KEY (nombre_genero) REFERENCES genero(nombre)
                );
        """;

        String sqlRentas = """
                CREATE TABLE IF NOT EXISTS renta (
                id INTEGER NOT NULL PRIMARY KEY,
                rut_cliente TEXT NOT NULL,
                titulo_pelicula TEXT NOT NULL,
                semanas INTEGER NOT NULL,
                fecha_renta TEXT NOT NULL,
                fecha_devolucion TEXT,
                monto INTEGER NOT NULL,
                devuelta INTEGER NOT NULL,
                FOREIGN KEY (rut_cliente) REFERENCES cliente(rut),
                FOREIGN KEY (titulo_pelicula) REFERENCES pelicula(titulo)
                );
        """;

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();

        // Se ejecutan las instrucciones SQL
        cursor.execute(sqlClientes);
        cursor.execute(sqlGeneros);
        cursor.execute(sqlPeliculas);
        cursor.execute(sqlRentas);
    }

    /**
     * Inserta los géneros en la base de datos.
     * @param generos Lista de géneros a insertar.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void insertarGeneros(List<String> generos) throws SQLException {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (String genero : generos) {
            cursor.setString(1, genero);
            // Se añade la instrucción a la lista de instrucciones
            cursor.addBatch();
        }
        // Se ejecutan las instrucciones
        cursor.executeBatch();
    }

    /**
     * Inserta las películas en la base de datos.
     * @param peliculas Lista de películas a insertar con sus datos en formato csv (titulo,nombre_genero,precio_semanal,activa).
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void insertarPeliculas(List<String> peliculas) throws SQLException {
        String sql = "INSERT INTO pelicula (titulo, nombre_genero, precio_semanal, activa) VALUES (?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (String pelicula : peliculas) {
            String[] datos = pelicula.split(",");
            cursor.setString(1, datos[0]);
            cursor.setString(2, datos[1]);
            cursor.setInt(3, Integer.parseInt(datos[2]));
            cursor.setInt(4, Integer.parseInt(datos[3]));
            // Se añade la instrucción a la lista de instrucciones
            cursor.addBatch();
        }
        // Se ejecutan las instrucciones
        cursor.executeBatch();
    }

    /**
     * Inserta los clientes en la base de datos.
     * @param clientes Lista de clientes a insertar con sus datos en formato csv (rut,nombre,correo,telefono).
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void insertarClientes(List<String> clientes) throws SQLException {
        String sql = "INSERT INTO cliente (rut, nombre, correo, telefono) VALUES (?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (String cliente : clientes) {
            String[] datos = cliente.split(",");
            cursor.setString(1, datos[0]);
            cursor.setString(2, datos[1]);
            cursor.setString(3, datos[2]);
            cursor.setString(4, datos[3]);
            // Se añade la instrucción a la lista de instrucciones
            cursor.addBatch();
        }
        // Se ejecutan las instrucciones
        cursor.executeBatch();
    }

    /**
     * Inserta las rentas en la base de datos.
     * @param rentas Lista de rentas a insertar con sus datos en formato csv (id,rut_cliente,titulo_pelicula,semanas,fecha_renta,fecha_devolucion,monto,devuelta).
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void insertarRentas(List<String> rentas) throws SQLException {
        String sql = "INSERT INTO renta (id, rut_cliente, titulo_pelicula, semanas, fecha_renta, fecha_devolucion, monto, devuelta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (String renta : rentas) {
            String[] datos = renta.split(",");
            cursor.setInt(1, Integer.parseInt(datos[0]));
            cursor.setString(2, datos[1]);
            cursor.setString(3, datos[2]);
            cursor.setInt(4, Integer.parseInt(datos[3]));
            cursor.setString(5, datos[4]);
            cursor.setString(6, datos[5]);
            cursor.setInt(7, Integer.parseInt(datos[6]));
            cursor.setInt(8, Integer.parseInt(datos[7]));
            // Se añade la instrucción a la lista de instrucciones
            cursor.addBatch();
        }
        // Se ejecutan las instrucciones
        cursor.executeBatch();
    }

    /**
     * Elimina los datos de las tablas de la base de datos.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void eliminarDatos() throws SQLException {
        String sqlClientes = "DELETE FROM cliente";
        String sqlGeneros = "DELETE FROM genero";
        String sqlPeliculas = "DELETE FROM pelicula";
        String sqlRentas = "DELETE FROM renta";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        cursor.execute(sqlClientes);
        cursor.execute(sqlGeneros);
        cursor.execute(sqlPeliculas);
        cursor.execute(sqlRentas);
    }

    /**
     * Carga los datos de las tablas de la base de datos en los gestores.
     * @param gestorPeliculas Gestor de películas.
     * @param gestorClientes Gestor de clientes.
     * @param gestorRentas Gestor de rentas.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    public void cargarDatos(GestorPeliculas gestorPeliculas, GestorClientes gestorClientes, GestorRentas gestorRentas) throws SQLException {
        cargarGeneros(gestorPeliculas);
        cargarPeliculas(gestorPeliculas);
        cargarClientes(gestorClientes);
        cargarRentas(gestorRentas, gestorPeliculas, gestorClientes);
    }

    /**
     * Carga los géneros de la base de datos en el gestor de películas.
     * @param gestorPeliculas Gestor de películas.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    private void cargarGeneros(GestorPeliculas gestorPeliculas) throws SQLException {
        String sql = "SELECT * FROM genero";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        ResultSet set = cursor.executeQuery(sql);
        while (set.next()) {
            gestorPeliculas.agregarGenero(set.getString("nombre"));
        }
    }

    /**
     * Carga las películas de la base de datos en el gestor de películas.
     * @param gestorPeliculas Gestor de películas.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    private void cargarPeliculas(GestorPeliculas gestorPeliculas) throws SQLException {
        String sql = "SELECT * FROM pelicula";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        ResultSet set = cursor.executeQuery(sql);
        while (set.next()) {
            String titulo = set.getString("titulo");
            String nombreGenero = set.getString("nombre_genero");
            int precio = set.getInt("precio_semanal");
            boolean activa = set.getBoolean("activa");

            gestorPeliculas.agregarPelicula(titulo, nombreGenero, precio, activa);
        }
    }

    /**
     * Carga los clientes desde la base de datos al gestor de clientes.
     * @param gestorClientes Gestor de clientes.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    private void cargarClientes(GestorClientes gestorClientes) throws SQLException {
        String sql = "SELECT * FROM cliente";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        ResultSet set = cursor.executeQuery(sql);
        while (set.next()) {
            String rut = set.getString("rut");
            String nombre = set.getString("nombre");
            String correo = set.getString("correo");
            String telefono = set.getString("telefono");

            gestorClientes.agregarCliente(rut, nombre, correo, telefono);
        }
    }

    /**
     * Carga las rentas desde la base de datos al gestor de rentas.
     * @param gestorRentas Gestor de rentas.
     * @param gestorPeliculas Gestor de películas.
     * @param gestorClientes Gestor de clientes.
     * @throws SQLException Si ocurre un error al ejecutar la instrucción SQL.
     */
    private void cargarRentas(GestorRentas gestorRentas, GestorPeliculas gestorPeliculas, GestorClientes gestorClientes) throws SQLException {
        String sql = "SELECT * FROM renta";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        ResultSet set = cursor.executeQuery(sql);
        while (set.next()) {
            int id = set.getInt("id");
            String rut = set.getString("rut_cliente");
            String titulo = set.getString("titulo_pelicula");
            int semanas = set.getInt("semanas");
            LocalDate fechaRenta = LocalDate.parse(set.getString("fecha_renta"));
            LocalDate fechaDev = LocalDate.parse(set.getString("fecha_devolucion"));
            int monto = set.getInt("monto");
            boolean devuelta = set.getBoolean("devuelta");

            Cliente cliente = gestorClientes.obtenerCliente(rut);
            Pelicula pelicula = gestorPeliculas.obtenerPeliculaActivaOInactiva(titulo);

            Renta renta = new Renta(id, cliente, pelicula, semanas, fechaRenta, fechaDev, monto, devuelta);

            // Agrega la renta al gestor de rentas
            gestorRentas.agregarRenta(renta);
            // Agrega la renta al historial del cliente
            gestorClientes.agregarRenta(rut, renta);

            // Actualiza el ID siguiente del gestor de rentas
            gestorRentas.actualizarIdSiguiente();
        }
    }

    // Getters y Setters
    /**
     * Devuelve la URL de la base de datos.
     * @return La URL de la base de datos.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL de la base de datos.
     * @param url La URL de la base de datos.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
