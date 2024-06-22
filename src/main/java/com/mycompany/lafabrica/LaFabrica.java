package com.mycompany.lafabrica;

import com.mycompany.lafabrica.modelos.Fabrica;
import com.mycompany.lafabrica.modelos.MateriaPrima;
import com.mycompany.lafabrica.modelos.OrdenProduccion;
import com.mycompany.lafabrica.modelos.Producto;
import com.mycompany.lafabrica.vistas.Vista;

import javax.swing.SwingUtilities;

public class LaFabrica {
    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica();

        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Vista view = new Vista(fabrica);
                view.setVisible(true);
                view.setLocationRelativeTo(null);
            }
        });
    }
}