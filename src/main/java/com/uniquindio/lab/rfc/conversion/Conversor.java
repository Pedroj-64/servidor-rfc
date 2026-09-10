package com.uniquindio.lab.rfc.conversion;

public interface Conversor {

    //Mateo si ve esto...feliz kirkanniversary tardio
    String decimalABinario(int numero, int longitudBits);

    
    int binarioADecimal(String binario);

   
    String decimalAHexadecimal(int numero, int longitudDigitos);

   
    int hexadecimalADecimal(String hexadecimal);

  
    String binarioAHexadecimal(String binario, int longitudDigitos);

   
    String hexadecimalABinario(String hexadecimal);

}
