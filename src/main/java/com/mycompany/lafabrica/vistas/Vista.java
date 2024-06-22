package com.mycompany.lafabrica.vistas;

import com.mycompany.lafabrica.modelos.Fabrica;
import com.mycompany.lafabrica.modelos.OrdenProduccion;
import com.mycompany.lafabrica.modelos.Producto;
import com.mycompany.lafabrica.modelos.MateriaPrima;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Vista extends javax.swing.JFrame {

    private Fabrica fabrica;

    public Vista(Fabrica fabrica) {
        this.fabrica = fabrica;
        initComponents();
        setupListeners();
    }

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

    private void jBotonEnviarActionPerformed(ActionEvent evt) {
        int opcion = jMenuDesple.getSelectedIndex();
        switch (opcion) {
            case 0:
                mostrarStock();
                break;
            case 1:
                fabricarProducto();
                break;
            case 2:
                procesarOrdenesPendientes();
                break;
            case 3:
                actualizarStock();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Opción no válida. Intente nuevamente.");
                break;
        }
    }

    private void mostrarStock() {
        StringBuilder sb = new StringBuilder("Stock actual de materias primas:\n");
        for (MateriaPrima mp : fabrica.getMateriasPrimas()) {
            sb.append(mp).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void fabricarProducto() {
        List<Producto> productos = fabrica.getProductos();
        String[] opciones = productos.stream().map(Producto::getNombre).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione el producto a fabricar:", "Fabricar producto", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            Producto productoSeleccionado = productos.stream().filter(p -> p.getNombre().equals(seleccion)).findFirst().orElse(null);
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

                OrdenProduccion orden = new OrdenProduccion(productoSeleccionado, cantidad);
                if (fabrica.puedeRealizarse(orden)) {
                    fabrica.realizarOrden(orden);
                    JOptionPane.showMessageDialog(this, "Orden realizada y stock actualizado.");
                } else {
                    fabrica.agregarOrdenProduccion(orden);
                    JOptionPane.showMessageDialog(this, "Orden agregada como pendiente por falta de stock.");
                }
            }
        }
    }

    private void procesarOrdenesPendientes() {
    if (fabrica.getOrdenesPendientes().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay órdenes pendientes para procesar.");
    } else {
        boolean todasProcesadas = true;
        for (OrdenProduccion orden : fabrica.getOrdenesPendientes()) {
            if (!fabrica.puedeRealizarse(orden)) {
                todasProcesadas = false;
                break;
            }
        }

        if (todasProcesadas) {
            fabrica.procesarOrdenesPendientes();
            JOptionPane.showMessageDialog(this, "Órdenes pendientes procesadas.");
        } else {
            JOptionPane.showMessageDialog(this, "No hay suficiente stock para procesar la orden pendiente.");
        }
    }
}

    private void actualizarStock() {
    List<MateriaPrima> materiasPrimas = fabrica.getMateriasPrimas();
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
            actualizarStockDeTodasLasMateriasPrimas();
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
                    JOptionPane.showMessageDialog(this, "Cantidad no válida.");
                    return;
                }

                fabrica.actualizarStock(materiaPrimaSeleccionada.getNombre(), cantidad);
                JOptionPane.showMessageDialog(this, "Stock actualizado para " + materiaPrimaSeleccionada.getNombre() + ".");
                }
            }
        }
    }

    private void actualizarStockDeTodasLasMateriasPrimas() {
        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar a cada materia prima:");
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

        for (MateriaPrima mp : fabrica.getMateriasPrimas()) {
            fabrica.actualizarStock(mp.getNombre(), cantidad);
        }
        JOptionPane.showMessageDialog(this, "Stock actualizado para todas las materias primas.");
    }

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

        jMenuDesple.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mostrar stock actual", "Fabricar producto", "Procesar ordenes pendientes", "Actualizar stock" }));
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
