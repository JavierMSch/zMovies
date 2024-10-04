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

/**
 * Clase que se encarga de gestionar las películas y los géneros del sistema.
 * Permite agregar, editar, eliminar y generar reportes.
 */
public class GestorPeliculas {
    private List<Genero> generosPeliculas; // Lista de géneros de películas.
    private List<Pelicula> listaPeliculas; // Lista de películas.

    /**
     * Constructor de la clase GestorPeliculas.
     * Inicializa las listas de géneros y películas.
     */
    public GestorPeliculas() {
        generosPeliculas = new ArrayList<>();
        listaPeliculas = new ArrayList<>();
    }

    /**
     * Obtiene una lista de los títulos de las películas activas.
     *
     * @return Lista de títulos de películas activas o null si no hay.
     */
    public List<String> obtenerStringListaPeliculas() {
        List<String> listaTitulos = new ArrayList<>();

        // Se recorre la lista de películas y se añaden los títulos de las activas.
        for (Pelicula pelicula: listaPeliculas) {
            if (pelicula.isActiva()) {
                listaTitulos.add(pelicula.getTitulo());
            }
        }

        if (listaTitulos.isEmpty()) {
            return null;
        }
        return listaTitulos;
    }

    /**
     * Obtiene una lista de los títulos de las películas de un género específico.
     *
     * @param nombreGenero Nombre del género.
     * @return Lista de películas del género o null si no hay.
     */
    public String obtenerStringListaPeliculas(String nombreGenero) {
        Genero genero = obtenerGenero(nombreGenero);
        if (genero == null) {
            return null;
        }
        return genero.obtenerListaPeliculas();
    }

    /**
     * Obtiene una lista de los nombres de los géneros.
     *
     * @return Lista de nombres de géneros o null si no hay.
     */
    public List<String> obtenerStringListaGeneros() {
        List<String> listaNombreGeneros = new ArrayList<>();

        for (Genero genero: generosPeliculas) {
            listaNombreGeneros.add(genero.getNombre());
        }

        if (listaNombreGeneros.isEmpty()) {
            return null;
        }
        return listaNombreGeneros;
    }

    /**
     * Busca un género por su nombre.
     *
     * @param nombre El nombre del género.
     * @return El objeto Genero si existe, o null si no.
     */
    public Genero obtenerGenero(String nombre) {
        for (Genero genero : generosPeliculas) {
            if (genero.getNombre().equalsIgnoreCase(nombre)) {
                return genero;
            }
        }
        return null;
    }

    /**
     * Obtiene el género de una película.
     *
     * @param titulo El título de la película.
     * @return El nombre del género de la película o null si no existe.
     */
    public String obtenerGeneroPelicula(String titulo) {
        // Se recorre la lista de géneros y se busca la película.
        for (Genero gen : generosPeliculas) {
            Pelicula pelicula = gen.obtenerPelicula(titulo);

            // Si se encuentra la película, se retorna el nombre del género.
            if (pelicula != null) {
                return gen.getNombre();
            }
        }
        return null;
    }

    /**
     * Agrega un género a la lista de géneros.
     *
     * @param nombreGenero El nombre del género.
     * @return true si se agregó, false si ya existe.
     */
    public boolean agregarGenero(String nombreGenero) {
        if (!existeGenero(nombreGenero)) {
            Genero genero = new Genero(nombreGenero.toUpperCase());
            generosPeliculas.add(genero);
        }
        return false;
    }

    /**
     * Obtiene una película por su título.
     *
     * @param titulo El título de la película.
     * @return El objeto Pelicula si existe, o null si no.
     */
    public Pelicula obtenerPelicula(String titulo) {
        for (Genero genero : generosPeliculas) {
            // Se busca la película en cada género.
            Pelicula peliculaBuscada = genero.obtenerPelicula(titulo);

            // Si se encuentra, se retorna.
            if (peliculaBuscada != null) {
                return peliculaBuscada;
            }
        }
        return null;
    }

    /**
     * Verifica si un género existe.
     *
     * @param nombreGenero El nombre del género.
     * @return true si existe, false si no.
     */
    public boolean existeGenero(String nombreGenero) {
        if (obtenerGenero(nombreGenero) == null) {
            return false;
        }
        return true;
    }

