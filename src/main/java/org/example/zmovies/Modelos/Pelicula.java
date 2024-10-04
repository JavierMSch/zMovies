package org.example.zmovies.Modelos;

/**
 * La clase Pelicula representa una película que puede ser rentada.
 */
public class Pelicula {
    private String titulo;
    private String genero;
    private int precioSemanal;
    private boolean activa;

    /**
     * Constructor de la clase Pelicula.
     *
     * @param titulo Título de la película.
     * @param genero Género de la película.
     * @param precioSemanal Precio de renta por semana de la película.
     */
    public Pelicula(String titulo, String genero, int precioSemanal) {
        this.titulo = titulo;
        this.genero = genero;
        this.precioSemanal = precioSemanal;
        this.activa = true;
    }

    /**
     * Constructor de la clase Pelicula con parámetro adicional.
     *
     * @param titulo Título de la película.
     * @param genero Género de la película.
     * @param precioSemanal Precio de renta por semana de la película.
     * @param activa Indica si la película está activa.
     */
    public Pelicula(String titulo, String genero, int precioSemanal, boolean activa) {
        this.titulo = titulo;
        this.genero = genero;
        this.precioSemanal = precioSemanal;
        this.activa = activa;
    }

    /**
     * Obtiene los detalles de la película en String.
     *
     * @return Detalles de la película.
     */
    public String obtenerDetalles() {
        return "Título: " + titulo + "\n" +
                "Género: " + genero + "\n" +
                "Precio semanal: $" + precioSemanal;
    }

    /**
     * Calcula el precio total de renta de la película según la cantidad de semanas ingresada.
     *
     * @param semanas Cantidad de semanas que se renta la película.
     * @return Precio total de renta.
     */
    public int calcularPrecio(int semanas) {
        return precioSemanal * semanas;
    }

    /**
     * Devuelve la representación en String de la película.
     *
     * @return Representación en String de la película.
     */
    @Override
    public String toString() {
        return "Título: " + titulo + ", Género: " + genero + ", Precio semanal: $" + precioSemanal;
    }

    // Getter y setters
    /**
     * Obtiene el título de la película.
     *
     * @return Título de la película.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la película.
     *
     * @param titulo Título de la película.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el género de la película.
     *
     * @return Género de la película.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el género de la película.
     *
     * @param genero Género de la película.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el precio de renta por semana de la película.
     *
     * @return Precio de renta por semana.
     */
    public int getPrecioSemanal() {
        return precioSemanal;
    }

    /**
     * Establece el precio de renta por semana de la película.
     *
     * @param precioSemanal Precio de renta por semana.
     */
    public void setPrecioSemanal(int precioSemanal) {
        this.precioSemanal = precioSemanal;
    }

    /**
     * Indica si la película está activa.
     *
     * @return true si la película está activa, false si no.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Establece si la película está activa.
     *
     * @param activa true si la película está activa, false si no.
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
