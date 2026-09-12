package com.uniquindio.lab.backend;

import com.uniquindio.lab.backend.model.PeticionConversion;

public class BackendTesting {
    public static void main(String[] args) {
        System.out.println("=== Prueba del conversor numerico ===");

        mostrarConversion("Decimal a binario", new PeticionConversion(1, "25", 0));
        mostrarConversion("Binario a decimal", new PeticionConversion(2, "11001", 0));
        mostrarConversion("Decimal a hexadecimal", new PeticionConversion(3, "255", 0));
        mostrarConversion("Hexadecimal a decimal", new PeticionConversion(4, "FF", 0));
        mostrarConversion("Binario a hexadecimal", new PeticionConversion(5, "11111111", 0));
        mostrarConversion("Hexadecimal a binario", new PeticionConversion(6, "FF", 0));

        System.out.println("\n=== Prueba de relleno con ceros ===");
        mostrarConversion("25 en binario, completado a 8 bits",
                new PeticionConversion(1, "25", 8));
    }

    private static void mostrarConversion(String descripcion, PeticionConversion peticion) {
        System.out.print(descripcion + ": " + peticion.generarRespuesta());
    }
}
