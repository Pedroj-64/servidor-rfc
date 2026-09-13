package com.uniquindio.lab.rfc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.uniquindio.lab.rfc.conversion.Conversor;
import com.uniquindio.lab.rfc.conversion.ConversorImp;

public class ClienteDriver implements Runnable {

    private final Socket socket;
    private final Conversor conversor;

    public ClienteDriver(Socket socket) {
        this.socket = socket;
        this.conversor = new ConversorImp();
    }

    @Override
    public void run() {
        String linea;
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter outing = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            while ((linea = input.readLine()) != null) {
                System.out.println("El servidor recibio: " + linea);
                String respuesta = procesador(linea);
                System.out.println("El servidor enviara: " + respuesta);

                outing.println(respuesta);

            }
        } catch (IOException e) {
            System.err.println("Error procesando cliente RFC: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {
            }
            System.out.println("Se desconecto al cliente");
        }
    }

    private String procesador(String entrada) {
        String[] partes = entrada.trim().split(";", -1);
        int opcion = Integer.parseInt(partes[0].trim());
        String valor = partes.length > 1 ? partes[1].trim() : "";
        int longitud = partes.length > 2 ? Integer.parseInt(partes[2].trim()) : 0;

        return switch (opcion) {
            case 1 -> conversor.decimalABinario(Integer.parseInt(valor), longitud);
            case 2 -> Integer.toString(conversor.binarioADecimal(valor));
            case 3 -> conversor.decimalAHexadecimal(Integer.parseInt(valor), longitud);
            case 4 -> Integer.toString(conversor.hexadecimalADecimal(valor));
            case 5 -> conversor.binarioAHexadecimal(valor, longitud);
            case 6 -> conversor.hexadecimalABinario(valor);
            default -> throw new IllegalArgumentException("Seleccione una opción válida");
        };
    }

}
