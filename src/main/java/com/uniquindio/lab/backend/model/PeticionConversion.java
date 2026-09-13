package com.uniquindio.lab.backend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de entrada del endpoint de conversion.
 *
 * Representa un JSON como {"opcion": 1, "parametros": ["25", "8"]} y lo
 * convierte a una linea de texto para el protocolo RFC: 1;25;8.
 */
public class PeticionConversion {
    private int opcion;
    private List<String> parametros = new ArrayList<>();

    /** Constructor vacio requerido por Jackson para deserializar JSON. */
    public PeticionConversion() {
    }

    /**
     * Crea una peticion directamente desde codigo Java.
     *
     * @param opcion codigo de la conversion, entre 1 y 6
     * @param parametros valores de entrada en el orden definido por el protocolo
     */
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

    /**
     * Serializa la peticion para enviarla por TCP al servidor RFC.
     *
     * @return linea con formato OPCION;PARAMETRO_1;PARAMETRO_2
     */
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