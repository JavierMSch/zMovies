package org.example.zmovies.Modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase GestorClientes se encarga de administrar los clientes dentro del sistema.
 */
public class GestorClientes {
    private Map<String, Cliente> mapaClientes; // Mapa que contiene a los clientes, la clave es el rut del cliente.

    /**
     * Constructor de la clase GestorClientes.
     * Inicializa el mapa de clientes.
     */
    public GestorClientes() {
        mapaClientes = new HashMap<>();
    }

    /**
     * Agrega un cliente al sistema.
     *
     * @param rut Rut del cliente.
     * @param nombreApellido Nombre y apellidos del cliente.
     * @param correo Correo del cliente.
     * @param telefono Teléfono del cliente.
     * @return true si el cliente se agrega correctamente, false si ya existe un cliente con el mismo rut.
     */
    public boolean agregarCliente(String rut, String nombreApellido, String correo, String telefono) {
        Cliente cliente = new Cliente(rut, nombreApellido, correo, telefono);

        if (mapaClientes.containsKey(cliente.getRut())) {
            return false;
        }

        mapaClientes.put(cliente.getRut(), cliente);
        return true;
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param rut Rut del cliente a eliminar.
     * @return true si el cliente se elimina correctamente, false si no existe un cliente con el rut ingresado.
     */
    public Cliente obtenerCliente(String rut) {
        return mapaClientes.get(rut);
    }

    /**
     * Obtiene el nombre del género favorito de un cliente.
     *
     * @param rut Rut del cliente.
     * @return Nombre del género favorito del cliente, null si el cliente no existe.
     */
    public String obtenerNombreGeneroFavorito(String rut) {
        Cliente cliente = mapaClientes.get(rut);

        if (cliente == null) {
            return null;
        }

        return cliente.nombreGeneroFavorito();
    }

    /**
     * Verifica si un cliente existe en el sistema.
     *
     * @param rut Rut del cliente.
     * @return true si el cliente existe, false si no existe.
     */
    public boolean existeCliente(String rut) {
        return mapaClientes.containsKey(rut);
    }

    /**
     * Agrega una renta al historial de un cliente.
     *
     * @param rut Rut del cliente.
     * @param renta Renta a agregar al historial del cliente.
     */
    public void agregarRenta(String rut, Renta renta) {
        obtenerCliente(rut).agregarRentaAHistorial(renta);
    }

    /**
     * Obtiene los detalles de un cliente en String.
     *
     * @param rut Rut del cliente.
     * @return Detalles del cliente en String, null si el cliente no existe.
     */
    public String obtenerDetallesCliente(String rut) {
        if (obtenerCliente(rut) == null) {
            return null;
        }

        return obtenerCliente(rut).toString();
    }

    /**
     * Obtiene una lista de Strings con los datos de todos los clientes.
     *
     * @return Una lista de Strings con los datos de cada cliente.
     */
    public List<String> obtenerListaStringClientes() {
        List<String> listaStringClientes = new ArrayList<>();
        for (Cliente cliente : mapaClientes.values()) {
            listaStringClientes.add(cliente.getRut() + "," + cliente.getNombreApellidos() + "," + cliente.getCorreo() + "," + cliente.getTelefono());
        }
        return listaStringClientes;
    }

    /**
     * Obtiene una lista de Strings con los ruts y nombres de todos los clientes.
     *
     * @return Una lista de Strings con los ruts y nombres de cada cliente.
     */
    public List<String> obtenerListaRutNombreClientes() {
        List<String> listaClientes = new ArrayList<>();

        // Se recorren los clientes, que son los valores del mapa
        for (Cliente cliente : mapaClientes.values()) {
            listaClientes.add(cliente.getRut()  + " - " + cliente.getNombreApellidos());
        }
        return listaClientes;
    }
}