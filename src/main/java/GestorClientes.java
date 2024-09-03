import java.util.HashMap;
import java.util.Map;

public class GestorClientes {
    private Map<String, Cliente> mapaClientes;

    public GestorClientes() {
        mapaClientes = new HashMap<>();
        //TODO: Ver si dejar datosTest
        datosTest();
    }

    private void datosTest() {
        Cliente clienteTest = new Cliente("11.111.111-1", "Juan Pérez", "juan@gmail.com", "12345678");
        agregarCliente(clienteTest);
        clienteTest = new Cliente("22.222.222-2", "María González", "maria@gmail.com", "87654321");
        agregarCliente(clienteTest);
        clienteTest = new Cliente("33.333.333-3", "Pedro Rodríguez", "pedro@gmail.com", "12348765");
        agregarCliente(clienteTest);
    }

    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null || mapaClientes.containsKey(cliente.getRut())) {
            return false;
        }
        mapaClientes.put(cliente.getRut(), cliente);
        return true;
    }

    public Cliente obtenerCliente(String rut) {
        return mapaClientes.get(rut);
    }

    public String obtenerGeneroFavorito(String rut) {
        // TODO
        Cliente cliente = mapaClientes.get(rut);
        if (cliente == null) {
            return null;
        }
        return cliente.generoFavorito();
    }

    public boolean existeCliente(String rut) {
        return mapaClientes.containsKey(rut);
    }


    // Getter y setters
    public Map<String, Cliente> getMapaClientes() {
        return mapaClientes;
    }

    public void setMapaClientes(Map<String, Cliente> mapaClientes) {
        this.mapaClientes = mapaClientes;
    }
}