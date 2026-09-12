package com.uniquindio.lab.backend.model;

/**
 * la clase @class RespuestaConversion es una clase utilitaria que unicamente
 * contiene el metodo @method
 * calcularConversion que se encarga de realizar las conversiones entre los
 * diferentes sistemas numericos.
 */
public class ResultadoConversion {
    /**
     * El metodo @method calcularConversion recibe un @param valor de tipo string y
     * una @param opcion
     * de tipo int.
     * 
     * // por un lado la eleccion de escoger @param valor como String se debe a que
     * la base
     * // hexadicimal cuenta con varias letras del abecedario
     * // por ende se utiliza String para poder manejar todos los posibles valores.
     * // como contramedida a esto para las opciones que si necesitan valores de
     * tipo
     * // Integer se convierte el String a Integer usando Integer.parseInt().
     */

    public static String calcularConversion(String valor, int opcion) {
        switch (opcion) {
            // decimal a binario
            case 1:
                return Integer.toBinaryString(Integer.parseInt(valor));

            // binario a decimal
            case 2:
                return Integer.toString(Integer.parseInt(valor, 2));

            // decimal a hexadecimal
            case 3:
                return Integer.toHexString(Integer.parseInt(valor));

            // hexadecimal a decimal
            case 4:
                return Integer.toString(Integer.parseInt(valor, 16));

            // binario a hexadecimal
            case 5:
                return Integer.toHexString(Integer.parseInt(valor, 2));

            // hexadecimal a binario
            case 6:
                return Integer.toBinaryString(Integer.parseInt(valor, 16));

            default:
                return "Seleccione una opción válida";
        }
    }
}