    /**
     * Agrega una película a la lista de películas.
     * Si la película ya existe, se activa y se actualizan los datos.
     * Si el género no existe, se crea.
     *
     * @param titulo El título de la película.
     * @param nombreGenero El nombre del género de la película.
     * @param precioSemanal El precio semanal de la película.
     * @return true si se agregó, false si no.
     */
    public boolean agregarPelicula(String titulo, String nombreGenero, int precioSemanal) {
        Pelicula pelicula = obtenerPeliculaActivaOInactiva(titulo);
        if (pelicula != null) {
            pelicula.setActiva(true);
            pelicula.setGenero(nombreGenero.toUpperCase());
            pelicula.setPrecioSemanal(precioSemanal);
        } else {
            pelicula = new Pelicula(titulo.toUpperCase(), nombreGenero.toUpperCase(), precioSemanal);
            listaPeliculas.add(pelicula);
        }

        // Se agrega el género (verificación de existencia dentro del metodo)
        agregarGenero(nombreGenero);
        Genero genero = obtenerGenero(nombreGenero);
        if (genero == null) {
            return false;
        }

        genero.agregarPelicula(pelicula);
        return true;
    }

    /**
     * Agrega una película con un estado activo o inactivo.
     *
     * @param titulo        El título de la película.
     * @param nombreGenero  El nombre del género.
     * @param precioSemanal El precio semanal de la película.
     * @param activa        Estado de la película (activa o inactiva).
     * @return true si se agregó; false si no.
     */
    public boolean agregarPelicula(String titulo, String nombreGenero, int precioSemanal, boolean activa) {
        Pelicula pelicula = new Pelicula(titulo.toUpperCase(), nombreGenero.toUpperCase(), precioSemanal, activa);
        // Se hace la verificación antes de buscar el género, ya que si la película fue eliminada
        // puede ocurrir que el género al que pertenecía se eliminó luego.
        if (pelicula.isActiva()) {
            Genero genero = obtenerGenero(nombreGenero);
            if (genero == null) {
                return false;
            }
            genero.agregarPelicula(pelicula);
        }
        listaPeliculas.add(pelicula);
        return true;
    }

