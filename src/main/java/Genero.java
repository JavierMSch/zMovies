import java.util.Map;
import java.util.HashMap;

public class Genero {
    private String nombre;
    private Map<String, Pelicula> peliculasTitulo;

    public Genero(String nombre) {
        this.nombre = nombre;
        peliculasTitulo = new HashMap<>();
    }

    public Pelicula obtenerPelicula(String titulo) {
        return peliculasTitulo.get(titulo.toUpperCase());
    }

    public boolean agregarPelicula(Pelicula pelicula) {
        if (pelicula == null || peliculasTitulo.containsKey(pelicula.getTitulo())) {
            return false;
        }
        peliculasTitulo.put(pelicula.getTitulo().toUpperCase(), pelicula);
        return true;
    }

    public String obtenerListaPeliculas() {
        StringBuilder cadenaPeliculas = new StringBuilder();
        boolean hayPeliculas = false;
        for (Pelicula pelicula: peliculasTitulo.values()) {
            cadenaPeliculas.append(pelicula).append("\n");
            hayPeliculas = true;
        }
        if (!hayPeliculas) {
            return null;
        }
        return cadenaPeliculas.toString();
    }

    public Pelicula eliminarPelicula(String titulo) {
        return peliculasTitulo.remove(obtenerPelicula(titulo).getTitulo());
    }

    public void editarGenero(String nuevoNombre) {
        String nuevoNombreGenero = nuevoNombre.toUpperCase();

        setNombre(nuevoNombreGenero);
        for (Pelicula pelicula: peliculasTitulo.values()) {
            pelicula.setGenero(nuevoNombreGenero);
        }
    }

    public void moverPeliculas(Genero recibe) {
        for (Pelicula pelicula: peliculasTitulo.values()) {
            recibe.agregarPelicula(pelicula);
            pelicula.setGenero(recibe.getNombre());
        }
    }

    // Getter y setters
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre.toUpperCase(); }
}
