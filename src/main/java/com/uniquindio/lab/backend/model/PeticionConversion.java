package com.uniquindio.lab.backend.model;

import java.util.ArrayList;
import java.util.List;

public class PeticionConversion {
    private int opcion;
    private List<String> parametros = new ArrayList<>();

    public PeticionConversion() {
    }

    public PeticionConversion(int opcion, List<String> parametros) {
        this.opcion = opcion;
        this.parametros = parametros;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    public String toLineaProtocolo() {
        StringBuilder linea = new StringBuilder().append(opcion);
        if (parametros != null) {
            for (String parametro : parametros) {
                linea.append(';').append(parametro == null ? "" : parametro.trim());
            }
        }
        return linea.toString();
    }
}