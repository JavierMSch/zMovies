package org.example.zmovies.Exceptions;

public class ReporteTextoException extends Exception {
    public ReporteTextoException(String mensaje) {
        super(mensaje);
    }

    public ReporteTextoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
