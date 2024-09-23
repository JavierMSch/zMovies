import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GestorRentas {
    private List<Renta> listaRentas;
    private int idSiguiente;

    public GestorRentas() {
        listaRentas = new ArrayList<>();
        idSiguiente = 1;
    }

    public boolean agregarRenta(Renta renta) {
        if (renta == null || listaRentas.contains(renta)) {
            return false;
        }
        listaRentas.add(renta);
        idSiguiente++;
        return true;
    }

    public Renta obtenerRenta(int id) {
        for (Renta renta : listaRentas) {
            if (renta.getId() == id) {
                return renta;
            }
        }
        return null;
    }

    public String obtenerListaRentas() {
        StringBuilder cadenaRentas = new StringBuilder();
        for (Renta renta : listaRentas) {
            cadenaRentas.append(renta).append("\n");
        }
        return cadenaRentas.toString();
    }

    public String obtenerListaRentasPendientes() {
        StringBuilder cadenaRentas = new StringBuilder();
        boolean hayPendientes = false;
        for (Renta renta : listaRentas) {
            if (renta.estaPendiente()) {
                cadenaRentas.append(renta).append("\n");
                hayPendientes = true;
            }
        }
        if (!hayPendientes) {
            return null;
        }
        return cadenaRentas.toString();
    }

    public String obtenerListaRentasPendientes(String rut) {
        StringBuilder cadenaRentas = new StringBuilder();
        boolean hayPendientes = false;
        for (Renta renta : listaRentas) {
            if (renta.esCliente(rut) && renta.estaPendiente()) {
                cadenaRentas.append(renta).append("\n");
                hayPendientes = true;
            }
        }
        if (!hayPendientes) {
            return null;
        }
        return cadenaRentas.toString();
    }

    public void devolverPelicula(int id) {
        obtenerRenta(id).marcarDevuelta();
    }

    public Pelicula peliculaMasVendidaGenero(String nombreGenero) {
        if (nombreGenero == null) {
            return null;
        }

        Map<Pelicula, Integer> contadorPeliculas = new HashMap<>();
        for (Renta renta : listaRentas) {
            if (renta.getPelicula().getGenero().equals(nombreGenero)) {
                contadorPeliculas.put(renta.getPelicula(), contadorPeliculas.getOrDefault(renta.getPelicula(), 0) + 1);
            }
        }

        Pelicula masVendida = null;
        int max = 0;
        for (Pelicula key : contadorPeliculas.keySet()) {
            if (masVendida == null || contadorPeliculas.get(key) > max) {
                masVendida = key;
                max = contadorPeliculas.get(key);
            }
        }

        return masVendida;
    }

    public boolean peliculaTieneRentasActivas(Pelicula pelicula) {
        for (Renta renta: listaRentas) {
            if (renta.getPelicula() == pelicula) {
                return true;
            }
        }
        return false;
    }

    // Getter y setters
    public int getIdSiguiente() {
        return idSiguiente;
    }

    public void setIdSiguiente(int idSiguiente) {
        this.idSiguiente = idSiguiente;
    }
}