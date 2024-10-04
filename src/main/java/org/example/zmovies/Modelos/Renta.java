package org.example.zmovies.Modelos;

import java.time.LocalDate;

/**
 * La clase Renta representa una transacción de renta de una película por parte de un cliente.
 */
public class Renta {
    private int id;
    private Cliente cliente;
    private Pelicula pelicula;
    private int semanasRentadas;
    private LocalDate fecha;
    private LocalDate fechaDevolucion;
    private int monto;
    private boolean peliculaDevuelta;

    /**
     * Constructor de la clase Renta.
     *
     * @param id Identificador de la renta.
     * @param cliente Cliente que renta la película.
     * @param pelicula Película rentada.
     * @param semanasRentadas Cantidad de semanas que se renta la película.
     */
    public Renta(int id, Cliente cliente, Pelicula pelicula, int semanasRentadas) {
        this.id = id;
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.semanasRentadas = semanasRentadas;
        this.fecha = LocalDate.now();
        this.fechaDevolucion = fecha.plusDays((long) semanasRentadas * 7);
        this.monto = pelicula.calcularPrecio(semanasRentadas);
        peliculaDevuelta = false;
    }

    /**
     * Constructor de la clase Renta con parámetros adicionales.
     *
     * @param id Identificador de la renta.
     * @param cliente Cliente que renta la película.
     * @param pelicula Película rentada.
     * @param semanasRentadas Cantidad de semanas que se renta la película.
     * @param fecha Fecha en que se realizó la renta.
     * @param fechaDevolucion Fecha en que se debe devolver la película.
     * @param monto Monto total de la renta.
     * @param peliculaDevuelta Indica si la película ha sido devuelta.
     */
    public Renta(int id, Cliente cliente, Pelicula pelicula, int semanasRentadas,
                 LocalDate fecha, LocalDate fechaDevolucion, int monto, boolean peliculaDevuelta) {
        this.id = id;
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.semanasRentadas = semanasRentadas;
        this.fecha = fecha;
        this.fechaDevolucion = fechaDevolucion;
        this.monto = monto;
        this.peliculaDevuelta = peliculaDevuelta;
    }

    /**
     * Indica si la película aún no ha sido devuelta.
     *
     * @return true si la película no ha sido devuelta, false en caso contrario.
     */
    public boolean estaPendiente() {
        return !peliculaDevuelta;
    }

    /**
     * Indica si la renta corresponde a un cliente específico.
     *
     * @param rut Rut del cliente.
     * @return true si la renta corresponde al cliente, false en caso contrario.
     */
    public boolean esCliente(String rut) {
        return cliente.getRut().equals(rut);
    }

    /**
     * Marca la película como devuelta.
     */
    public void marcarDevuelta() {
        setPeliculaDevuelta(true);
    }

    /**
     * Devuelve una representación en String de la renta.
     *
     * @return String con la información de la renta.
     */
    @Override
    public String toString() {
        return "ID: " + id + " - RUT: " + cliente.getRut() + " - Película: " + pelicula.getTitulo() + " - Monto Boleta: $" + monto + " - Fecha Renta: " + fecha + " - Fecha devolución: " + fechaDevolucion;
    }

    // Getters y setters

    /**
     * Obtiene el identificador de la renta.
     *
     * @return ID de la renta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la renta.
     *
     * @param id ID de la renta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el cliente que realizó la renta.
     *
     * @return Cliente de la renta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realizó la renta.
     *
     * @param cliente Cliente de la renta.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la película rentada.
     *
     * @return Película rentada.
     */
    public Pelicula getPelicula() {
        return pelicula;
    }

    /**
     * Establece la película rentada.
     *
     * @param pelicula Película rentada.
     */
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * Obtiene la cantidad de semanas que se rentó la película.
     *
     * @return Cantidad de semanas rentadas.
     */
    public int getSemanasRentadas() {
        return semanasRentadas;
    }

    /**
     * Establece la cantidad de semanas que se rentó la película.
     *
     * @param semanasRentadas Cantidad de semanas rentadas.
     */
    public void setSemanasRentadas(int semanasRentadas) {
        this.semanasRentadas = semanasRentadas;
    }

    /**
     * Obtiene la fecha en que se realizó la renta.
     *
     * @return Fecha de la renta.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó la renta.
     *
     * @param fecha Fecha de la renta.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha en que se debe devolver la película.
     *
     * @return Fecha de devolución.
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Establece la fecha en que se debe devolver la película.
     *
     * @param fechaDevolucion Fecha de devolución.
     */
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * Obtiene el monto total de la renta.
     *
     * @return Monto total de la renta.
     */
    public int getMonto() {
        return monto;
    }

    /**
     * Establece el monto total de la renta.
     *
     * @param monto Monto total de la renta.
     */
    public void setMonto(int monto) {
        this.monto = monto;
    }

    /**
     * Indica si la película ha sido devuelta.
     *
     * @return true si la película ha sido devuelta, false en caso contrario.
     */
    public boolean isPeliculaDevuelta() {
        return peliculaDevuelta;
    }

    /**
     * Establece si la película ha sido devuelta.
     *
     * @param peliculaDevuelta true si la película ha sido devuelta, false en caso contrario.
     */
    public void setPeliculaDevuelta(boolean peliculaDevuelta) {
        this.peliculaDevuelta = peliculaDevuelta;
    }
}