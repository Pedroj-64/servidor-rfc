package com.uniquindio.lab.backend;

import com.uniquindio.lab.backend.model.PeticionConversion;

import java.util.List;

/**
 * Prueba manual de la serializacion de peticiones al protocolo RFC.
 */
public class BackendTesting {
    /**
     * Muestra ejemplos de las seis operaciones soportadas.
     *
     * @param args argumentos opcionales de la ejecucion
     */
    public static void main(String[] args) {
        System.out.println("=== Prueba del conversor numerico ===");

        mostrarLinea("Decimal a binario", new PeticionConversion(1, List.of("25", "8")));
        mostrarLinea("Binario a decimal", new PeticionConversion(2, List.of("11001")));
        mostrarLinea("Decimal a hexadecimal", new PeticionConversion(3, List.of("255", "2")));
        mostrarLinea("Hexadecimal a decimal", new PeticionConversion(4, List.of("FF")));
        mostrarLinea("Binario a hexadecimal", new PeticionConversion(5, List.of("11111111", "2")));
        mostrarLinea("Hexadecimal a binario", new PeticionConversion(6, List.of("FF")));
    }

    private static void mostrarLinea(String descripcion, PeticionConversion peticion) {
        System.out.println(descripcion + ": " + peticion.toLineaProtocolo());
    }
}
