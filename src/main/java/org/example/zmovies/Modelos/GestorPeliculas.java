package org.example.zmovies.Modelos;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.zmovies.Exceptions.ReportePlanillaException;
import org.example.zmovies.Exceptions.ReporteTextoException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class GestorPeliculas {
    private List<Genero> generosPeliculas;
    private List<Pelicula> listaPeliculas;

    public GestorPeliculas() {
        generosPeliculas = new ArrayList<>();
        listaPeliculas = new ArrayList<>();
    }

    public String obtenerStringListaPeliculas() {
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

    public String obtenerStringListaPeliculas(String nombreGenero) {
        Genero genero = obtenerGenero(nombreGenero);
        if (genero == null) {
            return null;
        }
        return genero.obtenerListaPeliculas();
    }

    public String obtenerStringListaGeneros() {
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

        Pelicula pelicula = new Pelicula(titulo.toUpperCase(), nombreGenero.toUpperCase(), precioSemanal);
        genero.agregarPelicula(pelicula);
        listaPeliculas.add(pelicula);
        return true;
    }

    public boolean agregarPelicula(String titulo, String nombreGenero, int precioSemanal, boolean activa) {
        Pelicula pelicula = new Pelicula(titulo.toUpperCase(), nombreGenero.toUpperCase(), precioSemanal, activa);
        if (pelicula.isActiva()) {
            // Se hace la verificación antes de buscar el género, ya que si la película fué eliminada
            // puede ocurrir que el género al que pertenecía se eliminó luego.
            Genero genero = obtenerGenero(nombreGenero);
            if (genero == null) {
                return false;
            }
            genero.agregarPelicula(pelicula);
        }
        listaPeliculas.add(pelicula);
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
            obtenerGenero(pelicula.getGenero()).eliminarPelicula(pelicula.getTitulo());
            pelicula.setTitulo(nuevoTitulo.toUpperCase());
            obtenerGenero(pelicula.getGenero()).agregarPelicula(pelicula);
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

    public boolean eliminarGenero(String nombreGenero) {
        Genero genero = obtenerGenero(nombreGenero);
        if (genero != null && !genero.getNombre().equalsIgnoreCase("SIN GENERO")) {
            Genero generoEspecial = obtenerGenero("SIN GENERO");
            genero.moverPeliculas(generoEspecial);
            generosPeliculas.remove(genero);
            return true;
        }
        return false;
    }

    public String obtenerListaPeliculasParaCSV() {
        StringBuilder cadena = new StringBuilder();
        cadena.append("Titulo,Género,Precio Semanal\n");
        for (Pelicula pelicula: listaPeliculas) {
            if (pelicula.isActiva()) {
                cadena.append(pelicula.getTitulo()).append(",")
                        .append(pelicula.getGenero()).append(",")
                        .append(pelicula.getPrecioSemanal()).append("\n");
            }
        }
        return cadena.toString();
    }

    public void generarReporteTexto() throws ReporteTextoException {
        String nombreArchivo = "reportePeliculas.txt";
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) {
            writer.println("Titulo,Género,Precio Semanal");
            for (Pelicula pelicula: listaPeliculas) {
                if (pelicula.isActiva()) {
                    writer.printf("%s,%s,%d\n", pelicula.getTitulo(), pelicula.getGenero(), pelicula.getPrecioSemanal());
                }
            }
        } catch (IOException e) {
            throw new ReporteTextoException("Error al generar el reporte de películas en formato texto", e);
        }
    }

    public void generarReportePlanilla() throws ReportePlanillaException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet planilla = workbook.createSheet("Películas");

            Row header = planilla.createRow(0);
            header.createCell(0).setCellValue("Título");
            header.createCell(1).setCellValue("Género");
            header.createCell(2).setCellValue("Precio Semanal");

            planilla.setColumnWidth(0, 8000);
            planilla.setColumnWidth(1, 6000);
            planilla.setColumnWidth(2, 4000);

            int numeroFila = 1;
            for (Pelicula pelicula: listaPeliculas) {
                Row fila = planilla.createRow(numeroFila);

                fila.createCell(0).setCellValue(pelicula.getTitulo());
                fila.createCell(1).setCellValue(pelicula.getGenero());
                fila.createCell(2).setCellValue(pelicula.getPrecioSemanal());
                numeroFila++;
            }

            String nombreArchivo = "planillaPeliculas.xlsx";
            try (FileOutputStream archivoSalida = new FileOutputStream(nombreArchivo)) {
                workbook.write(archivoSalida);
            }

        } catch (IOException e) {
            throw new ReportePlanillaException("Error al generar el reporte de películas en formato planilla", e);
        }
    }

    public List<String> obtenerListaStringGeneros() {
        List<String> listaStringGeneros = new ArrayList<>();
        for (Genero genero: generosPeliculas) {
            listaStringGeneros.add(genero.getNombre());
        }
        return listaStringGeneros;
    }

    public List<String> obtenerListaStringPeliculas() {
        List<String> listaStringPeliculas = new ArrayList<>();
        for (Pelicula pelicula: listaPeliculas) {
            listaStringPeliculas.add(pelicula.getTitulo() + "," + pelicula.getGenero()
                    + "," + pelicula.getPrecioSemanal() + "," + (pelicula.isActiva() ? "1" : "0"));
        }
        return listaStringPeliculas;
    }

    public Pelicula obtenerPeliculaActivaOInactiva(String titulo) {
        for (Pelicula pelicula: listaPeliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }
}
