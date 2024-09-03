public class Pelicula {
    private int id;
    private String titulo;
    private int precio;
    private int stockDisponible;
    private int cantidadTotal;

    public Pelicula(int id, String titulo, int precio, int cantidadTotal) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.cantidadTotal = cantidadTotal;
        this.stockDisponible = cantidadTotal;
    }

    public boolean rentar() {
        if (stockDisponible > 0) {
            stockDisponible--;
            return true;
        }
        return false;
    }

    public void devolver() {
        stockDisponible++;
    }

    public void aumentarCantidadTotal() {
        cantidadTotal++;
        stockDisponible++;
    }

    public void aumentarCantidadTotal(int cantidad) {
        cantidadTotal += cantidad;
        stockDisponible += cantidad;
    }

    public boolean rentarPelicula() {
        if (this.stockDisponible > 0) {
            this.stockDisponible--;
            return true;
        }
        return false;
    }

    public String obtenerDetalles() {
        return "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Precio semanal: " + precio + "\n" +
                "Stock disponible: " + stockDisponible + "\n" +
                "Unidades totales: " + cantidadTotal + "\n";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Precio semanal: $" + precio + ", Stock: " + stockDisponible;
    }

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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
}
