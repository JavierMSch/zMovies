import java.util.HashMap;
import java.util.Map;

public class GestorClientes {
    private Map<String, Cliente> mapaClientes;

    public GestorClientes() {
        mapaClientes = new HashMap<>();
        datosTest();
    }

    private void datosTest() {
        agregarCliente("11.111.111-1", "Juan Pérez", "juan@gmail.com", "12345678");
        agregarCliente("22.222.222-2", "María González", "maria@gmail.com", "87654321");
        agregarCliente("33.333.333-3", "Pedro Rodríguez", "pedro@gmail.com", "12348765");
    }

    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null || mapaClientes.containsKey(cliente.getRut())) {
            return false;
        }
        mapaClientes.put(cliente.getRut(), cliente);
        return true;
    }

    public boolean agregarCliente(String rut, String nombreApellido, String correo, String telefono) {
        Cliente cliente = new Cliente(rut, nombreApellido, correo, telefono);
        if (mapaClientes.containsKey(cliente.getRut())) {
            return false;
        }
        mapaClientes.put(cliente.getRut(), cliente);
        return true;
    }

    public Cliente obtenerCliente(String rut) {
        return mapaClientes.get(rut);
    }

    public String obtenerNombreGeneroFavorito(String rut) {
        Cliente cliente = mapaClientes.get(rut);
        if (cliente == null) {
            return null;
        }
        return cliente.nombreGeneroFavorito();
    }

    public boolean existeCliente(String rut) {
        return mapaClientes.containsKey(rut);
    }

    public void agregarRenta(String rut, Renta renta) {
        obtenerCliente(rut).agregarRentaAHistorial(renta);
    }

    public String obtenerDetallesCliente(String rut) {
        if (obtenerCliente(rut) == null) {
            return null;
        }
        return obtenerCliente(rut).toString();
    }

    // Getter y setters
    public Map<String, Cliente> getMapaClientes() {
        return mapaClientes;
    }

    public void setMapaClientes(Map<String, Cliente> mapaClientes) {
        this.mapaClientes = mapaClientes;
    }
}