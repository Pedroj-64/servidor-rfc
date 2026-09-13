# Servidor RFC - Conversor numerico

Aplicacion de laboratorio que convierte numeros entre los sistemas decimal, binario y hexadecimal. El proyecto combina una interfaz web con Spring Boot y un servidor RFC independiente que atiende peticiones TCP.

## 1. Arquitectura general

El sistema esta formado por dos procesos Java:

1. **Backend Spring Boot**
   - Atiende la pagina web.
   - Recibe peticiones HTTP en el puerto `8080`.
   - Convierte el JSON recibido en un objeto `PeticionConversion`.
   - Serializa la peticion al protocolo de texto RFC.
   - Actua como cliente TCP del servidor RFC.
   - Devuelve la respuesta al navegador como JSON.

2. **Servidor RFC**
   - Escucha conexiones TCP en el puerto `5000`.
   - Crea un `ClienteDriver` por cliente conectado.
   - Interpreta la linea del protocolo.
   - Ejecuta la conversion mediante `ConversorImp`.
   - Devuelve una sola linea con el resultado.

Flujo completo:

```text
Navegador
    | POST /api/convertir con JSON
    v
ConversionController (Spring Boot :8080)
    |
    | PeticionConversion.toLineaProtocolo()
    v
ClienteRFCService
    | TCP: "1;25;8\\n"
    v
ServidorRFC (:5000)
    v
ClienteDriver.procesador()
    v
ConversorImp
    | resultado: "00011001"
    v
ClienteRFCService -> ConversionController -> navegador
```

## 2. Estructura del proyecto

```text
servidor-rfc/
├── pom.xml
├── README.md
├── COMANDOS-INICIO.txt
└── src/
    └── main/
        ├── java/com/uniquindio/lab/
        │   ├── backend/
        │   │   ├── BackendApplication.java
        │   │   ├── BackendTesting.java
        │   │   ├── controller/
        │   │   │   └── ConversionController.java
        │   │   ├── model/
        │   │   │   ├── PeticionConversion.java
        │   │   │   └── RespuestaConversion.java
        │   │   └── service/
        │   │       └── ClienteRFCService.java
        │   └── rfc/
        │       ├── ClienteDriver.java
        │       ├── ServidorRFC.java
        │       └── conversion/
        │           ├── Conversor.java
        │           └── ConversorImp.java
        └── resources/
            ├── application.properties
            └── static/
                ├── app.js
                ├── index.html
                └── style.css
```

`target/` es una carpeta generada por Maven. No contiene el codigo fuente y puede regenerarse ejecutando Maven.

## 3. Tecnologias y configuracion

- Java 17 como nivel de compilacion.
- Spring Boot `3.2.5`.
- Spring Web, que incluye MVC, Jackson y Tomcat embebido.
- Maven.
- JavaScript, HTML y CSS para la interfaz.
- TCP mediante `ServerSocket` y `Socket`.

La configuracion esta en `src/main/resources/application.properties`:

```properties
server.port=8080
rfc.host=localhost
rfc.puerto=5000
```

`BackendApplication` tiene `@SpringBootApplication`. Esa anotacion activa la autoconfiguracion de Spring y permite iniciar Tomcat, descubrir el controlador y registrar el servicio.

## 4. Inicio del sistema

Los dos procesos deben estar activos al mismo tiempo.

### Opcion rapida: un solo comando

En Linux o macOS, desde la raiz del proyecto:

```bash
chmod +x iniciar.sh
./iniciar.sh
```

El script compila el JAR si todavía no existe, inicia ambos servicios y permite
detenerlos juntos con `Ctrl + C`. La aplicación queda disponible en
`http://localhost:8080`.

### Opcion A: desde VS Code

1. Ejecutar `ServidorRFC`.
2. Ejecutar `BackendApplication`.
3. Abrir `http://localhost:8080`.

### Opcion B: desde terminal

Terminal 1, compilar:

```bash
cd /home/ajolote/Documentos/Codigo/servidor-rfc
mvn clean package
```

Terminal 2, iniciar el servidor RFC:

