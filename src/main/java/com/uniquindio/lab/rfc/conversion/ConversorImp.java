package com.uniquindio.lab.rfc.conversion;

/**
 * Implementacion de las conversiones numericas del protocolo RFC.
 */
public class ConversorImp implements Conversor {

    /** {@inheritDoc} */
    @Override
    public String decimalABinario(int numero, int longitudBits) {
        return rellenar(Integer.toBinaryString(numero), longitudBits);
    }

    /** {@inheritDoc} */
    @Override
    public int binarioADecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    /** {@inheritDoc} */
    @Override
    public String decimalAHexadecimal(int numero, int longitudDigitos) {
        return rellenar(
                Integer.toHexString(numero).toUpperCase(),
                longitudDigitos
        );
    }

    /** {@inheritDoc} */
    @Override
    public int hexadecimalADecimal(String hexadecimal) {
        return Integer.parseInt(hexadecimal, 16);
    }

    /** {@inheritDoc} */
    @Override
    public String binarioAHexadecimal(String binario, int longitudDigitos) {
        String hexadecimal = Integer.toHexString(
                binarioADecimal(binario)
        ).toUpperCase();

        return rellenar(hexadecimal, longitudDigitos);
    }

    /** {@inheritDoc} */
    @Override
    public String hexadecimalABinario(String hexadecimal) {
        return Integer.toBinaryString(
                hexadecimalADecimal(hexadecimal)
        );
    }

    /**
     * Agrega ceros a la izquierda sin cortar resultados que ya superan la
     * longitud solicitada.
     *
     * @param valor resultado antes del relleno
     * @param longitud longitud minima deseada
     * @return valor con el relleno aplicado
     */
    private String rellenar(String valor, int longitud) {
        if (longitud <= 0 || valor.length() >= longitud) {
            return valor;
        }
        

        return "0".repeat(longitud - valor.length()) + valor;
    }
}