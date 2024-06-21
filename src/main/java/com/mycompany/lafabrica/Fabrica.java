/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebachatgpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fabrica {
    private List<MateriaPrima> materiasPrimas;
    private List<Producto> productos;
    private List<OrdenProduccion> ordenesPendientes;

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

    public void agregarOrdenProduccion(OrdenProduccion orden) {
        ordenesPendientes.add(orden);
    }

    public List<MateriaPrima> getMateriasPrimas() {
        return materiasPrimas;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void actualizarStock(String nombre, int cantidad) {
        for (MateriaPrima mp : materiasPrimas) {
            if (mp.getNombre().equalsIgnoreCase(nombre)) {
                mp.setCantidad(mp.getCantidad() + cantidad);
                return;
            }
        }
        System.out.println("Materia prima no encontrada.");
    }

    public void procesarOrdenesPendientes() {
        List<OrdenProduccion> ordenesCompletadas = new ArrayList<>();
        if(ordenesPendientes.isEmpty()){
            System.out.println("No hay ordenes pendientes para procesar");
        }else{
            for (OrdenProduccion orden : ordenesPendientes) {
                if (puedeRealizarse(orden)) {
                    realizarOrden(orden);
                    ordenesCompletadas.add(orden);
                    System.out.println("Orden procesada");
                } else {
                    System.out.println("No hay suficiente stock para procesar la orden pendiente");
                }
            }
        }
        ordenesPendientes.removeAll(ordenesCompletadas);
    }

    private boolean puedeRealizarse(OrdenProduccion orden) {
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

    private void realizarOrden(OrdenProduccion orden) {
        Producto producto = orden.getProducto();
        for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
            MateriaPrima materiaPrima = entrada.getKey();
            int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
            materiaPrima.setCantidad(materiaPrima.getCantidad() - cantidadNecesaria);
        }
        orden.setPendiente(false);
    }
}


/*
public class Fabrica {
    private List<MateriaPrima> materiasPrimas;
    private List<Producto> productos;
    private List<OrdenProduccion> ordenesPendientes;

    public Fabrica() {
        this.materiasPrimas = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ordenesPendientes = new ArrayList<>();
    }

    public void agregarMateriaPrima(MateriaPrima materiaPrima) {
        materiasPrimas.add(materiaPrima);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void agregarOrdenProduccion(OrdenProduccion orden) {
        ordenesPendientes.add(orden);
    }

    public MateriaPrima buscarMateriaPrima(String nombre) {
        for (MateriaPrima mp : materiasPrimas) {
            if (mp.getNombre().equals(nombre)) {
                return mp;
            }
        }
        return null;
    }

    public void procesarOrdenesPendientes() {
        List<OrdenProduccion> ordenesRealizadas = new ArrayList<>();
        for (OrdenProduccion orden : ordenesPendientes) {
            Producto producto = orden.getProducto();
            boolean puedeRealizarse = true;

            for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
                MateriaPrima materiaPrima = entrada.getKey();
                int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
                if (materiaPrima.getCantidad() < cantidadNecesaria) {
                    puedeRealizarse = false;
                    break;
                }
            }

            if (puedeRealizarse) {
                for (Map.Entry<MateriaPrima, Integer> entrada : producto.getFormula().entrySet()) {
                    MateriaPrima materiaPrima = entrada.getKey();
                    int cantidadNecesaria = entrada.getValue() * orden.getCantidad();
                    materiaPrima.setCantidad(materiaPrima.getCantidad() - cantidadNecesaria);
                }
                orden.setPendiente(false);
                ordenesRealizadas.add(orden);
            }
        }
        ordenesPendientes.removeAll(ordenesRealizadas);
    }

    public List<Producto> listarProductosPorMateriaPrima(MateriaPrima materiaPrima) {
        List<Producto> productosUtilizanMateriaPrima = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getFormula().containsKey(materiaPrima)) {
                productosUtilizanMateriaPrima.add(producto);
            }
        }
        productosUtilizanMateriaPrima.sort((p1, p2) -> 
            p2.getFormula().get(materiaPrima) - p1.getFormula().get(materiaPrima)
        );
        return productosUtilizanMateriaPrima;
    }
}*/