```bash
cd /home/ajolote/Documentos/Codigo/servidor-rfc
java -cp target/classes com.uniquindio.lab.rfc.ServidorRFC
```

Terminal 3, iniciar Spring Boot:

```bash
cd /home/ajolote/Documentos/Codigo/servidor-rfc
mvn spring-boot:run
```

Abrir en el navegador:

```text
http://localhost:8080
```

Tambien puede abrirse directamente:

```text
http://localhost:8080/index.html
```

No se debe iniciar unicamente Spring Boot si se quieren realizar conversiones: el backend necesita encontrar el servidor RFC en `localhost:5000`.

## 5. Recorrido de una peticion

Ejemplo: convertir decimal `25` a binario con longitud `8`.

### 5.1 Interfaz web

`index.html` ofrece las seis operaciones. `app.js` genera los campos de entrada dinamicamente mediante `renderCampos()`.

Cuando el usuario pulsa **Convertir**, se ejecuta `convertir(event)`:

```json
{
  "opcion": 1,
  "parametros": ["25", "8"]
}
```

El navegador envia ese objeto con:

```text
POST /api/convertir
Content-Type: application/json
```

### 5.2 Controlador HTTP

`ConversionController.convertir(...)` recibe el JSON usando `@RequestBody`.

Spring deserializa el JSON en `PeticionConversion` porque la clase tiene constructor vacio, getters y setters.

El controlador llama:

```java
peticion.toLineaProtocolo()
```

La salida es:

```text
1;25;8
```

Luego delega en:

```java
servicio.enviar("1;25;8")
```

### 5.3 Cliente TCP del backend

`ClienteRFCService.enviar(...)`:

1. Lee `rfc.host` y `rfc.puerto`.
2. Abre un `Socket` hacia `localhost:5000`.
3. Envia la linea mediante `PrintWriter.println(...)`.
4. Lee una linea de respuesta con `BufferedReader.readLine()`.
5. Cierra automaticamente el socket mediante `try-with-resources`.

El salto de linea de `println` es necesario porque el servidor RFC lee con `readLine()`.

### 5.4 Servidor RFC

`ServidorRFC.main()` abre un `ServerSocket` en el puerto `5000` y espera conexiones con `accept()`.

Por cada conexion crea:

```java
new ClienteDriver(socketCliente)
```

y la ejecuta en un hilo independiente.

### 5.5 Procesamiento de la linea

`ClienteDriver.run()` lee:

```text
1;25;8
```

y llama a `procesador(linea)`.

`procesador` separa la linea por `;`:

```text
partes[0] = "1"
partes[1] = "25"
partes[2] = "8"
```

Despues selecciona la operacion con un `switch` y llama al metodo adecuado de `ConversorImp`.

Para la opcion `1`:

```java
conversor.decimalABinario(25, 8)
```

El resultado es:

```text
00011001
```

`ClienteDriver` envia esa respuesta al backend con `outing.println(respuesta)`.

### 5.6 Respuesta HTTP

El controlador recibe el resultado y crea:

```java
new RespuestaConversion("00011001", null)
```

Jackson lo serializa como:

```json
{
  "resultado": "00011001",
  "error": null
}
```

Finalmente `app.js` muestra `data.resultado` en el elemento `#resultado`.

## 6. Contratos de datos

### JSON de entrada

```json
{
  "opcion": 1,
  "parametros": ["25", "8"]
}
```

`parametros` es una lista de textos porque puede contener numeros binarios y hexadecimales.

### Protocolo TCP

Formato general:

```text
OPCION;PARAMETRO_1;PARAMETRO_2
```

Ejemplos:

```text
1;25;8
2;11001
3;255;2
4;FF
5;11111111;2
6;FF
```

El protocolo no utiliza JSON. El JSON solo se usa entre el navegador y Spring Boot.

### JSON de salida

Respuesta exitosa:

```json
{
  "resultado": "00011001",
  "error": null
}
```

Respuesta con error:

```json
{
  "resultado": null,
  "error": "mensaje del error"
}
```

## 7. Operaciones soportadas

