package com.mycompany.lafabrica;

import java.util.ArrayList;
import java.util.Random;

public class MateriaPrima {
    private static Random random = new Random();
    private String nombre;
    private int stock;
    private String unidadDeMedida;

    public MateriaPrima(String nombre, int stock, String unidadDeMedida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidadDeMedida = unidadDeMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    
    public static ArrayList<MateriaPrima> crearListaProductos() {
        ArrayList<MateriaPrima> ingredientes = new ArrayList<>();
        ingredientes.add(new MateriaPrima("Harina", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Huevos", random.nextInt(50), "unidades"));
        ingredientes.add(new MateriaPrima("Aceite", random.nextInt(50), "litros"));
        ingredientes.add(new MateriaPrima("Sal", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Pollo", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Ricota", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Jamon y Queso", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Carne", random.nextInt(50), "kg"));
        ingredientes.add(new MateriaPrima("Papa", random.nextInt(50), "kg"));
        return ingredientes;
    }
    
    @Override
    public String toString() {
        return "MateriaPrima{" +
                "nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", unidadDeMedida='" + unidadDeMedida + '\'' +
                '}';
    }
}
