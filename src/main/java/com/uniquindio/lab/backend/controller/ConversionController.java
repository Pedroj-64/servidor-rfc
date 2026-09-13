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

/**
 * Controlador HTTP que recibe peticiones de conversion desde la interfaz web.
 *
 * La ruta publica es POST /api/convertir. El controlador no realiza la
 * conversion matematica: serializa la peticion y la delega al cliente RFC.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ConversionController {
	private final ClienteRFCService servicio;

	/**
	 * Crea el controlador usando inyeccion de dependencias por constructor.
	 *
	 * @param servicio cliente TCP usado para comunicarse con el servidor RFC
	 */
	public ConversionController(ClienteRFCService servicio) {
		this.servicio = servicio;
	}

	/**
	 * Recibe el JSON de la pagina, solicita la conversion y construye el JSON de
	 * respuesta.
	 *
	 * @param peticion datos recibidos con las propiedades opcion y parametros
	 * @return resultado de la conversion o mensaje de error
	 */
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
