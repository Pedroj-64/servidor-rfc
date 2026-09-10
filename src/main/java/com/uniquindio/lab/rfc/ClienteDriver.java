package com.uniquindio.lab.rfc;

import java.net.Socket;

import org.springframework.core.Conventions;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
