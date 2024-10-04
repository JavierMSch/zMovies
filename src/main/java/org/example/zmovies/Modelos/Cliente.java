package org.example.zmovies.Modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * La clase Cliente representa un cliente del video club. Almacena su RUT, nombre completo, correo electrónico,
 * teléfono y un historial de rentas.
 */
public class Cliente {
    private String rut;
    private String nombreApellidos;
    private String correo;
    private String telefono;
    private List<Renta> historialRentas;

    /**
     * Constructor que inicializa un nuevo cliente con su RUT, nombre completo, correo electrónico y teléfono.
     *
     * @param rut el RUT del cliente
     * @param nombre el nombre completo del cliente
     * @param correo el correo electrónico del cliente
     * @param telefono el número de teléfono del cliente
     */
    public Cliente(String rut, String nombre, String correo, String telefono) {
        this.rut = rut;
        this.nombreApellidos = nombre;
        this.correo = correo;
        this.telefono = telefono;
        historialRentas = new ArrayList<>();
    }

    /**
     * Devuelve el historial de rentas del cliente.
     *
     * @return el historial de rentas del cliente
     */
    public void agregarRentaAHistorial(Renta renta) {
        historialRentas.add(renta);
    }

    /**
     * Devuelve el historial de rentas del cliente.
     *
     * @return el historial de rentas del cliente
     */
    public String nombreGeneroFavorito() {
        Map<String, Integer> contadorGeneros = new HashMap<>();
        for (Renta renta : historialRentas) {
            String genero = renta.getPelicula().getGenero();
            contadorGeneros.put(genero, contadorGeneros.getOrDefault(genero, 0) + 1);
        }

        String generoFavorito = null;
        int max = 0;
        for (String key : contadorGeneros.keySet()) {
            if (generoFavorito == null || contadorGeneros.get(key) > max) {
                generoFavorito = key;
                max = contadorGeneros.get(key);
            }
        }
        return generoFavorito;
    }

    /**
     * Devuelve una representación en cadena del cliente, que incluye su RUT, nombre, correo y teléfono.
     *
     * @return una cadena con la información del cliente
     */
    @Override
    public String toString() {
        return "RUT: " + rut + "\nNombre y apellido(s): " + nombreApellidos + "\nCorreo: " + correo + "\nTeléfono: " + telefono;
    }

    // Getters y Setters

    /**
     * Obtiene el RUT del cliente.
     *
     * @return el RUT del cliente
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del cliente.
     *
     * @param rut el nuevo RUT del cliente
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return el nombre completo del cliente
     */
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    /**
     * Establece el nombre completo del cliente.
     *
     * @param nombreApellidos el nuevo nombre completo del cliente
     */
    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return el correo electrónico del cliente
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo el nuevo correo electrónico del cliente
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return el número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono el nuevo número de teléfono del cliente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
