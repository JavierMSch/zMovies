package org.example.zmovies.Modelos;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class GestorBaseDatos {
    private String url;

    public GestorBaseDatos(String url) {
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

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
        cursor.execute(sqlClientes);
        cursor.execute(sqlGeneros);
        cursor.execute(sqlPeliculas);
        cursor.execute(sqlRentas);
    }

    public boolean insertarGeneros(List<Genero> generos) throws SQLException {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (Genero genero : generos) {
            cursor.setString(1, genero.getNombre());
            cursor.addBatch();
        }
        cursor.executeBatch();

        return true;
    }

    public boolean insertarPeliculas(List<Pelicula> peliculas) throws SQLException {
        String sql = "INSERT INTO pelicula (titulo, nombre_genero, precio_semanal, activa) VALUES (?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (Pelicula pelicula : peliculas) {
            cursor.setString(1, pelicula.getTitulo());
            cursor.setString(2, pelicula.getGenero());
            cursor.setInt(3, pelicula.getPrecioSemanal());
            cursor.setInt(4, pelicula.isActiva() ? 1 : 0);
            cursor.addBatch();
        }
        cursor.executeBatch();

        return true;
    }

    public boolean insertarClientes(List<Cliente> clientes) throws SQLException {
        String sql = "INSERT INTO cliente (rut, nombre, correo, telefono) VALUES (?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (Cliente cliente : clientes) {
            cursor.setString(1, cliente.getRut());
            cursor.setString(2, cliente.getNombreApellidos());
            cursor.setString(3, cliente.getCorreo());
            cursor.setString(4, cliente.getTelefono());
            cursor.addBatch();
        }
        cursor.executeBatch();

        return true;
    }

    public boolean insertarRentas(List<Renta> rentas) throws SQLException {
        String sql = "INSERT INTO renta (id, rut_cliente, titulo_pelicula, semanas, fecha_renta, fecha_devolucion, monto, devuelta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement cursor = conn.prepareStatement(sql);
        for (Renta renta : rentas) {
            cursor.setInt(1, renta.getId());
            cursor.setString(2, renta.getCliente().getRut());
            cursor.setString(3, renta.getPelicula().getTitulo());
            cursor.setInt(4, renta.getSemanasRentadas());
            cursor.setString(5, renta.getFecha().toString());
            cursor.setString(6, renta.getFechaDevolucion().toString());
            cursor.setInt(7, renta.getMonto());
            cursor.setInt(8, renta.isPeliculaDevuelta() ? 1 : 0);
            cursor.addBatch();
        }
        cursor.executeBatch();

        return true;
    }

    public boolean eliminarDatos() throws SQLException {
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
        return true;
    }

    public boolean cargarDatos(GestorPeliculas gestorPeliculas, GestorClientes gestorClientes, GestorRentas gestorRentas) throws SQLException {
        cargarGeneros(gestorPeliculas);
        cargarPeliculas(gestorPeliculas);
        cargarClientes(gestorClientes);
        cargarRentas(gestorRentas, gestorPeliculas, gestorClientes);
        return true;
    }

    private void cargarGeneros(GestorPeliculas gestorPeliculas) throws SQLException {
        String sql = "SELECT * FROM genero";

        Connection conn = getConnection();
        Statement cursor = conn.createStatement();
        ResultSet set = cursor.executeQuery(sql);
        while (set.next()) {
            gestorPeliculas.agregarGenero(set.getString("nombre"));
        }
    }

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

            gestorRentas.agregarRenta(renta);
            gestorClientes.agregarRenta(rut, renta);

            gestorRentas.actualizarIdSiguiente();
        }
    }
}
