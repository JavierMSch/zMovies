package org.example.zmovies.Exceptions;

public class ReportePlanillaException extends Exception {
    public ReportePlanillaException(String mensaje) {
        super(mensaje);
    }

    public ReportePlanillaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
