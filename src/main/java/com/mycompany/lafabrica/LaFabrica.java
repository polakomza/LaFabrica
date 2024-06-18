package com.mycompany.lafabrica;

import java.util.ArrayList;
//import javax.swing.JOptionPane;
public class LaFabrica {

    public static void main(String[] args) {

        /*public static ArrayList<OrdenProduccion> mostrarProductos(){
            ArrayList<OrdenProduccion> mostrar = new ArrayList<>();
            // validar cantidad minima en la orden de produccion
            mostrar.add(new OrdenProduccion(100, "Gnoquis", 20));
            mostrar.add(new OrdenProduccion(101, "Fideos Rellenos", 30));
            mostrar.add(new OrdenProduccion(102, "Spaggetti", 50));
            mostrar.add(new OrdenProduccion(103, "Ravioles", 15));
            mostrar.add(new OrdenProduccion(104, "Sorrentinos", 28));
            mostrar.add(new OrdenProduccion(105, "Capeletinni", 28));

            return mostrar;
        }*/
        //mostrar menu switch
        //ingrese el codigo del poducto a producir
        // codigo = 100
        //coloco 100 case 100: nombreProducto = "gnoquis";
        // ingrese la cantidad del producto en gramos
        //  cantidad = 2000
        // minimo de cantidad permitido
        OrdenProduccion orden = new OrdenProduccion(100, "papas clasicas", 2000);
        OrdenProduccion orden2 = new OrdenProduccion(101, "papas en gajos", 3000);


        //ordenVeficada = orden.verificarOrden();
        // if(ordenVerificada){
        //  sout("preparando orden")
        // }
        // else {
        //  sout("orden Pendiente por falta materia prima")
        //  orden.producto.procesar_ordenes_pendientes()
        // }

        orden.producto.verMateriaPrima();
        System.out.println(orden.producto.crear_producto());
        orden.producto.verMateriaPrima();
        //System.out.println(orden2);
        //orden.producto.verMateriaPrima();

    }
}

