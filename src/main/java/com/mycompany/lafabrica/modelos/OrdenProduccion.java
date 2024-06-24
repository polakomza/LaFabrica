package com.mycompany.lafabrica.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenProduccion {
    private int id;
    private Producto producto;
    private int cantidad;
    private boolean pendiente;

    public OrdenProduccion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pendiente = true;
    }
    public OrdenProduccion(int id, Producto producto, int cantidad, boolean pendiente) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.pendiente = pendiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean getPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    // Método que actualiza el estado de una OrdenProduccion por ID
    public static boolean actualizarEstado(int id, boolean pendiente, Connection connection) {
        boolean actualizado = false;
        String query = "UPDATE OrdenProduccion SET pendiente = ? WHERE id = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, pendiente);
            statement.setInt(2, id);
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }

    // Método que busca una OrdenProduccion por ID
    public static OrdenProduccion buscarPorId(int id, Connection connection) {
        OrdenProduccion orden = null;
        String query = "SELECT * FROM OrdenProduccion WHERE id = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Producto producto = Producto.buscarPorNombre(resultSet.getString("producto"),connection);
                int cantidad = resultSet.getInt("cantidad");
                boolean pendiente = resultSet.getBoolean("pendiente");
                orden = new OrdenProduccion(id, producto, cantidad, pendiente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orden;
    }
    // Método que obtiene todas las órdenes de producción
    public static List<OrdenProduccion> obtenerTodas(Connection connection) {
        List<OrdenProduccion> ordenes = new ArrayList<>();
        String query = "SELECT * FROM OrdenProduccion";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Producto producto = Producto.buscarPorNombre(resultSet.getString("producto"),connection);
                int cantidad = resultSet.getInt("cantidad");
                boolean pendiente = resultSet.getBoolean("pendiente");
                OrdenProduccion orden = new OrdenProduccion(id, producto, cantidad, pendiente);
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordenes;
    }
}