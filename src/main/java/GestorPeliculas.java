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
            if (pelicula.isActiva()) {
                cadenaPeliculas.append(pelicula).append("\n");
                hayPeliculas = true;
            }
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

    public String obtenerListaGeneros() {
        StringBuilder cadenaGeneros = new StringBuilder();
        boolean hayGeneros = false;
        for (Genero genero: generosPeliculas) {
            cadenaGeneros.append(genero.getNombre()).append("\n");
            hayGeneros = true;
        }
        if (!hayGeneros) {
            return null;
        }
        return cadenaGeneros.toString();
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

    public boolean editarPelicula(String titulo, String nuevoTitulo, String nuevoGenero, int nuevoPrecio) {
        Pelicula pelicula = obtenerPelicula(titulo);
        if (pelicula == null) {
            return false;
        }

        if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
            pelicula.setTitulo(nuevoTitulo);
        }
        if (nuevoGenero != null && !nuevoGenero.isEmpty()) {
            if (!existeGenero(nuevoGenero)) {
                agregarGenero(nuevoGenero);
            }
            String antiguoGenero = pelicula.getGenero();
            Genero genero = obtenerGenero(nuevoGenero);
            pelicula.setGenero(genero.getNombre());
            genero.agregarPelicula(pelicula);
            obtenerGenero(antiguoGenero).eliminarPelicula(pelicula.getTitulo());
        }
        if (nuevoPrecio >= 0) {
            pelicula.setPrecioSemanal(nuevoPrecio);
        }
        return true;
    }

    public boolean eliminarPelicula(String titulo) {
        Pelicula pelicula = obtenerPelicula(titulo);
        if (pelicula != null) {
            Genero genero = obtenerGenero(pelicula.getGenero());
            genero.eliminarPelicula(titulo);
            pelicula.setActiva(false);
            return true;
        }
        return false;
    }

    public boolean editarGenero(String nombreGenero, String nuevoNombre) {
        Genero genero = obtenerGenero(nombreGenero);

        if (genero != null) {
            genero.editarGenero(nuevoNombre);
            return true;
        }
        return false;
    }

    // Getter y setters
    public int getIdPeliculaSiguiente() {
        return idPeliculaSiguiente;
    }

    public void setIdPeliculaSiguiente(int idPeliculaSiguiente) {
        this.idPeliculaSiguiente = idPeliculaSiguiente;
    }
}
