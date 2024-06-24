
package com.mycompany.lafabrica.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Producto {
    private String nombre;
    private Map<MateriaPrima, Integer> formula;

    public Producto(String nombre) {
        this.nombre = nombre;
        this.formula = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Map<MateriaPrima, Integer> getFormula() {
        return formula;
    }

    public void agregarMateriaPrima(MateriaPrima materiaPrima, int cantidad) {
        formula.put(materiaPrima, cantidad);
    }

    // Método que busca un Producto por nombre
    public static Producto buscarPorNombre(String nombre, Connection connection) {
        Producto producto = null;
        String query = "SELECT * FROM Producto WHERE nombre = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                producto = new Producto(nombre);
                // Cargar la fórmula del producto
                cargarFormula(producto, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    // Método que carga la fórmula de un producto
    private static void cargarFormula(Producto producto, Connection connection) {
        String query = "SELECT materiaPrima, cantidad FROM ProductoMateriaPrima WHERE producto = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, producto.getNombre());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombreMateriaPrima = resultSet.getString("materiaPrima");
                int cantidad = resultSet.getInt("cantidad");
                MateriaPrima materiaPrima = MateriaPrima.buscarPorNombre(nombreMateriaPrima, connection);
                if (materiaPrima != null) {
                    producto.agregarMateriaPrima(materiaPrima, cantidad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método que obtiene todos los productos
    public static List<Producto> obtenerTodos(Connection connection) {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM Producto";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombreProducto = resultSet.getString("nombre");
                Producto producto = new Producto(nombreProducto);
                cargarFormula(producto, connection);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    @Override
    public String toString() {
        return nombre;
    }
}