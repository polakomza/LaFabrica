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
    
    public void procesarOrdenesPendientes(){
        //Procesar las ordenes sin completar
        System.out.println("no hay suficiente stock de la materia prima {papas} para la cantidad ingresada");
        System.out.println("ingrese el stock de la materia prima");
        //scanner actualizar stock
    }
    
    public Producto cantidadProductos(ArrayList<MateriaPrima>mat_primas){
        //Consultar a la base de datos para las materias primas mas utilizadas
        return Producto.this;
    }

    public void verMateriaPrima() {
        for (MateriaPrima i : mat_primas){
            System.out.println(i);
        }
    }
    
    public boolean crearProducto(){

        // diferencia de stock tomando en cuenta el nombre del producto y la cantidad de produccion
        if (nombre == "Papas fritas") {
            modificarStockMateriaPrima(1200,125,15,3);
            return true;
        } else if (nombre == "Gajos de papas") {
            modificarStockMateriaPrima(1000,38, 5,3);
            return true;
        } else if (nombre == "Papas al horno") {
            modificarStockMateriaPrima(1200, 50, 10, 3);
            return true;
        } else if (nombre == "Tater tots") {
            modificarStockMateriaPrima(850, 35, 7,3);
            return true;
        }else {
            System.out.println("Producto inexistente");
            return false;
        }
        //Debe guardarse en la base de datos
    }

    public void modificarStockMateriaPrima(int papa, int aceite, int sal, int pads) {
        for (MateriaPrima matPri: mat_primas) {
            if (matPri.getNombre() == "Papa"){
                matPri.setStock(matPri.getStock() - papa*cantidad);
            } else if (matPri.getNombre() == "Aceite") {
                matPri.setStock(matPri.getStock() - aceite*cantidad);
            } else if (matPri.getNombre() == "Sal") {
                    matPri.setStock(matPri.getStock()- sal*cantidad);
            } else if (matPri.getNombre() == "Pirofosfato ácido de sodio") {
                    matPri.setStock(matPri.getStock()-pads*cantidad);
            }else {
                System.out.println("materia prima Inexistente");
            }
        }
        System.out.println("Se actualizo el stock de la materia prima del producto: " + nombre);
    }
}
