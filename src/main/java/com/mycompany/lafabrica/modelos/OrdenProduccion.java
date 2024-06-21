package com.mycompany.lafabrica;;

class OrdenProduccion {
    private Producto producto;
    private int cantidad;
    private boolean pendiente;

    public OrdenProduccion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pendiente = true;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }
}