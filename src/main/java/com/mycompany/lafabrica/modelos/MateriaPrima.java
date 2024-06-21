
package com.mycompany.lafabrica.modelos;


public class MateriaPrima {
    private String nombre;
    private int cantidad;

    public MateriaPrima(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + ": " + cantidad;
    }
}