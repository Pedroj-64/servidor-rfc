package com.uniquindio.lab.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Service
public class ClienteRFCService {
    @Value("${rfc.host}")
    private String host;

    @Value("${rfc.puerto}")
    private int puerto;

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
