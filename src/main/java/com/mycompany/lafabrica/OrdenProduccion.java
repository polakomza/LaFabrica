package com.mycompany.lafabrica;

import java.util.ArrayList;

/*Las órdenes de producción indican
la cantidad de unidades de un 
determinado producto que hay
 que fabricar. */

public class OrdenProduccion {
    public String producto_a_fabricar;
    private boolean cumplida;
    public int cantidad;
    public Producto producto;
    public int codigo;

    public OrdenProduccion(int codigo, String producto_a_fabricar, int cantidad) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.producto = new Producto(producto_a_fabricar,cantidad);
    }

    public OrdenProduccion(String producto_a_fabricar, boolean cumplida, int cantidad) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cumplida = cumplida;
        this.cantidad = cantidad;
        this.producto = new Producto(producto_a_fabricar, cantidad);
    }
    
    public boolean verificarOrden(){
        //recibira por parametro lo que retorne de crear producto
        return producto.crear_producto();
    }

    public String getProducto_a_fabricar() {
        return producto_a_fabricar;
    }
    public void setProducto_a_fabricar(String producto_a_fabricar) {
        this.producto_a_fabricar = producto_a_fabricar;
    }

    public boolean isCumplida() {
        return cumplida;
    }
    public void setCumplida(boolean cumplida) {
        this.cumplida = cumplida;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
}
