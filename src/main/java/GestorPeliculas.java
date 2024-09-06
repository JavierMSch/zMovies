import java.util.List;
import java.util.ArrayList;

public class GestorPeliculas {
    private List<Genero> generosPeliculas;
    private List<Pelicula> listaPeliculas;
    private int idPeliculaSiguiente;

    public GestorPeliculas() {
        generosPeliculas = new ArrayList<>();
        listaPeliculas = new ArrayList<>();
        idPeliculaSiguiente = 1;
    }

    public String obtenerListaPeliculas() {
        StringBuilder cadenaPeliculas = new StringBuilder();
        boolean hayPeliculas = false;
        for (Pelicula pelicula: listaPeliculas) {
            cadenaPeliculas.append(pelicula).append("\n");
            hayPeliculas = true;
        }
        if (!hayPeliculas) {
            return null;
        }
        return cadenaPeliculas.toString();
    }

    public String obtenerListaPeliculas(String nombreGenero) {
        Genero genero = obtenerGenero(nombreGenero);
        if (genero == null) {
            return null;
        }
        return genero.obtenerListaPeliculas();
    }

    public Genero obtenerGenero(String nombre) {
        for (Genero gen : generosPeliculas) {
            if (gen.getNombre().equalsIgnoreCase(nombre)) {
                return gen;
            }
        }
        return null;
    }

    public boolean agregarGenero(String nombreGenero) {
        if (!existeGenero(nombreGenero)) {
            Genero genero = new Genero(nombreGenero.toUpperCase());
            generosPeliculas.add(genero);
        }
        return false;
    }

    public Pelicula obtenerPelicula(String titulo) {
        for (Genero gen : generosPeliculas) {
            Pelicula peliculaBuscada = gen.obtenerPelicula(titulo);
            if (peliculaBuscada != null) {
                return peliculaBuscada;
            }
        }
        return null;
    }

    public Pelicula obtenerPelicula(int id) {
        for (Genero gen : generosPeliculas) {
            Pelicula peliculaBuscada = gen.obtenerPelicula(id);
            if (peliculaBuscada != null) {
                return peliculaBuscada;
            }
        }
        return null;
    }

    public boolean existeGenero(String nombreGenero) {
        if (obtenerGenero(nombreGenero) == null) {
            return false;
        }
        return true;
    }

    public boolean agregarPelicula(String titulo, String nombreGenero, int precioSemanal) {
        Genero genero = obtenerGenero(nombreGenero);
        if (genero == null) {
            return false;
        }

        Pelicula pelicula = new Pelicula(idPeliculaSiguiente, titulo.toUpperCase(),
                nombreGenero.toUpperCase(), precioSemanal);
        genero.agregarPelicula(pelicula);
        listaPeliculas.add(pelicula);
        idPeliculaSiguiente++;
        return true;
    }

    public boolean existePelicula(String titulo) {
        for (Genero genero: generosPeliculas) {
            if (genero.obtenerPelicula(titulo) != null) {
                return true;
            }
        }
        return false;
    }

    public int precioPelicula(String titulo, int semanas) {
        return obtenerPelicula(titulo).calcularPrecio(semanas);
    }

    public int precioPelicula(String titulo) {
        return obtenerPelicula(titulo).getPrecioSemanal();
    }

    public String obtenerDetallesPelicula(String titulo) {
        if (!existePelicula(titulo)) {
            return null;
        }
        return obtenerPelicula(titulo).obtenerDetalles();
    }

    // Getter y setters
    public List<Genero> getGenerosPeliculas() {
        return generosPeliculas;
    }

    public void setGenerosPeliculas(List<Genero> generosPeliculas) {
        this.generosPeliculas = generosPeliculas;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
}