| Opcion | Entrada | Salida | Ejemplo |
|---|---|---|---|
| 1 | Decimal, longitud en bits | Binario | `1;25;8` -> `00011001` |
| 2 | Binario | Decimal | `2;11001` -> `25` |
| 3 | Decimal, longitud en digitos hexadecimales | Hexadecimal | `3;255;2` -> `FF` |
| 4 | Hexadecimal | Decimal | `4;FF` -> `255` |
| 5 | Binario, longitud en digitos hexadecimales | Hexadecimal | `5;11111111;2` -> `FF` |
| 6 | Hexadecimal | Binario | `6;FF` -> `11111111` |

La longitud solo se aplica a las operaciones `1`, `3` y `5`. Se usa para rellenar con ceros a la izquierda cuando el resultado es mas corto.

## 8. Responsabilidad de cada clase

### Backend

- `BackendApplication`: punto de entrada de Spring Boot.
- `ConversionController`: endpoint HTTP `POST /api/convertir`; coordina la peticion y la respuesta.
- `PeticionConversion`: modelo del JSON de entrada y serializador hacia la linea RFC.
- `RespuestaConversion`: modelo del JSON de salida.
- `ClienteRFCService`: cliente TCP del backend; no realiza conversiones.

### Servidor RFC

- `ServidorRFC`: abre el puerto `5000`, acepta clientes y crea hilos.
- `ClienteDriver`: atiende una conexion, interpreta el protocolo y delega la operacion.
- `Conversor`: contrato de las conversiones disponibles.
- `ConversorImp`: implementacion de las conversiones y del relleno con ceros.

La conversion matematica no debe colocarse en `PeticionConversion`: esa clase pertenece al transporte de datos del backend. La conversion pertenece a `rfc/conversion`.

## 9. Pruebas manuales

Con ambos procesos activos, se puede probar el endpoint con `curl`:

```bash
curl -X POST http://localhost:8080/api/convertir \
  -H "Content-Type: application/json" \
  -d '{"opcion":1,"parametros":["25","8"]}'
```

Resultado esperado:

```json
{"resultado":"00011001","error":null}
```

Para probar solo la serializacion del modelo:

```bash
mvn test
java -cp target/classes com.uniquindio.lab.backend.BackendTesting
```

## 10. Errores frecuentes

### La pagina no abre

- Confirmar que `BackendApplication` esta ejecutandose.
- Confirmar que Spring inicio sin errores.
- Abrir `http://localhost:8080`, no el archivo HTML directamente.
- Confirmar que `index.html`, `app.js` y `style.css` estan en `src/main/resources/static`.

### Error de conexion al convertir

El backend no logra conectarse a `localhost:5000`. Iniciar primero `ServidorRFC` y comprobar que muestra `Servidor activo`.

### El puerto 8080 ya esta ocupado

Detener el proceso que usa el puerto o cambiar `server.port` en `application.properties`.

### El puerto 5000 ya esta ocupado

Detener otro servidor RFC o cambiar conjuntamente `ServidorRFC.PUERTO` y `rfc.puerto`.

### Se devuelve la linea original en vez del resultado

En `ClienteDriver.run()` debe enviarse:

```java
outing.println(respuesta);
```

y no:

```java
outing.println(linea);
```

### El resultado no tiene el formato esperado

Revisar la opcion enviada, el orden de `parametros` y la longitud indicada. El servidor RFC espera valores separados por punto y coma en el orden definido en la tabla.

## 11. Como extender el proyecto

Para agregar una nueva conversion:

1. Agregar el metodo al contrato `Conversor`.
2. Implementarlo en `ConversorImp`.
3. Agregar un nuevo `case` en `ClienteDriver.procesador`.
4. Agregar la opcion y sus campos en `CAMPOS_POR_OPCION` de `app.js`.
5. Agregar la opcion visual en `index.html`.
6. Actualizar la tabla del protocolo y agregar una prueba.

Para cambiar el formato del protocolo, actualizar de forma coordinada:

- `PeticionConversion.toLineaProtocolo()`.
- `ClienteDriver.procesador()`.
- `ClienteRFCService` si cambia la forma de lectura/escritura.
- La documentacion y las pruebas manuales.
