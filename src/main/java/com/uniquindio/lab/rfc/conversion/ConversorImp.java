package com.uniquindio.lab.rfc.conversion;

public class ConversorImp implements Conversor {

    @Override
    public String decimalABinario(int numero, int longitudBits) {
        return rellenar(Integer.toBinaryString(numero), longitudBits);
    }

    @Override
    public int binarioADecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    @Override
    public String decimalAHexadecimal(int numero, int longitudDigitos) {
        return rellenar(
                Integer.toHexString(numero).toUpperCase(),
                longitudDigitos
        );
    }

    @Override
    public int hexadecimalADecimal(String hexadecimal) {
        return Integer.parseInt(hexadecimal, 16);
    }

    @Override
    public String binarioAHexadecimal(String binario, int longitudDigitos) {
        String hexadecimal = Integer.toHexString(
                binarioADecimal(binario)
        ).toUpperCase();

        return rellenar(hexadecimal, longitudDigitos);
    }

    @Override
    public String hexadecimalABinario(String hexadecimal) {
        return Integer.toBinaryString(
                hexadecimalADecimal(hexadecimal)
        );
    }

    private String rellenar(String valor, int longitud) {
        if (longitud <= 0 || valor.length() >= longitud) {
            return valor;
        }
        

        return "0".repeat(longitud - valor.length()) + valor;
    }
}