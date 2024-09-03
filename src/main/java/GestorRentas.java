import java.util.ArrayList;
import java.util.List;

public class GestorRentas {
    private List<Renta> listaRentas;

    public GestorRentas() {
        listaRentas = new ArrayList<>();
    }

    public boolean agregarRenta(Renta renta) {
        if (renta == null || listaRentas.contains(renta)) {
            return false;
        }
        listaRentas.add(renta);
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

    public int obtenerTotalRentas() {
        return listaRentas.size();
    }

    public String obtenerListaRentas() {
        StringBuilder cadenaRentas = new StringBuilder();
        for (Renta renta : listaRentas) {
            cadenaRentas.append(renta).append("\n");
        }
        return cadenaRentas.toString();
    }


    // Getter y setters
    public List<Renta> getListaRentas() {
        return listaRentas;
    }

    public void setListaRentas(List<Renta> listaRentas) {
        this.listaRentas = listaRentas;
    }
}