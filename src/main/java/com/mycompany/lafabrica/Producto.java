package com.mycompany.lafabrica;
import java.util.ArrayList;


public class Producto {
    public String nombre;
    public int cantidad;
    public ArrayList <MateriaPrima>mat_primas = MateriaPrima.crearListaIngredientes();

    public Producto(String nombre, int cantidad) {

        this.nombre = nombre;
        this.cantidad = cantidad/1000;
    }
    
    public static void procesar_ordenes_pendientes(){
        //Procesar las ordenes sin completar
    }
    
    public Producto cantidad_productos(ArrayList<MateriaPrima>mat_primas){
        //Consultar a la base de datos para las materias primas mas utilizadas
        return Producto.this;
    }

    public void verMateriaPrima() {
        for (MateriaPrima i : mat_primas){
            System.out.println(i);
        }
    }
    
    public boolean crear_producto(){
        //formula de fabricacion

        System.out.println("nombre: "+nombre);

        // diferencia de stock tomando en cuenta el nombre del producto y la cantidad de produccion
        if (nombre == "papas clasicas") {
            for (MateriaPrima i : mat_primas) {
                    if (i.getNombre() == "Papa"){
                        i.setStock(i.getStock() - 1200*cantidad);
                    }
            }
        }


        //Debe guardarse en la base de datos
        return false;
    }
}
