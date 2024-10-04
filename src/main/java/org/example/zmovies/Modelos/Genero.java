package org.example.zmovies.Modelos;

import java.util.Map;
import java.util.HashMap;

/**
 * La clase Genero representa un género de películas en el video club. Almacena un nombre y un mapa de películas
 * que pertenecen a ese género.
 */
public class Genero {
    private String nombre;
    private Map<String, Pelicula> peliculasTitulo;

    /**
     * Constructor que inicializa un nuevo género con su nombre.
     *
     * @param nombre el nombre del género
     */
    public Genero(String nombre) {
        this.nombre = nombre;
        peliculasTitulo = new HashMap<>();
    }

    /**
     * Devuelve la película con el título especificado que pertenece a este género.
     *
     * @param titulo el título de la película que se desea obtener
     * @return la película con el título especificado, o {@code null} si no existe
     */
    public Pelicula obtenerPelicula(String titulo) {
        return peliculasTitulo.get(titulo.toUpperCase());
    }

    /**
     * Agrega una película a este género.
     *
     * @param pelicula la película que se desea agregar
     * @return {@code true} si la película se agregó correctamente, {@code false} si la película es {@code null} o ya existe
     */
    public boolean agregarPelicula(Pelicula pelicula) {
        if (pelicula == null || peliculasTitulo.containsKey(pelicula.getTitulo())) {
            return false;
        }
        peliculasTitulo.put(pelicula.getTitulo().toUpperCase(), pelicula);
        return true;
    }

    /**
     * Devuelve una cadena con los títulos de las películas que pertenecen a este género.
     *
     * @return una cadena con los títulos de las películas, o {@code null} si no hay películas
     */
    public String obtenerListaPeliculas() {
        StringBuilder cadenaPeliculas = new StringBuilder();
        boolean hayPeliculas = false;
        for (Pelicula pelicula: peliculasTitulo.values()) {
            cadenaPeliculas.append(pelicula.getTitulo()).append("\n");
            hayPeliculas = true;
        }
        if (!hayPeliculas) {
            return null;
        }
        return cadenaPeliculas.toString();
    }

    /**
     * Elimina la película con el título especificado que pertenece a este género.
     *
     * @param titulo el título de la película que se desea eliminar
     * @return la película eliminada, o {@code null} si no existe
     */
    public Pelicula eliminarPelicula(String titulo) {
        return peliculasTitulo.remove(obtenerPelicula(titulo).getTitulo());
    }

    /**
     * Edita el nombre de este género y actualiza el nombre de las películas que pertenecen a él.
     *
     * @param nuevoNombre el nuevo nombre del género
     */
    public void editarGenero(String nuevoNombre) {
        String nuevoNombreGenero = nuevoNombre.toUpperCase();

        setNombre(nuevoNombreGenero);
        for (Pelicula pelicula: peliculasTitulo.values()) {
            pelicula.setGenero(nuevoNombreGenero);
        }
    }

    /**
     * Mueve todas las películas de este género a otro género.
     *
     * @param recibe el género al que se moverán las películas
     */
    public void moverPeliculas(Genero recibe) {
        for (Pelicula pelicula: peliculasTitulo.values()) {
            recibe.agregarPelicula(pelicula);
            pelicula.setGenero(recibe.getNombre());
        }
    }

    // Getter y setters

    /**
     * Obtiene el nombre de este género.
     *
     * @return el nombre del género
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre de este género.
     *
     * @param nombre el nuevo nombre del género
     */
    public void setNombre(String nombre) { this.nombre = nombre.toUpperCase(); }
}
