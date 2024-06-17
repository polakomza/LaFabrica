
package com.mycompany.lafabrica;


public class OrdenProduccion {
    public String producto_a_fabricar;
    public boolean cumplida;
    public int cantidad;
    public Producto producto;

    public OrdenProduccion(String producto_a_fabricar, boolean cumplida, int cantidad, Producto producto) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cumplida = cumplida;
        this.cantidad = cantidad;
        this.producto = producto;
    }
    
    public boolean verificar_cumplido(){
        //recibira por parametro lo que retorne de crear producto
        return false;
    }
}
