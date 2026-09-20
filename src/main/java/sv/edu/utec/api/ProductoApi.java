package sv.edu.utec.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sv.edu.utec.modelo.Producto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoApi {
    private int id;
    private String title;
    private int stock;

    public ProductoApi() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Producto aProducto() {
        Producto p = new Producto();
        p.setId(this.id);
        if (this.title != null && this.title.length() > 50) {
            p.setNombre(this.title.substring(0, 50));
        } else {
            p.setNombre(this.title);
        }
        p.setCantidad(this.stock);
        return p;
    }
}