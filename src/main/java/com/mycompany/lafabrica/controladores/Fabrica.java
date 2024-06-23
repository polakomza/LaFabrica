package com.mycompany.lafabrica.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fabrica {
    private List<MateriaPrima> materiasPrimas;
    private List<Producto> productos;
    private static List<OrdenProduccion> ordenesPendientes;

    public Fabrica() {
        materiasPrimas = new ArrayList<>();
        productos = new ArrayList<>();
        ordenesPendientes = new ArrayList<>();
    }
    
    public void agregarMateriaPrima(MateriaPrima materiaPrima) {
        materiasPrimas.add(materiaPrima);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public static void agregarOrdenProduccionPendiente(OrdenProduccion orden) {
        ordenesPendientes.add(orden);
    }

    public List<MateriaPrima> getMateriasPrimas() {
        return materiasPrimas;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    public static List<OrdenProduccion> getOrdenesPendientes() {
        return ordenesPendientes;
    }


    public static void procesarOrdenesPendientesBD(Connection connection) {
        List<OrdenProduccion> ordenesCompletadas = new ArrayList<>();

        try {
            ordenesPendientes = getOrdenesPendientesEnBD(connection);
        } catch (SQLException e) {
            System.out.println("Error al obtener órdenes pendientes desde la base de datos.");
            e.printStackTrace();
            return;
        }
        if (ordenesPendientes.isEmpty()) {
            System.out.println("No hay órdenes pendientes para procesar.");
        } else {
            for (OrdenProduccion orden : ordenesPendientes) {
                try {
                    if (puedeRealizarseEnBD(connection, orden)) {
                        realizarOrdenEnBD(connection, orden);
                        ordenesCompletadas.add(orden);
                    } else {
                        System.out.println("No hay suficiente stock para procesar la orden pendiente.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al procesar la orden pendiente.");
                    e.printStackTrace();
                }
            }
        }

        // Eliminar las órdenes completadas de la lista de órdenes pendientes
        ordenesPendientes.removeAll(ordenesCompletadas);
    }

    public static void agregarOrdenProduccionEnBD(Connection connection, OrdenProduccion orden) throws SQLException {
        String sql = "INSERT INTO ordenproduccion (producto, cantidad, pendiente) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orden.getProducto().getNombre());
            pstmt.setInt(2, orden.getCantidad());
            pstmt.setBoolean(3, orden.getPendiente());
            pstmt.executeUpdate();
        }
    }
    public static List<OrdenProduccion> getOrdenesPendientesEnBD(Connection connection) throws SQLException {
        String sql = "SELECT * FROM ordenproduccion WHERE pendiente = true";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("producto");
                int cantidad = rs.getInt("cantidad");
                boolean pendiente = rs.getBoolean("pendiente");

                // Obtener el producto asociado a partir de su ID
                Producto producto = Producto.buscarPorNombre(nombre, connection);

                OrdenProduccion orden = new OrdenProduccion(id, producto, cantidad, pendiente);
                agregarOrdenProduccionPendiente(orden);
            }
        }
        return getOrdenesPendientes();
    }

    public static boolean  puedeRealizarseEnBD(Connection connection, OrdenProduccion orden) throws SQLException {
        Producto producto = orden.getProducto();
        for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
            MateriaPrima materiaPrima = entrada.getKey();
            int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
            if (obtenerCantidadMateriaPrimaEnBD(connection, materiaPrima.getNombre()) < cantidadNecesaria) {
                System.out.println(obtenerCantidadMateriaPrimaEnBD(connection, materiaPrima.getNombre())+ " can: "+ cantidadNecesaria);
                return false;
            }
        }
        return true;
    }
    private static int obtenerCantidadMateriaPrimaEnBD(Connection connection, String nombreMateriaPrima) throws SQLException {
        String sql = "SELECT cantidad FROM materiaprima WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreMateriaPrima);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
            return 0; // Si no se encuentra la materia prima, se asume que hay 0 en stock
        }
    }

    public static void realizarOrdenEnBD(Connection connection, OrdenProduccion orden) throws SQLException {
        Producto producto = orden.getProducto();
        for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
            MateriaPrima materiaPrima = entrada.getKey();
            int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
            actualizarStockEnBD(connection, materiaPrima.getNombre(), -cantidadNecesaria);
        }
        String sql = "UPDATE ordenproduccion SET pendiente = false WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orden.getId());
            pstmt.executeUpdate();
        }
    }
    public static void actualizarStockEnBD(Connection connection, String nombreMateriaPrima, int cantidad) throws SQLException {
        String sql = "UPDATE materiaprima SET cantidad = cantidad + ? WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, nombreMateriaPrima);
            pstmt.executeUpdate();
        }
    }
}