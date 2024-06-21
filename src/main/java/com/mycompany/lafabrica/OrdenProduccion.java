/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebachatgpt;

class OrdenProduccion {
    private Producto producto;
    private int cantidad;
    private boolean pendiente;

    public OrdenProduccion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pendiente = true;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }
}
/*public class OrdenProduccion {
    private Producto producto;
    private int cantidad;
    private boolean pendiente;

    public OrdenProduccion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pendiente = true;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }
}
*/