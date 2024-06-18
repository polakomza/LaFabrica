package com.mycompany.lafabrica;

import java.util.ArrayList;
import java.util.Scanner;
public class LaFabrica {
/*
    nosotros vamos a vender en bolsas de 1kg o 1000 gramos
    1200 gramos de papa para 
    */
    static Scanner sc;
    public static void main(String[] args) {
        //OrdenProduccion orden = new OrdenProduccion(100, "papas clasicas", 2000);
        //OrdenProduccion orden2 = new OrdenProduccion(101, "papas en gajos", 3000);
        //System.out.println(orden2.producto.crear_producto());
        sc =  new Scanner(System.in);
        System.out.println("Bienvenido a la fabrica de Papas congeladas");
        int cantALlevar = 0;
        int codigoAIngresar = 0;
        String nombreProducto = "";
        boolean salida = false;
        while(salida != true) {
            mostrarProductos();
            System.out.println("Ingrese el código del producto a llevar");
            codigoAIngresar = sc.nextInt();
            sc.nextLine();
            switch (codigoAIngresar) {
                case 100:
                    nombreProducto = "Papas fritas";
                    cantALlevar = validarCantidad();
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 101:
                    nombreProducto = "Gajos de papas";
                    cantALlevar = validarCantidad();
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 102:
                    nombreProducto = "Papas al horno";
                    cantALlevar = validarCantidad();
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 103:
                    nombreProducto = "Tater tots";//bola de papa
                    cantALlevar = validarCantidad();
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                
                default:
                    System.out.println("Ingrese un codigo valido");
                    break;
                    
            }
            System.out.println("Nombre " + nombreProducto + ", codigo " + codigoAIngresar + " y cantidad " + cantALlevar);
        }
    }
    //Validamos que se ingrese el minimo pedido de los gramos (1000)
    public static int validarCantidad(){
        int cantidadALlevar;
        System.out.println("Ingrese en gramos la cantidad a preparar");
        System.out.println("Se pide como minimo 1000 gramos");
        while(true){
            cantidadALlevar = sc.nextInt();
            if(cantidadALlevar < 1000){
                System.out.println("La cantidad ingresada no supera el minimo permitido, ingrese nuevamente");
            }else{
                break;
            }
        }
        return cantidadALlevar;
    }
    //Saber si el usuario quiere o no otra cosa
    public static boolean encargarOtraCosa(int cantALlevar){
        boolean quiereOno = false;
        sc.nextLine();
        if(cantALlevar>1000){
            System.out.println("Desea comprar algo mas? Si o No");
            String salidaONo = sc.nextLine();
            quiereOno = !salidaONo.toLowerCase().equalsIgnoreCase("si");
        }
        return quiereOno;
    }
        //mostrar menu switch
        //ingrese el codigo del poducto a producir
        // codigo = 100
        //coloco 100 case 100: nombreProducto = "gnoquis";
        // ingrese la cantidad del producto en gramos
        //  cantidad = 2000
        // minimo de cantidad permitido
        //ordenVeficada = orden.verificarOrden();
        // if(ordenVerificada){
        //  sout("preparando orden")
        // }
        // else {
        //  sout("orden Pendiente por falta materia prima")
        //  orden.producto.procesar_ordenes_pendientes()
        // }
    public static void mostrarProductos(){

        ArrayList<OrdenProduccion> mostrar = new ArrayList<>();
        // validar cantidad minima en la orden de produccion
        mostrar.add(new OrdenProduccion(100, "Papas fritas", 20));
        mostrar.add(new OrdenProduccion(101, "Gajos de papas", 30));
        mostrar.add(new OrdenProduccion(102, "Papas al horno", 50));
        mostrar.add(new OrdenProduccion(103, "Tater tots", 15));
        
        System.out.println("Codigo    Producto   ");
        System.out.println("---------------------");
        for (OrdenProduccion ordenProduccion : mostrar) {
            String codigoSrt = String.valueOf(ordenProduccion.getCodigo());
            System.out.println(ordenProduccion.getCodigo() + completaEspaciosBlanco(codigoSrt, 10) 
            + ordenProduccion.getProducto_a_fabricar() +  completaEspaciosBlanco(ordenProduccion.getProducto_a_fabricar(), 10));
        }
    }
    private static String completaEspaciosBlanco(String palabra, int longitud) {
            int espacios = longitud - palabra.length();
            StringBuilder espacioBlanco = new StringBuilder();
            while (espacios > 0) {
                espacioBlanco.append(" ");
                --espacios;
            }
            return espacioBlanco.toString();
    }
}

