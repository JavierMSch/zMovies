package org.example.zmovies.Exceptions;

/**
 * Excepción personalizada para errores relacionados con el reporte de películas en formato de planilla.
 */
public class ReportePlanillaException extends Exception {
    /**
     * Constructor de la excepción que acepta un mensaje de error.
     *
     * @param mensaje Mensaje de error asociado con la excepción
     */
    public ReportePlanillaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor de la excepción que acepta un mensaje de error y una causa.
     *
     * @param mensaje Mensaje de error asociado con la excepción
     * @param causa Causa original de la excepción
     */
    public ReportePlanillaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}