    /**
     * Verifica si una película existe.
     *
     * @param titulo El título de la película.
     * @return true si existe, false si no.
     */
    public boolean existePelicula(String titulo) {
        for (Genero genero: generosPeliculas) {
            if (genero.obtenerPelicula(titulo) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el precio semanal de una película.
     *
     * @param titulo El título de la película.
     * @return El precio semanal o -1 si no existe.
     */
    public int precioPelicula(String titulo) {
        if (!existePelicula(titulo)) {
            return -1;
        }
        return obtenerPelicula(titulo).getPrecioSemanal();
    }

    /**
     * Obtiene los detalles de una película.
     *
     * @param titulo El título de la película.
     * @return Los detalles de la película o null si no existe.
     */
    public String obtenerDetallesPelicula(String titulo) {
        if (!existePelicula(titulo)) {
            return null;
        }
        return obtenerPelicula(titulo).obtenerDetalles();
    }

    /** Edita los detalles de una película.
     *
     * @param titulo El título de la película.
     * @param nuevoTitulo El nuevo título de la película.
     * @param nuevoGenero El nuevo género de la película.
     * @param nuevoPrecio El nuevo precio semanal de la película.
     * @return true si se editó; false si no.
     */
    public boolean editarPelicula(String titulo, String nuevoTitulo, String nuevoGenero, int nuevoPrecio) {
        Pelicula pelicula = obtenerPeliculaActivaOInactiva(titulo);

        // Si la película no existe, retorna false.
        if (pelicula == null) {
            return false;
        }

        // Si el nuevo título es válido, se edita.
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
            obtenerGenero(pelicula.getGenero()).eliminarPelicula(pelicula.getTitulo());
            pelicula.setTitulo(nuevoTitulo.toUpperCase());
            obtenerGenero(pelicula.getGenero()).agregarPelicula(pelicula);
        }
        // Si el nuevo género es válido, se edita.
        if (nuevoGenero != null && !nuevoGenero.isEmpty()) {
            // Si el género no existe, se crea.
            if (!existeGenero(nuevoGenero)) {
                agregarGenero(nuevoGenero);
            }
            String antiguoGenero = pelicula.getGenero();
            Genero genero = obtenerGenero(nuevoGenero);
            pelicula.setGenero(genero.getNombre());
            genero.agregarPelicula(pelicula);
            obtenerGenero(antiguoGenero).eliminarPelicula(pelicula.getTitulo());
        }
        // Si el nuevo precio es válido, se edita.
        if (nuevoPrecio >= 0) {
            pelicula.setPrecioSemanal(nuevoPrecio);
        }
        return true;
    }

    /**
     * Elimina una película.
     *
     * @param titulo El título de la película.
     * @return true si se eliminó; false si no.
     */
    public boolean eliminarPelicula(String titulo) {
        Pelicula pelicula = obtenerPelicula(titulo);
        if (pelicula != null) {
            Genero genero = obtenerGenero(pelicula.getGenero());
            genero.eliminarPelicula(titulo); // Elimina de su género
            pelicula.setActiva(false); // Marca la película como inactiva
            return true;
        }
        return false;
    }

    /** Edita el nombre de un género.
     *
     * @param nombreGenero El nombre del género a editar.
     * @param nuevoNombre El nuevo nombre del género.
     * @return true si se editó; false si no.
     */
    public boolean editarGenero(String nombreGenero, String nuevoNombre) {
        Genero genero = obtenerGenero(nombreGenero);

        if (genero != null) {
            genero.editarGenero(nuevoNombre);
            return true;
        }
        return false;
    }

    /** Elimina un género y mueve sus películas al género especial "SIN GENERO".
     *
     * @param nombreGenero El nombre del género a eliminar.
     * @return true si se eliminó; false si no.
     */
    public boolean eliminarGenero(String nombreGenero) {
        Genero genero = obtenerGenero(nombreGenero);
        // Se verifica si el género es válido para ser eliminado
        if (genero != null && !genero.getNombre().equalsIgnoreCase("SIN GENERO")) {
            Genero generoEspecial = obtenerGenero("SIN GENERO");
            // Se mueven las películas del género a eliminar al género especial
            genero.moverPeliculas(generoEspecial);

            generosPeliculas.remove(genero); // Se elimina el género
            return true;
        }
        return false;
    }

    /**
     * Genera un reporte de las películas en txt con formato separado por comas.
     *
     * @param ruta La ruta donde se guardará el reporte.
     * @throws ReporteTextoException Si hay un error al generar el archivo.
     */
    public void generarReporteTexto(String ruta) throws ReporteTextoException {
        String nombreArchivo = ruta + File.separator + "reportePeliculas.txt";
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) {
            writer.println("Titulo,Género,Precio Semanal");
            for (Pelicula pelicula: listaPeliculas) {
                // Se añaden solo las películas activas
                if (pelicula.isActiva()) {
                    writer.printf("%s,%s,%d\n", pelicula.getTitulo(), pelicula.getGenero(), pelicula.getPrecioSemanal());
                }
            }
        } catch (IOException e) {
            throw new ReporteTextoException("Error al generar el reporte de películas en formato texto", e);
        }
    }

    /**
     * Genera un reporte de películas en formato planilla (Excel).
     *
     * @param ruta Ruta donde se guardará el archivo.
     * @throws ReportePlanillaException Si hay un error al generar el archivo.
     */
    public void generarReportePlanilla(String ruta) throws ReportePlanillaException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet planilla = workbook.createSheet("Películas");

            // Se crea la fila de encabezado
            Row header = planilla.createRow(0);
            header.createCell(0).setCellValue("Título");
            header.createCell(1).setCellValue("Género");
            header.createCell(2).setCellValue("Precio Semanal");

            // Se ajustan los tamaños de las columnas
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

            String nombreArchivo = ruta + File.separator + "reportePeliculas.xlsx";
            try (FileOutputStream archivoSalida = new FileOutputStream(nombreArchivo)) {
                workbook.write(archivoSalida);
            }

        } catch (IOException e) {
            throw new ReportePlanillaException("Error al generar el reporte de películas en formato planilla", e);
        }
    }

    /**
     * Obtiene una lista de nombres de géneros.
     *
     * @return Lista de nombres de géneros.
     */
    public List<String> obtenerListaStringGeneros() {
        List<String> listaStringGeneros = new ArrayList<>();

        for (Genero genero: generosPeliculas) {
            listaStringGeneros.add(genero.getNombre());
        }
        return listaStringGeneros;
    }

    /**
     * Obtiene una lista de Strings con la información de todas las películas.
     *
     * @return Lista de Strings con detalles de cada película.
     */
    public List<String> obtenerListaStringPeliculas() {
        List<String> listaStringPeliculas = new ArrayList<>();

        for (Pelicula pelicula: listaPeliculas) {
            listaStringPeliculas.add(pelicula.getTitulo() + "," + pelicula.getGenero()
                    + "," + pelicula.getPrecioSemanal() + "," + (pelicula.isActiva() ? "1" : "0"));
        }
        return listaStringPeliculas;
    }

    /**
     * Busca una película que esté activa o inactiva por su título.
     *
     * @param titulo El título de la película.
     * @return El objeto Pelicula si se encuentra, o null si no.
     */
    public Pelicula obtenerPeliculaActivaOInactiva(String titulo) {
        for (Pelicula pelicula: listaPeliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de nombres de géneros como una cadena.
     *
     * @return Cadena con nombres de géneros.
     */
    public String obtenerNombresGeneros() {
        StringBuilder cadena = new StringBuilder();

        for (Genero genero: generosPeliculas) {
            cadena.append(genero.getNombre()).append("\n");
        }
        return cadena.toString();
    }
}
