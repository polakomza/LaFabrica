
package com.mycompany.lafabrica.modelos;

import java.util.HashMap;
import java.util.Map;
public class Producto {
    private String nombre;
    private Map<MateriaPrima, Integer> formula;

    public Producto(String nombre) {
        this.nombre = nombre;
        this.formula = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Map<MateriaPrima, Integer> getFormula() {
        return formula;
    }

    public void agregarMateriaPrima(MateriaPrima materiaPrima, int cantidad) {
        formula.put(materiaPrima, cantidad);
    }

    @Override
    public String toString() {
        return nombre;
    }
}