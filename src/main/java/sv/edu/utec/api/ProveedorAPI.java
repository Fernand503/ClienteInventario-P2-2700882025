package sv.edu.utec.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import sv.edu.utec.modelo.Producto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProveedorAPI {

    private static final String URL_BASE = "https://dummyjson.com/products";

    private final HttpClient cliente;
    private final ObjectMapper mapper;

    public ProveedorAPI() {
        cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        mapper = new ObjectMapper();
    }

    public List<Producto> obtenerProductos(int limite)
            throws IOException, InterruptedException {

        if (limite <= 0) {
            throw new IllegalArgumentException("El limite debe ser mayor que cero");
        }

        String url = URL_BASE + "?limit=" + limite + "&select=title,stock";

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() != 200) {
            throw new IOException(
                    "La API devolvio el codigo HTTP: " + respuesta.statusCode()
            );
        }

        RespuestaProductos datos = mapper.readValue(
                respuesta.body(),
                RespuestaProductos.class
        );

        if (datos == null || datos.getProducts() == null) {
            throw new IOException("La respuesta de la API no contiene la lista products");
        }

        List<Producto> productos = new ArrayList<>();
        for (ProductoApi productoApi : datos.getProducts()) {
            if (productoApi == null) {
                throw new IOException("La API devolvio un producto nulo");
            }
            productos.add(productoApi.aProducto());
        }

        return productos;
    }
}
