package com.mycompany.lafabrica;

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
    
    
    @Override
    public String toString() {
        return "MateriaPrima: " +
                "nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", unidadDeMedida='" + unidadDeMedida + '\'';
    }
}
