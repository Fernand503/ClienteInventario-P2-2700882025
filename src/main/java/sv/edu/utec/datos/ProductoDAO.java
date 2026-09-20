package sv.edu.utec.datos;

import sv.edu.utec.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void crearTabla() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS producto (" +
                "id INT PRIMARY KEY, nombre VARCHAR(50), cantidad INT)";
        try (Connection cn = ConexionDB.obtenerConexion();
             Statement st = cn.createStatement()) {
            st.execute(sql);
        }
    }

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (id, nombre, cantidad) VALUES (?, ?, ?)";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getCantidad());
            ps.executeUpdate();
        }
    }

    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, cantidad FROM producto";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad")));
            }
        }
        return lista;
    }

    public boolean actualizar(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre = ?, cantidad = ? WHERE id = ?";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCantidad());
            ps.setInt(3, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean existe(int id) throws SQLException {
        String sql = "SELECT 1 FROM producto WHERE id = ?";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}