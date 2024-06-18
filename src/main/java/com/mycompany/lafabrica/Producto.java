package com.mycompany.lafabrica;
import java.util.ArrayList;


public class Producto {
    public String nombre;
    public ArrayList <MateriaPrima>mat_primas = MateriaPrima.crearListaIngredientes();

    public Producto(String nombre) {

        this.nombre = nombre;
    }
    
    public static void procesar_ordenes_pendientes(){
        //Procesar las ordenes sin completar
    }
    
    public Producto cantidad_productos(ArrayList<MateriaPrima>mat_primas){
        //Consultar a la base de datos para las materias primas mas utilizadas
        return Producto.this;
    }
    
    public boolean crear_producto(){
        //formula de fabricacion
        //Debe guardarse en la base de datos
        return false;
    }
}
