package com.uniquindio.lab.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Cliente TCP utilizado por el backend para comunicarse con ServidorRFC.
 *
 * Cada llamada abre una conexion, envia una linea, lee una respuesta y cierra
 * el socket. El host y el puerto se obtienen desde application.properties.
 */
@Service
public class ClienteRFCService {
    @Value("${rfc.host}")
    private String host;

    @Value("${rfc.puerto}")
    private int puerto;

    /**
     * Envia una peticion RFC y espera una respuesta de una sola linea.
     *
     * @param mensajeProtocolo linea como 1;25;8, sin salto de linea final
     * @return respuesta enviada por el servidor RFC
     * @throws IOException si no es posible abrir, escribir o leer el socket
     */
    public String enviar(String mensajeProtocolo) throws IOException {
        try (Socket socket = new Socket(host, puerto);
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            salida.println(mensajeProtocolo);
            return entrada.readLine();
        }
    }
}
