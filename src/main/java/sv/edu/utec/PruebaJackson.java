package sv.edu.utec;

import com.fasterxml.jackson.databind.ObjectMapper;
import sv.edu.utec.modelo.Producto;
import java.io.IOException;

// Clase temporal de laboratorio. NO forma parte del entregable.
public class PruebaJackson {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Serializar: objeto -> texto
        Producto p = new Producto(1, "Teclado mecanico", 15);
        System.out.println(mapper.writeValueAsString(p));
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p));

        // Deserializar: texto -> objeto
        String entrada = """
                {"id":7,"nombre":"Mouse inalambrico","cantidad":30}""";
        Producto recuperado = mapper.readValue(entrada, Producto.class);
        System.out.println(recuperado.getNombre() + " -> " + recuperado.getCantidad());
    }
}