package com.uniquindio.lab.backend.model;

/**
 * Modelo de salida del endpoint de conversion.
 *
 * En una respuesta exitosa se informa resultado y error es null. Si ocurre un
 * problema, resultado es null y error contiene una descripcion.
 */
public class RespuestaConversion {
    private String resultado;
    private String error;

    /** Constructor vacio requerido por herramientas de serializacion. */
    public RespuestaConversion() {
    }

    /**
     * Crea una respuesta con resultado o error.
     *
     * @param resultado valor convertido
     * @param error descripcion del error, o null si la operacion fue exitosa
     */
    public RespuestaConversion(String resultado, String error) {
        this.resultado = resultado;
        this.error = error;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}