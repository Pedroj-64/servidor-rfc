package com.uniquindio.lab.rfc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor TCP del protocolo RFC.
 *
 * Escucha el puerto 5000 y crea un hilo ClienteDriver para cada conexion.
 */
public class ServidorRFC {

    /** Puerto TCP en el que escucha el servidor RFC. */
    public static final int PUERTO = 5000;

    /** Inicia el servidor y acepta clientes hasta que el proceso termina. */
    public static void main(String[] args) {
        // Mensaje de LOG
        System.out.println("Pruebita Arrancando en el puerto " + PUERTO);
        // El try-catch lo que hace es intentar iniciar un server y cerrarlo al salir
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor activo");
            // Blucle infinit para aceptar la peticion de conexion de cliente, el se bloque
            // solo hasta que entre un cliente
            while (true) {
                Socket socketCliente = serverSocket.accept();

                // Mensaje LOG para conocer quien se conecto
                System.out.println("Se conecto un cliente desde:" + socketCliente.getInetAddress().getHostAddress()
                        + ":" + socketCliente.getPort());

                //
                ClienteDriver mDriver = new ClienteDriver(socketCliente);
                // Hilo de procesamiento por cliente
                new Thread(mDriver).start();
            }
        } catch (IOException e) {
            System.err.println("Se cayo la vuelta, error fatal mire: " + e.getMessage());
        }
    }
}
