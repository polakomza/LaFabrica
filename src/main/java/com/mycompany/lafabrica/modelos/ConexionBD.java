package com.mycompany.lafabrica.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/fabrica";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Método para obtener la conexión a la base de datos (Singleton)
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecer la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error al conectar a la base de datos");
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
