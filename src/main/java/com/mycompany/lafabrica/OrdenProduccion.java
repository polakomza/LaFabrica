package com.mycompany.lafabrica;

import java.util.ArrayList;

/*Las órdenes de producción indican
la cantidad de unidades de un 
determinado producto que hay
 que fabricar. */

public class OrdenProduccion {
    private String producto_a_fabricar;
    private boolean cumplida;
    private int cantidad;
    public Producto producto;
    private int codigo;

    public OrdenProduccion(int codigo, String producto_a_fabricar, int cantidad) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.producto = new Producto(producto_a_fabricar);
    }

    public OrdenProduccion(String producto_a_fabricar, boolean cumplida, int cantidad) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cumplida = cumplida;
        this.cantidad = cantidad;
        this.producto = new Producto(producto_a_fabricar);
    }



    public boolean verificarOrden(){
        //recibira por parametro lo que retorne de crear producto
        return producto.crear_producto();
    }

    @Override
    public String toString() {
        return "OrdenProduccion: " +
                "producto_a_fabricar=' " + producto_a_fabricar + '\'' +
                ", codigo= " + codigo;
    }
}
