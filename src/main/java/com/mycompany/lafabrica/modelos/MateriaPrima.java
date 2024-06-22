
package com.mycompany.lafabrica.modelos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrima {
    private String nombre;
    private int cantidad;
    private String unidadMedida;

    public MateriaPrima(String nombre, int cantidad ) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public MateriaPrima(String nombre, int cantidad, String unidadMedida) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre;}

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida(){ return unidadMedida;}

    public void setUnidadMedida(String unidadMedida){ this.unidadMedida = unidadMedida;}

    // Método para buscar una MateriaPrima por nombre
    public static MateriaPrima buscarPorNombre(String nombre, Connection connection) {
        MateriaPrima materiaPrima = null;
        String query = "SELECT * FROM MateriaPrima WHERE nombre = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int stock = resultSet.getInt("cantidad");
                String unidadMedida = resultSet.getString("unidadMedida");
                materiaPrima = new MateriaPrima(nombre, stock, unidadMedida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materiaPrima;
    }

    // Método estático para obtener todas las materias primas
    public static List<MateriaPrima> obtenerTodas(Connection connection) {
        List<MateriaPrima> materiasPrimas = new ArrayList<>();
        String query = "SELECT * FROM MateriaPrima";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int cantidad = resultSet.getInt("cantidad");
                String unidadMedida = resultSet.getString("unidadMedida");
                MateriaPrima materiaPrima = new MateriaPrima(nombre, cantidad, unidadMedida);
                materiasPrimas.add(materiaPrima);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materiasPrimas;
    }

    @Override
    public String toString() {
        return nombre + ": " + cantidad;
    }
}