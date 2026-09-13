package com.uniquindio.lab.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend HTTP basado en Spring Boot.
 */
@SpringBootApplication
public class BackendApplication {
    /**
     * Inicia Spring Boot y el servidor web embebido en el puerto configurado.
     *
     * @param args argumentos opcionales de la aplicacion
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
