public class VideoClub {
    private GestorClientes gestorClientes;
    private GestorPeliculas gestorPeliculas;
    private GestorRentas gestorRentas;

    public VideoClub() {
        gestorClientes = new GestorClientes();
        gestorPeliculas = new GestorPeliculas();
        gestorRentas = new GestorRentas();
    }

    public boolean existeGenero(String genero) {
        return gestorPeliculas.existeGenero(genero);
    }

    public void agregarGenero(String nombre) {
        gestorPeliculas.agregarGenero(nombre);
    }

    public void agregarPelicula(String titulo, String genero, int precioSemanal) {
        gestorPeliculas.agregarPelicula(titulo, genero, precioSemanal);
    }

    public String obtenerListaPeliculas() {
        return gestorPeliculas.obtenerListaPeliculas();
    }

    public String obtenerListaPeliculas(String nombreGenero) {
        return gestorPeliculas.obtenerListaPeliculas(nombreGenero);
    }

    public boolean existeCliente(String rut) {
        return gestorClientes.existeCliente(rut);
    }

    public void agregarCliente(String rut, String nombreApellido, String correo, String telefono) {
        gestorClientes.agregarCliente(rut, nombreApellido, correo, telefono);
    }

    public void rentarPelicula(String rut, String tituloPelicula, int semanas) {
        Renta renta = new Renta(gestorRentas.getIdSiguiente(), gestorClientes.obtenerCliente(rut),
                gestorPeliculas.obtenerPelicula(tituloPelicula), semanas);
        gestorRentas.agregarRenta(renta);
        gestorClientes.agregarRenta(rut, renta);
    }

    public int precioPelicula(String titulo, int semanas) {
        return gestorPeliculas.precioPelicula(titulo, semanas);
    }

    public boolean existePelicula(String titulo) {
        return gestorPeliculas.existePelicula(titulo);
    }

    public String obtenerListaRentasPendientes() {
        return gestorRentas.obtenerListaRentasPendientes();
    }

    public String obtenerListaRentasPendientes(String rut) {
        return gestorRentas.obtenerListaRentasPendientes(rut);
    }

    public void devolverPelicula(int id) {
        gestorRentas.devolverPelicula(id);
    }

    public String recomendarPelicula(String rut) {
        String nombreGeneroFavorito = gestorClientes.obtenerNombreGeneroFavorito(rut);
        if (nombreGeneroFavorito == null) {
            return null;
        }

        Pelicula pelicula = gestorRentas.peliculaMasVendidaGenero(nombreGeneroFavorito);
        if (pelicula == null) {
            return null;
        }

        return pelicula.toString();
    }

    // Getter y setters
    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    public void setGestorClientes(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;
    }

    public GestorPeliculas getGestorPeliculas() {
        return gestorPeliculas;
    }

    public void setGestorPeliculas(GestorPeliculas gestorPeliculas) {
        this.gestorPeliculas = gestorPeliculas;
    }

    public GestorRentas getGestorRentas() {
        return gestorRentas;
    }

    public void setGestorRentas(GestorRentas gestorRentas) {
        this.gestorRentas = gestorRentas;
    }
}
