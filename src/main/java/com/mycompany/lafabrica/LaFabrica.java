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

        // Crear materias primas (componentes de la PC)
        MateriaPrima procesador = new MateriaPrima("Procesador", 100);
        MateriaPrima memoriaRAM = new MateriaPrima("Memoria RAM", 200);
        MateriaPrima discoDuro = new MateriaPrima("Disco Duro", 150);
        MateriaPrima tarjetaMadre = new MateriaPrima("Tarjeta Madre", 100);
        MateriaPrima fuentePoder = new MateriaPrima("Fuente de Poder", 100);
        MateriaPrima gabinete = new MateriaPrima("Gabinete", 100);
        MateriaPrima placaVideo = new MateriaPrima("Placa de video", 100);

        // Agregar materias primas a la fábrica
        fabrica.agregarMateriaPrima(procesador);
        fabrica.agregarMateriaPrima(memoriaRAM);
        fabrica.agregarMateriaPrima(discoDuro);
        fabrica.agregarMateriaPrima(tarjetaMadre);
        fabrica.agregarMateriaPrima(fuentePoder);
        fabrica.agregarMateriaPrima(gabinete);
        fabrica.agregarMateriaPrima(placaVideo);

        // Crear producto (PC Completa) y definir su fórmula de fabricación
        Producto notebook1 = new Producto("Notebook de oficina");
        notebook1.agregarMateriaPrima(procesador, 1);
        notebook1.agregarMateriaPrima(memoriaRAM, 2);
        notebook1.agregarMateriaPrima(discoDuro, 1);
        notebook1.agregarMateriaPrima(tarjetaMadre, 1);
        notebook1.agregarMateriaPrima(fuentePoder, 1);
        notebook1.agregarMateriaPrima(gabinete, 1);
        notebook1.agregarMateriaPrima(placaVideo, 0);

        // Agregar producto a la fábrica
        fabrica.agregarProducto(notebook1);

        Producto notebook2 = new Producto("Notebook Gamer");
        notebook2.agregarMateriaPrima(procesador, 1);
        notebook2.agregarMateriaPrima(memoriaRAM, 2);
        notebook2.agregarMateriaPrima(discoDuro, 2);
        notebook2.agregarMateriaPrima(tarjetaMadre, 1);
        notebook2.agregarMateriaPrima(fuentePoder, 1);
        notebook2.agregarMateriaPrima(gabinete, 1);
        notebook2.agregarMateriaPrima(placaVideo, 1);

        fabrica.agregarProducto(notebook2);

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