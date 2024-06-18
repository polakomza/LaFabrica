package com.mycompany.lafabrica;

import java.util.ArrayList;

public class MateriaPrima {
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
    
    public static ArrayList<MateriaPrima> crearListaIngredientes() {
        ArrayList<MateriaPrima> ingredientes = new ArrayList<>();
        ingredientes.add(new MateriaPrima("Harina", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Huevos", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Aceite", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Sal", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Pollo", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Ricota", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Jamon y Queso", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Carne", 10000, "gramos"));
        ingredientes.add(new MateriaPrima("Papa", 10000, "gramos"));
        return ingredientes;
    }
    
    @Override
    public String toString() {
        return "MateriaPrima: " +
                "nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", unidadDeMedida='" + unidadDeMedida + '\'';
    }
}
