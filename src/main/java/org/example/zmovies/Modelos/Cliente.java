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
    private final List<Renta> historialRentas;

    /**
     * Constructor que inicializa un nuevo cliente con su RUT, nombre completo, correo electrónico y teléfono.
     *
     * @param rut RUT del cliente
     * @param nombre Nombre completo del cliente
     * @param correo Correo electrónico del cliente
     * @param telefono Número de teléfono del cliente
     */
    public Cliente(String rut, String nombre, String correo, String telefono) {
        this.rut = rut;
        this.nombreApellidos = nombre;
        this.correo = correo;
        this.telefono = telefono;
        historialRentas = new ArrayList<>();
    }

    /**
     * Agrega una renta al historial de rentas del cliente.
     *
     * @param renta Renta a agregar al historial
     */
    public void agregarRentaAHistorial(Renta renta) {
        historialRentas.add(renta);
    }

    /**
     * Retorna el nombre del género que más ha rentado el cliente.
     *
     * @return Nombre del género más rentado.
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
     * @return Cadena con la información del cliente
     */
    @Override
    public String toString() {
        return "RUT: " + rut + "\nNombre y apellido(s): " + nombreApellidos + "\nCorreo: " + correo + "\nTeléfono: " + telefono;
    }

    // Getters y Setters

    /**
     * Obtiene el RUT del cliente.
     *
     * @return RUT del cliente
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del cliente.
     *
     * @param rut Nuevo RUT del cliente
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return Nombre completo del cliente
     */
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    /**
     * Establece el nombre completo del cliente.
     *
     * @param nombreApellidos Nuevo nombre completo del cliente
     */
    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return Correo electrónico del cliente
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo Nuevo correo electrónico del cliente
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return Número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono Nuevo número de teléfono del cliente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
