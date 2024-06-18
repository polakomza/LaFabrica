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
    private Producto producto;
    private String codigo;
    public OrdenProduccion() {
    }
    
    public OrdenProduccion(String producto_a_fabricar, boolean cumplida, int cantidad, Producto producto) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cumplida = cumplida;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public OrdenProduccion(String codigo, String producto_a_fabricar, int cantidad) {
        this.producto_a_fabricar = producto_a_fabricar;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }
    
    
    public static ArrayList<OrdenProduccion> mostrarProductos(){
        ArrayList<OrdenProduccion> mostrar = new ArrayList<>();
        mostrar.add(new OrdenProduccion("100", "Gnoquis", 20));
        mostrar.add(new OrdenProduccion("101", "Fideos Rellenos", 30));
        mostrar.add(new OrdenProduccion("102", "Spaggetti", 50));
        mostrar.add(new OrdenProduccion("103", "Ravioles", 15));
        mostrar.add(new OrdenProduccion("104", "Sorrentinos", 28));
        mostrar.add(new OrdenProduccion("105", "Capeletinni", 28));
        
        return mostrar;
    }
    public boolean verificar_cumplido(){
        //recibira por parametro lo que retorne de crear producto
        return false;
    }
}
