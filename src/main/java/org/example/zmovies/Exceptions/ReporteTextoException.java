package org.example.zmovies.Exceptions;

/**
 * Excepción personalizada para errores relacionados con el reporte de películas en formato texto.
 */
public class ReporteTextoException extends Exception {
    /**
     * Constructor de la excepción que acepta un mensaje de error.
     *
     * @param mensaje Mensaje de error asociado con la excepción
     */
    public ReporteTextoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor de la excepción que acepta un mensaje de error y una causa.
     *
     * @param mensaje Mensaje de error asociado con la excepción
     * @param causa Causa original de la excepción
     */
    public ReporteTextoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
