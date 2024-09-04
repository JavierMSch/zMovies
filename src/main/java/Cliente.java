import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Cliente {
    private String rut;
    private String nombreApellidos;
    private String correo;
    private String telefono;
    private List<Renta> historialRentas;

    public Cliente(String rut, String nombre, String correo, String telefono) {
        this.rut = rut;
        this.nombreApellidos = nombre;
        this.correo = correo;
        this.telefono = telefono;
        historialRentas = new ArrayList<>();
    }

    public void agregarRentaAHistorial(Renta Renta) {
        historialRentas.add(Renta);
    }

    public String nombreGeneroFavorito() {
        Map<String, Integer> contadorGeneros = new HashMap<>();
        for (Renta renta : historialRentas) {
            String genero = renta.getPelicula().getGenero();
            contadorGeneros.put(genero, contadorGeneros.getOrDefault(genero, 0) + 1);
        }

        String generoFavorito = null;
        int max = 0;
        for (String key : contadorGeneros.keySet()) {
            if (generoFavorito == null || contadorGeneros.get(key) > max) {
                generoFavorito = key;
                max = contadorGeneros.get(key);
            }
        }
        return generoFavorito;
    }

    // Getter y setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Renta> getHistorialRentas() {
        return historialRentas;
    }

    public void setHistorialRentas(List<Renta> historialRentas) {
        this.historialRentas = historialRentas;
    }
}