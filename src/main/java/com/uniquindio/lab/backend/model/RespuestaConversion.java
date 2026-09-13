package com.uniquindio.lab.backend.model;

public class RespuestaConversion {
    private String resultado;
    private String error;

    public RespuestaConversion() {
    }

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