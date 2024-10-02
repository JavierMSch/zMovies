package org.example.zmovies.Modelos;

import java.time.LocalDate;

public class Renta {
    private int id;
    private Cliente cliente;
    private Pelicula pelicula;
    private int semanasRentadas;
    private LocalDate fecha;
    private LocalDate fechaDevolucion;
    private int monto;
    private boolean peliculaDevuelta;

    public Renta(int id, Cliente cliente, Pelicula pelicula, int semanasRentadas) {
        this.id = id;
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.semanasRentadas = semanasRentadas;
        this.fecha = LocalDate.now();
        this.fechaDevolucion = fecha.plusDays((long) semanasRentadas * 7);
        this.monto = pelicula.getPrecioSemanal() * semanasRentadas;
        peliculaDevuelta = false;
    }

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

    public boolean estaPendiente() {
        return !peliculaDevuelta;
    }

    public boolean esCliente(String rut) {
        return cliente.getRut().equals(rut);
    }

    public void marcarDevuelta() {
        setPeliculaDevuelta(true);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + "Película: " + pelicula.getTitulo() + ", RUT: " + cliente.getRut() + ", Monto Boleta: $" + monto + ", Fecha devolución: " + fechaDevolucion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public int getSemanasRentadas() {
        return semanasRentadas;
    }

    public void setSemanasRentadas(int semanasRentadas) {
        this.semanasRentadas = semanasRentadas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public boolean isPeliculaDevuelta() {
        return peliculaDevuelta;
    }

    public void setPeliculaDevuelta(boolean peliculaDevuelta) {
        this.peliculaDevuelta = peliculaDevuelta;
    }
}