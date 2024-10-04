package org.example.zmovies.Modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Clase que se encarga de administrar las rentas de películas.
 */
public class GestorRentas {
    private List<Renta> listaRentas; // lista de rentas
    private int idSiguiente; // id de la próxima renta a agregar

    /**
     * Constructor de la clase GestorRentas.
     * Inicializa la lista de rentas y el idSiguiente en 1.
     */
    public GestorRentas() {
        listaRentas = new ArrayList<>();
        idSiguiente = 1;
    }

    /**
     * Agrega una renta a la lista de rentas.
     * @param renta renta a agregar
     * @return true si la renta se agrega correctamente, false si la renta es null o ya está en la lista
     */
    public boolean agregarRenta(Renta renta) {
        if (renta == null || listaRentas.contains(renta)) {
            return false;
        }
        listaRentas.add(renta);
        idSiguiente++; // incrementa el id de la próxima renta
        return true;
    }

    /**
     * Obtiene una renta por su ID.
     * @param id ID de la renta a buscar.
     * @return La renta correspondiente al ID, o null si no se encuentra.
     */
    public Renta obtenerRenta(int id) {
        for (Renta renta : listaRentas) {
            if (renta.getId() == id) {
                return renta;
            }
        }
        return null;
    }

    /**
     * Obtiene una representación en String de las rentas pendientes.
     * @return Un String con las rentas pendientes, cada una en una nueva línea.
     * Si no hay rentas pendientes, retorna null.
     */
    public String obtenerListaRentasPendientes() {
        StringBuilder cadenaRentas = new StringBuilder();
        for (Renta renta : listaRentas) {
            if (renta.estaPendiente()) {
                cadenaRentas.append(renta).append("\n");
            }
        }
        if (cadenaRentas.isEmpty()) {
            return null;
        }
        return cadenaRentas.toString();
    }

    /**
     * Obtiene una representación en String de las rentas pendientes de un cliente.
     * @param rut Rut del cliente a buscar.
     * @return Un String con las rentas pendientes del cliente, cada una en una nueva línea.
     * Si no hay rentas pendientes, retorna null.
     */
    public String obtenerListaRentasPendientes(String rut) {
        StringBuilder cadenaRentas = new StringBuilder();
        for (Renta renta : listaRentas) {
            if (renta.esCliente(rut) && renta.estaPendiente()) {
                cadenaRentas.append(renta).append("\n");
            }
        }
        if (cadenaRentas.isEmpty()) {
            return null;
        }
        return cadenaRentas.toString();
    }

    /**
     * Marca una renta de película como devuelta.
     * @param id ID de la renta a marcar como devuelta.
     */
    public void devolverPelicula(int id) {
        obtenerRenta(id).marcarDevuelta();
    }

    /**
     * Obtiene la película más rentada de un género.
     * @param nombreGenero Nombre del género.
     * @return La película más vendida del género, o null si no se encuentra el género.
     */
    public Pelicula peliculaMasVendidaGenero(String nombreGenero) {
        if (nombreGenero == null) {
            return null;
        }

        // Cuenta cuántas veces se ha rentado cada película del género
        Map<Pelicula, Integer> contadorPeliculas = new HashMap<>();
        for (Renta renta : listaRentas) {
            if (renta.getPelicula().getGenero().equals(nombreGenero)) {
                contadorPeliculas.put(renta.getPelicula(), contadorPeliculas.getOrDefault(renta.getPelicula(), 0) + 1);
            }
        }

        // Encuentra la película más rentada del género
        Pelicula masVendida = null;
        int max = 0;
        for (Pelicula key : contadorPeliculas.keySet()) {
            if (masVendida == null || contadorPeliculas.get(key) > max) {
                masVendida = key;
                max = contadorPeliculas.get(key);
            }
        }

        return masVendida;
    }

    /**
     * Verifica si una película tiene rentas activas.
     * @param pelicula La película a verificar.
     * @return true si tiene rentas activas, false en caso contrario.
     */
    public boolean peliculaTieneRentasActivas(Pelicula pelicula) {
        for (Renta renta: listaRentas) {
            if (renta.getPelicula() == pelicula && renta.estaPendiente()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza el ID siguiente para la próxima renta en base a las rentas en el sistema.
     */
    public void actualizarIdSiguiente() {
        setIdSiguiente(listaRentas.size() + 1);
    }

    /**
     * Obtiene una lista de rentas en formato String con sus datos separados por coma.
     * @return Una lista de Strings, cada uno representando una renta.
     */
    public List<String> obtenerListaStringRentas() {
        List<String> listaStringRentas = new ArrayList<>();

        for (Renta renta : listaRentas) {
            listaStringRentas.add(renta.getId() + "," + renta.getCliente().getRut() + ","
                    + renta.getPelicula().getTitulo() + "," + renta.getSemanasRentadas() + ","
                    + renta.getFecha().toString() + "," + renta.getFechaDevolucion().toString() + ","
                    + renta.getMonto() + "," + (renta.isPeliculaDevuelta() ? "1" : "0"));
        }
        return listaStringRentas;
    }

    // Getter y setters
    /**
     * Obtiene el ID siguiente disponible.
     * @return El ID siguiente.
     */
    public int getIdSiguiente() {
        return idSiguiente;
    }

    /**
     * Establece el ID siguiente disponible.
     * @param idSiguiente El ID siguiente a establecer.
     */
    public void setIdSiguiente(int idSiguiente) {
        this.idSiguiente = idSiguiente;
    }
}