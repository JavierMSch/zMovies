package org.example.zmovies.Modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorClientes {
    private Map<String, Cliente> mapaClientes;

    public GestorClientes() {
        mapaClientes = new HashMap<>();
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

    public List<Cliente> obtenerListaClientes() {
        return new ArrayList<>(mapaClientes.values());
    }
}