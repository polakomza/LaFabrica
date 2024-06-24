package com.mycompany.lafabrica.vistas;

import com.mycompany.lafabrica.controladores.*;
import com.mycompany.lafabrica.modelos.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Vista extends javax.swing.JFrame {

    private final Fabrica fabrica;
    private Connection connection; // Conexión a la base de datos

    public Vista(Fabrica fabrica) {
        this.fabrica = fabrica;
        initComponents();
        setupListeners();
        try {
            connection = ConexionBD.getConnection(); // Obtener conexión a la base de datos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    // Configuro los listeners para los botones de la interfaz
    private void setupListeners() {
        jBotonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jBotonEnviarActionPerformed(evt);
            }
        });

        jBotonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jBotonSalirActionPerformed(evt);
            }
        });
    }

    // Manejo las acciones cuando se presiona el botón "Enviar"
    private void jBotonEnviarActionPerformed(ActionEvent evt) {
        int opcion = jMenuDesple.getSelectedIndex();
        switch (opcion) {
            case 0:
                mostrarStockBD();
                break;
            case 1:
                fabricarProductoBD();
                break;
            case 2:
                procesarOrdenesPendientesBD();
                break;
            case 3:
                actualizarStockBD();
                break;
            case 4:
                mostrarMateriaPrimaMasUsadaBD();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Opción no válida. Intente nuevamente.");
                break;
        }
    }

    // Actualizo el stock de las materias primas en la base de datos
    private void actualizarStockBD() {
        try {
            List<MateriaPrima> materiasPrimas = MateriaPrima.obtenerTodas(connection);
            String[] opciones = new String[materiasPrimas.size() + 1]; // Una opción adicional para actualizar todas
            for (int i = 0; i < materiasPrimas.size(); i++) {
                opciones[i] = materiasPrimas.get(i).getNombre();
            }
            opciones[materiasPrimas.size()] = "Actualizar todas las materias primas";

            String seleccion = (String) JOptionPane.showInputDialog(this,
                    "Seleccione la materia prima a actualizar o 'Actualizar todas las materias primas':",
                    "Actualizar stock", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion != null) {
                if (seleccion.equals("Actualizar todas las materias primas")) {
                    String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar a cada materia prima:");
                    int cantidad;
                    try {
                        cantidad = Integer.parseInt(cantidadStr);
                        if (cantidad < 0) {
                            throw new NumberFormatException("Cantidad no válida.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Ingrese un numero positivo");
                        return;
                    }

                    for (MateriaPrima mp : materiasPrimas) {
                        Fabrica.actualizarStockEnBD(connection, mp.getNombre(), cantidad);
                    }
                    JOptionPane.showMessageDialog(this, "Stock actualizado para todas las materias primas.");
                } else {
                    MateriaPrima materiaPrimaSeleccionada = materiasPrimas.stream()
                            .filter(mp -> mp.getNombre().equals(seleccion)).findFirst().orElse(null);
                    if (materiaPrimaSeleccionada != null) {
                        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar:");
                        int cantidad;
                        try {
                            cantidad = Integer.parseInt(cantidadStr);
                            if (cantidad < 0) {
                                throw new NumberFormatException("Cantidad no válida.");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Ingrese un numero positivo");
                            return;
                        }

                        Fabrica.actualizarStockEnBD(connection, materiaPrimaSeleccionada.getNombre(), cantidad);
                        JOptionPane.showMessageDialog(this, "Stock actualizado para " + materiaPrimaSeleccionada.getNombre() + ".");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock desde la base de datos.");
            e.printStackTrace();
        }
    }

    // Proceso las órdenes de producción pendientes en la base de datos
    private void procesarOrdenesPendientesBD() {
        try {
            if (Fabrica.getOrdenesPendientesEnBD(connection).isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay órdenes pendientes para procesar.");
            } else {
                boolean todasProcesadas = true;
                for (OrdenProduccion orden : Fabrica.getOrdenesPendientesEnBD(connection)) {
                    if (!Fabrica.puedeRealizarseEnBD(connection, orden)) {
                        todasProcesadas = false;
                        break;
                    }
                }

                if (todasProcesadas) {
                    Fabrica.procesarOrdenesPendientesBD(connection);
                    JOptionPane.showMessageDialog(this, "Órdenes pendientes procesadas.");
                } else {
                    JOptionPane.showMessageDialog(this, "No hay suficiente stock para procesar la orden pendiente.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al procesar las órdenes pendientes desde la base de datos.");
            e.printStackTrace();
        }
    }

    // Muestro el stock actual de materias primas desde la base de datos
    private void mostrarStockBD() {
        try {
            connection = ConexionBD.getConnection();
            List<MateriaPrima> materiasPrimas = MateriaPrima.obtenerTodas(connection);
            StringBuilder sb = new StringBuilder("Stock actual de materias primas:\n");
            for (MateriaPrima mp : materiasPrimas) {
                sb.append(mp).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el stock desde la base de datos.");
            e.printStackTrace();
        }
    }

    // Manejo la fabricación de productos utilizando la base de datos
    private void fabricarProductoBD() {
    List<Producto> productos;

    try {
        connection = ConexionBD.getConnection();
        productos = Producto.obtenerTodos(connection);
        String[] opciones = productos.stream().map(Producto::getNombre).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, 
                    "Seleccione el producto a fabricar:", "Fabricar producto", 
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            Producto productoSeleccionado = productos.stream().filter(p -> 
            p.getNombre().equals(seleccion)).findFirst().orElse(null);
            if (productoSeleccionado != null) {
                String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a fabricar:");
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad < 0) {
                        throw new NumberFormatException("Cantidad no válida.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Cantidad no válida.");
                    return;
                }
                OrdenProduccion orden = new OrdenProduccion(productoSeleccionado, cantidad, false);
                if (Fabrica.puedeRealizarseEnBD(connection, orden)) {
                    Fabrica.realizarOrdenEnBD(connection, orden);
                    JOptionPane.showMessageDialog(this, "Orden realizada y stock actualizado.");
                } else {
                    Fabrica.agregarOrdenProduccionEnBD(connection, orden);
                    JOptionPane.showMessageDialog(this, "Orden agregada como pendiente por falta de stock.");
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al obtener los productos desde la base de datos.");
        e.printStackTrace();
    }
}

    // Muestro la materia prima más usada en la producción desde la base de datos
    private void mostrarMateriaPrimaMasUsadaBD() {
        try {
            String query =
                    "SELECT mp.nombre, SUM(op.cantidad * pm.cantidad) AS total_usado " +
                    "FROM OrdenProduccion op " +
                    "JOIN ProductoMateriaPrima pm ON op.producto = pm.producto " +
                    "JOIN MateriaPrima mp ON pm.materiaPrima = mp.nombre " +
                    "GROUP BY mp.nombre " +
                    "ORDER BY total_usado DESC";

            StringBuilder sb = new StringBuilder("Materia Prima más usada:\n");

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int totalUsado = rs.getInt("total_usado");
                    sb.append(nombre).append(": ").append(totalUsado).append("\n");
                }
            }

            if (sb.length() == "Materia Prima más usada:\n".length()) {
                JOptionPane.showMessageDialog(this, "No se encontraron datos de uso de materias primas.");
            } else {
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el uso de materias primas desde la base de datos.");
            e.printStackTrace();
        }
    }

    // Cierro la aplicación cuando se presiona el botón "Salir"
    private void jBotonSalirActionPerformed(ActionEvent evt) {
        System.exit(0);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuDesple = new javax.swing.JComboBox<>();
        jBotonSalir = new javax.swing.JButton();
        jBotonEnviar = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Bienvenidos a la fabrica de notebooks HP");

        jLabel2.setText("Elija una opcion:");

        jMenuDesple.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mostrar stock actual", "Fabricar producto", "Procesar ordenes pendientes", "Actualizar stock", "Materia prima más usada" }));
        jMenuDesple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDespleActionPerformed(evt);
            }
        });

        jBotonSalir.setText("Salir");

        jBotonEnviar.setText("Enviar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jMenuDesple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBotonSalir)
                            .addComponent(jBotonEnviar))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jMenuDesple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBotonEnviar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 191, Short.MAX_VALUE)
                        .addComponent(jBotonSalir)
                        .addGap(25, 25, 25))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuDespleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDespleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuDespleActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonEnviar;
    private javax.swing.JButton jBotonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> jMenuDesple;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
