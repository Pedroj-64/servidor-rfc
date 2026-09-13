package com.uniquindio.lab.backend.controller;

import com.uniquindio.lab.backend.model.PeticionConversion;
import com.uniquindio.lab.backend.model.RespuestaConversion;
import com.uniquindio.lab.backend.service.ClienteRFCService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ConversionController {
	private final ClienteRFCService servicio;

	public ConversionController(ClienteRFCService servicio) {
		this.servicio = servicio;
	}

	@PostMapping("/convertir")
	public RespuestaConversion convertir(@RequestBody PeticionConversion peticion) {
		try {
			String resultado = servicio.enviar(peticion.toLineaProtocolo());
			return new RespuestaConversion(resultado, null);
		} catch (IOException | IllegalArgumentException e) {
			return new RespuestaConversion(null, e.getMessage());
		}
	}
}
