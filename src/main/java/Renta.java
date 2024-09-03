import java.time.LocalDate;

public class Renta {
    private int id;
    private Cliente cliente;
    private int idPelicula;
    private int semanasRentadas;
    private LocalDate fecha;
    private LocalDate fechaDevolucion;
    private int monto;
    private boolean peliculaDevuelta;

    public Renta(int id, Cliente cliente, int idPelicula, int semanasRentadas, int monto) {
        this.id = id;
        this.cliente = cliente;
        this.idPelicula = idPelicula;
        this.semanasRentadas = semanasRentadas;
        this.fecha = LocalDate.now();
        this.fechaDevolucion = fecha.plusDays((long) semanasRentadas * 7);
        this.monto = monto;
        peliculaDevuelta = false;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", RUT: " + cliente.getRut() + ", Monto Boleta: $" + monto + ", Fecha: " + fecha;
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

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
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