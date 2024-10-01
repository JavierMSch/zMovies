public class Pelicula {
    private String titulo;
    private String genero;
    private int precioSemanal;
    private boolean activa;

    public Pelicula(String titulo, String genero, int precioSemanal) {
        this.titulo = titulo;
        this.genero = genero;
        this.precioSemanal = precioSemanal;
        this.activa = true;
    }

    public Pelicula(String titulo, String genero, int precioSemanal, boolean activa) {
        this.titulo = titulo;
        this.genero = genero;
        this.precioSemanal = precioSemanal;
        this.activa = activa;
    }

    public String obtenerDetalles() {
        return "Título: " + titulo + "\n" +
                "Género: " + genero + "\n" +
                "Precio semanal: $" + precioSemanal;
    }

    public int calcularPrecio(int semanas) {
        return precioSemanal * semanas;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + ", Género: " + genero + ", Precio semanal: $" + precioSemanal;
    }

    // Getter y setters
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
