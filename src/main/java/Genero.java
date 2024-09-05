import java.util.Map;
import java.util.HashMap;

public class Genero {
    private String nombre;
    private Map<Integer, Pelicula> peliculasID;
    private Map<String, Pelicula> peliculasTitulo;

    public Genero(String nombre) {
        this.nombre = nombre;
        peliculasID = new HashMap<>();
        peliculasTitulo = new HashMap<>();
    }

    public Pelicula obtenerPelicula(int id) { return peliculasID.get(id); }

    public Pelicula obtenerPelicula(String titulo) {
        return peliculasTitulo.get(titulo.toUpperCase());
    }

    public boolean agregarPelicula(Pelicula pelicula) {
        if (pelicula == null || peliculasID.containsKey(pelicula.getId())) {
            return false;
        }
        peliculasID.put(pelicula.getId(), pelicula);
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

    public Pelicula eliminarPelicula(int id) {
        peliculasTitulo.remove(peliculasID.get(id).getTitulo());
        return peliculasID.remove(id);
    }

    public Pelicula eliminarPelicula(String titulo) {
        peliculasID.remove(peliculasTitulo.get(titulo).getId());
        return peliculasTitulo.remove(titulo);
    }

    // Getter y setters
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre.toUpperCase(); }

    public Map<Integer, Pelicula> getPeliculasID() {
        return peliculasID;
    }

    public void setPeliculasID(Map<Integer, Pelicula> peliculasID) {
        this.peliculasID = peliculasID;
    }

    public Map<String, Pelicula> getPeliculasTitulo() {
        return peliculasTitulo;
    }

    public void setPeliculasTitulo(Map<String, Pelicula> peliculasTitulo) {
        this.peliculasTitulo = peliculasTitulo;
    }
}
