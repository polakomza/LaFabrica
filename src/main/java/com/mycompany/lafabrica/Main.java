
package com.mycompany.pruebachatgpt;

import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica();
        Scanner scanner = new Scanner(System.in);

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

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar stock actual");
            System.out.println("2. Fabricar producto");
            System.out.println("3. Procesar órdenes pendientes");
            System.out.println("4. Actualizar stock de materia prima");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarStock(fabrica);
                    break;
                case 2:
                    fabricarProducto(fabrica, scanner);
                    break;
                case 3:
                    fabrica.procesarOrdenesPendientes();
                    break;
                case 4:
                    actualizarStock(fabrica, scanner);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }


    public static void mostrarStock(Fabrica fabrica) {
        System.out.println("\nStock actual de materias primas:");
        for (MateriaPrima mp : fabrica.getMateriasPrimas()) {
            System.out.println(mp);
        }
    }

    public static void fabricarProducto(Fabrica fabrica, Scanner scanner) {
        System.out.println("\nSeleccione el producto a fabricar:");
        List<Producto> productos = fabrica.getProductos();
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i).getNombre());
        }
        int seleccionProducto = scanner.nextInt() - 1;

        if (seleccionProducto >= 0 && seleccionProducto < productos.size()) {
            Producto productoSeleccionado = productos.get(seleccionProducto);
            System.out.print("Ingrese la cantidad a fabricar: ");
            int cantidad = scanner.nextInt();

            OrdenProduccion orden = new OrdenProduccion(productoSeleccionado, cantidad);
            
            if (puedeRealizarse(fabrica, orden)) {
                realizarOrden(fabrica, orden);
                System.out.println("Orden realizada y stock actualizado.");
            } else {
                fabrica.agregarOrdenProduccion(orden);
                System.out.println("Orden agregada como pendiente por falta de stock.");
            }
        } else {
            System.out.println("Selección no válida.");
        }
    }

    public static boolean puedeRealizarse(Fabrica fabrica, OrdenProduccion orden) {
        Producto producto = orden.getProducto();
        for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
            MateriaPrima materiaPrima = entrada.getKey();
            int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
            if (materiaPrima.getCantidad() < cantidadNecesaria) {
                return false;
            }
        }
        return true;
    }

    public static void realizarOrden(Fabrica fabrica, OrdenProduccion orden) {
        Producto producto = orden.getProducto();
        for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
            MateriaPrima materiaPrima = entrada.getKey();
            int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
            materiaPrima.setCantidad(materiaPrima.getCantidad() - cantidadNecesaria);
        }
        orden.setPendiente(false);
    }

    public static void actualizarStock(Fabrica fabrica, Scanner scanner) {
        List<MateriaPrima> materiasPrimas = fabrica.getMateriasPrimas();
        System.out.println("\nSeleccione la materia prima a actualizar:");
        for (int i = 0; i < materiasPrimas.size(); i++) {
            System.out.println((i + 1) + ". " + materiasPrimas.get(i).getNombre());
        }
        System.out.println((materiasPrimas.size() + 1) + ". Actualizar todas las materias primas");

        int seleccionMateriaPrima = scanner.nextInt() - 1;

        if (seleccionMateriaPrima >= 0 && seleccionMateriaPrima < materiasPrimas.size()) {
            MateriaPrima materiaPrimaSeleccionada = materiasPrimas.get(seleccionMateriaPrima);
            System.out.print("Ingrese la cantidad a agregar: ");
            int cantidad = scanner.nextInt();
            while (cantidad < 0) {
                System.out.println("Cantidad no válida. Intente nuevamente.");
                System.out.print("Ingrese la cantidad a agregar: ");
                cantidad = scanner.nextInt();
            }
            materiaPrimaSeleccionada.setCantidad(materiaPrimaSeleccionada.getCantidad() + cantidad);
            System.out.println("Stock actualizado.");
        } else if (seleccionMateriaPrima == materiasPrimas.size()) {
            System.out.print("Ingrese la cantidad a agregar a todas las materias primas: ");
            int cantidad = scanner.nextInt();
            while (cantidad < 0) {
                System.out.println("Cantidad no válida. Intente nuevamente.");
                System.out.print("Ingrese la cantidad a agregar a todas las materias primas: ");
                cantidad = scanner.nextInt();
            }
            for (MateriaPrima mp : materiasPrimas) {
                mp.setCantidad(mp.getCantidad() + cantidad);
            }
            System.out.println("Stock de todas las materias primas actualizado.");
        } else {
            System.out.println("Selección no válida.");
        }
    }
}


/*
public class Main {
    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica();

        MateriaPrima mp1 = new MateriaPrima("Hierro", 1000);
        MateriaPrima mp2 = new MateriaPrima("Carbon", 500);
        fabrica.agregarMateriaPrima(mp1);
        fabrica.agregarMateriaPrima(mp2);

        Producto producto1 = new Producto("Acero");
        producto1.agregarMateriaPrima(mp1, 2);
        producto1.agregarMateriaPrima(mp2, 1);
        fabrica.agregarProducto(producto1);

        Producto producto2 = new Producto("Aleacion");
        producto2.agregarMateriaPrima(mp1, 1);
        producto2.agregarMateriaPrima(mp2, 2);
        fabrica.agregarProducto(producto2);

        OrdenProduccion orden1 = new OrdenProduccion(producto1, 100);
        OrdenProduccion orden2 = new OrdenProduccion(producto2, 50);
        fabrica.agregarOrdenProduccion(orden1);
        fabrica.agregarOrdenProduccion(orden2);

        fabrica.procesarOrdenesPendientes();

        List<Producto> productosPorMP1 = fabrica.listarProductosPorMateriaPrima(mp1);
        for (Producto producto : productosPorMP1) {
            System.out.println(producto.getNombre());
        }
    }
}*/