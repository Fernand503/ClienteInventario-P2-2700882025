package sv.edu.utec.servicio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import sv.edu.utec.datos.ProductoDAO;
import sv.edu.utec.modelo.Producto;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventarioJsonService {

    private final ProductoDAO dao = new ProductoDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    public InventarioJsonService() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);  // JSON legible
    }

    public void exportar(String ruta) throws SQLException, IOException {
        List<Producto> productos = dao.listar();
        mapper.writeValue(new File(ruta), productos);
    }

    public int importar(String ruta) throws SQLException, IOException {
        List<Producto> productos = mapper.readValue(
                new File(ruta),
                new TypeReference<List<Producto>>() {}
        );

        int insertados = 0;
        for (Producto p : productos) {
            if (!dao.existe(p.getId())) {
                dao.insertar(p);
                insertados++;
            }
        }
        return insertados;
    }
}