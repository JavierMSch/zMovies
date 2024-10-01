import java.sql.*;
import java.util.List;

public class GestorBaseDatos {
    private String url;

    public GestorBaseDatos(String url) {
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void inicializarTablas() {
        String sqlClientes = "CREATE TABLE IF NOT EXISTS cliente ("
                + "rut TEXT NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "correo TEXT NOT NULL,"
                + "telefono TEXT NOT NULL"
                + ");";

        String sqlGeneros = "CREATE TABLE IF NOT EXISTS genero ("
                + "nombre TEXT NOT NULL PRIMARY KEY"
                + ");";

        String sqlPeliculas = "CREATE TABLE IF NOT EXISTS pelicula ("
                + "titulo TEXT NOT NULL PRIMARY KEY,"
                + "nombre_genero TEXT NOT NULL,"
                + "precio_semanal INTEGER NOT NULL,"
                + "activa INTEGER NOT NULL,"
                + "FOREIGN KEY (nombre_genero) REFERENCES genero(nombre)"
                + ");";

        String sqlRentas = "CREATE TABLE IF NOT EXISTS renta ("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "rut_cliente TEXT NOT NULL,"
                + "titulo_pelicula TEXT NOT NULL,"
                + "semanas INTEGER NOT NULL,"
                + "fecha_renta TEXT NOT NULL,"
                + "fecha_devolucion TEXT,"
                + "monto INTEGER NOT NULL,"
                + "devuelta INTEGER NOT NULL,"
                + "FOREIGN KEY (rut_cliente) REFERENCES cliente(rut),"
                + "FOREIGN KEY (titulo_pelicula) REFERENCES pelicula(titulo)"
                + ");";

        try (Connection conn = getConnection()) {
            Statement cursor = conn.createStatement();
            cursor.execute(sqlClientes);
            cursor.execute(sqlGeneros);
            cursor.execute(sqlPeliculas);
            cursor.execute(sqlRentas);
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean insertarGeneros(List<Genero> generos) {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";

        try (Connection conn = getConnection()) {
            PreparedStatement cursor = conn.prepareStatement(sql);
            for (Genero genero : generos) {
                cursor.setString(1, genero.getNombre());
                cursor.addBatch();
            }
            cursor.executeBatch();
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean insertarPeliculas(List<Pelicula> peliculas) {
        String sql = "INSERT INTO pelicula (titulo, nombre_genero, precio_semanal, activa) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            PreparedStatement cursor = conn.prepareStatement(sql);
            for (Pelicula pelicula : peliculas) {
                cursor.setString(1, pelicula.getTitulo());
                cursor.setString(2, pelicula.getGenero());
                cursor.setInt(3, pelicula.getPrecioSemanal());
                cursor.setInt(4, pelicula.isActiva() ? 1 : 0);
                cursor.addBatch();
            }
            cursor.executeBatch();
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean insertarClientes(List<Cliente> clientes) {
        String sql = "INSERT INTO cliente (rut, nombre, correo, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            PreparedStatement cursor = conn.prepareStatement(sql);
            for (Cliente cliente : clientes) {
                cursor.setString(1, cliente.getRut());
                cursor.setString(2, cliente.getNombreApellidos());
                cursor.setString(3, cliente.getCorreo());
                cursor.setString(4, cliente.getTelefono());
                cursor.addBatch();
            }
            cursor.executeBatch();
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean insertarRentas(List<Renta> rentas) {
        String sql = "INSERT INTO renta (id, rut_cliente, titulo_pelicula, semanas, fecha_renta, fecha_devolucion, monto, devuelta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
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
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean eliminarDatos() {
        String sqlClientes = "DELETE FROM cliente";
        String sqlGeneros = "DELETE FROM genero";
        String sqlPeliculas = "DELETE FROM pelicula";
        String sqlRentas = "DELETE FROM renta";

        try (Connection conn = getConnection()) {
            Statement cursor = conn.createStatement();
            cursor.execute(sqlClientes);
            cursor.execute(sqlGeneros);
            cursor.execute(sqlPeliculas);
            cursor.execute(sqlRentas);
        } catch(SQLException e) {
            //TODO exception custom
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
