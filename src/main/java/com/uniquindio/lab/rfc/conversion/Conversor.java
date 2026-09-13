package com.uniquindio.lab.rfc.conversion;

/**
 * Contrato de las conversiones numericas ofrecidas por el servidor RFC.
 */
public interface Conversor {

    /** Convierte decimal a binario y completa con ceros hasta la longitud indicada. */
    String decimalABinario(int numero, int longitudBits);

    /** Convierte una cadena binaria a decimal. */
    int binarioADecimal(String binario);

    /** Convierte decimal a hexadecimal y completa con ceros si es necesario. */
    String decimalAHexadecimal(int numero, int longitudDigitos);

    /** Convierte una cadena hexadecimal a decimal. */
    int hexadecimalADecimal(String hexadecimal);

    /** Convierte binario a hexadecimal y completa con ceros si es necesario. */
    String binarioAHexadecimal(String binario, int longitudDigitos);

    /** Convierte una cadena hexadecimal a binario. */
    String hexadecimalABinario(String hexadecimal);

}
