import java.util.List;
import java.util.ArrayList;

public class GestorPeliculas {
    private List<Genero> generosPeliculas;
    private List<Pelicula> listaPeliculas;

    public GestorPeliculas() {
        generosPeliculas = new ArrayList<>();
        listaPeliculas = new ArrayList<>();
        datosTest();
    }

    private void datosTest() {
        Genero generoTest = new Genero("Drama");
        Genero generoTest2 = new Genero("Romance");
        agregarGenero(generoTest);
        agregarGenero(generoTest2);

        Pelicula peliculaTest = new Pelicula(1, "El Padrino", 100, 10);
        agregarPelicula(generoTest, peliculaTest);
        peliculaTest = new Pelicula(2, "El Padrino II", 100, 10);
        agregarPelicula(generoTest, peliculaTest);
        peliculaTest = new Pelicula(3, "Titanic", 100, 10);
        agregarPelicula(generoTest2, peliculaTest);
    }

    public String obtenerListaPeliculas() {
        StringBuilder cadenaPeliculas = new StringBuilder();
        for (Pelicula pelicula: listaPeliculas) {
            cadenaPeliculas.append(pelicula).append("\n");
        }
        return cadenaPeliculas.toString();
    }

    private Genero obtenerGenero(String nombre) {
        for (Genero gen : generosPeliculas) {
            if (gen.getNombre().equals(nombre)) {
                return gen;
            }
        }
        return null;
    }

    public boolean agregarGenero(Genero genero) {
        if (genero == null || obtenerGenero(genero.getNombre()) != null) {
            return false;
        }
        generosPeliculas.add(genero);
        return true;
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

    public boolean agregarPelicula(Genero genero, Pelicula pelicula) {
        if (pelicula == null || obtenerPelicula(pelicula.getId()) != null) {
            return false;
        }
        genero.agregarPelicula(pelicula);
        listaPeliculas.add(pelicula);
        return true;
    }

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
