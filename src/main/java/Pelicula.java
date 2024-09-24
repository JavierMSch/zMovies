public class Pelicula {
    private int id;
    private String titulo;
    private String genero;
    private int precioSemanal;
    private boolean activa;

    public Pelicula(int id, String titulo, String genero, int precioSemanal) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.precioSemanal = precioSemanal;
        this.activa = true;
    }

    public String obtenerDetalles() {
        return "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Género: " + genero + "\n" +
                "Precio semanal: $" + precioSemanal;
    }

    public int calcularPrecio(int semanas) {
        return precioSemanal * semanas;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Género: " + genero + ", Precio semanal: $" + precioSemanal;
    }

    // Getter y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getPrecioSemanal() {
        return precioSemanal;
    }

    public void setPrecioSemanal(int precioSemanal) {
        this.precioSemanal = precioSemanal;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
