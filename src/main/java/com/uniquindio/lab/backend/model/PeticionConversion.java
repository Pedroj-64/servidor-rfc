package com.uniquindio.lab.backend.model;

public class PeticionConversion {
    private int opcion;
    private String valor;

    // solo para cuando se vaya a rellenar con ceros a la izquierda un numero
    // binario o hexadecimal
    private int longitudBits;

    public PeticionConversion(int opcion, String valor, int longitudBits) {
        this.opcion = opcion;
        this.valor = valor;
        this.longitudBits = longitudBits;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getLongitudBits() {
        return longitudBits;
    }

    public void setLongitudBits(int longitudBits) {
        this.longitudBits = longitudBits;
    }

    public String generarRespuesta() {
        String resultado = ResultadoConversion.calcularConversion(valor, opcion);

        // rellenar con ceros a la izquierda si es necesario
        if (longitudBits > 0) {
            while (resultado.length() < longitudBits) {
                resultado = "0" + resultado;
            }
        }
        return resultado + "\n";
    }
}