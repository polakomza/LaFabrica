package com.mycompany.lafabrica;

import java.util.ArrayList;
import java.util.Scanner;
public class LaFabrica { 
    
    static Scanner sc;
    public static void main(String[] args) {
        ArrayList<MateriaPrima> ingredientes = new ArrayList<>();
        ingredientes.add(new MateriaPrima("Papa", 10000, "gramos")); //1200 gramos para 1000 gramos de papa
        ingredientes.add(new MateriaPrima("Aceite", 10000, "gramos"));//125 gramos
        ingredientes.add(new MateriaPrima("Sal", 10000, "gramos"));//de 15 gramos
        ingredientes.add(new MateriaPrima("Pirofosfato ácido de sodio", 1000, "gramos"));// 3 gramo
        
        
        sc = new Scanner(System.in);
        while(true){
            System.out.println("Bienvenido a la fabrica de Papas congeladas");
            System.out.println("Ingrese: \n"
                        + " 1. Para mostrar menu \n"
                        + " 2. Para actualizar stock \n"
                        + " 3. Para salir");
            System.out.print("Elija la opcion que desee: ");
            int opcion =  sc.nextInt();
            switch (opcion){
                case 1:
                    mostrarMenu();
                    continue;
                case 2:
                    actualizarStock(ingredientes);
                    continue;
                case 3:
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Ingrese una opcion correcta");
                    break;
            }
            break;
        }
    }
    public static void actualizarStock(ArrayList<MateriaPrima> ingredientes){
        
        System.out.println("La materia prima es: ");
        int cont = 1;
        for (MateriaPrima mat_prima : ingredientes) {
            System.out.println(cont + ". "+mat_prima.getNombre());
            cont++;
        }
        //se puede usar try catch
        System.out.println("Ingrese el numero de la materia prima a actualizar");
        int opcion = sc.nextInt();
        MateriaPrima seleccionadaMP = ingredientes.get(opcion-1);
        System.out.println("Tu stock actual es de: " + seleccionadaMP.getStock());
        System.out.println("Ahora indique el stock de " + seleccionadaMP.getNombre() + " a agregar");
        System.out.println("Se requiere un minimo de 1000 gramos");
        int stockNuevoFinal = 0;
        while(true){
            int stockNuevo = sc.nextInt();
            if(stockNuevo < 1000){
                System.out.println("Porfavor ingrese un numero valido");
            }else{
                stockNuevoFinal = (seleccionadaMP.getStock()+ stockNuevo);
                seleccionadaMP.setStock(stockNuevoFinal);
                break;
            }
        }
        System.out.println("Se actualizo el stock de " + seleccionadaMP.getNombre());
        System.out.println("Ahora su stock actual es de: " + stockNuevoFinal);
    }
    public static void mostrarMenu(){

        int cantALlevar = 0;
        int codigoAIngresar = 0;
        String nombreProducto = "";
        boolean salida = false;
        OrdenProduccion orden = null;
        while (salida != true) {
            mostrarProductos();
            System.out.println("Ingrese el código del producto a fabricar");
            codigoAIngresar = sc.nextInt();
            sc.nextLine();
            switch (codigoAIngresar) {
                case 100:
                    nombreProducto = "Papas fritas";
                    cantALlevar = validarCantidad();
                    orden = new OrdenProduccion(codigoAIngresar, nombreProducto, cantALlevar);
                    nuevaOrden(orden);
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 101:
                    nombreProducto = "Gajos de papas";
                    cantALlevar = validarCantidad();
                    orden = new OrdenProduccion(codigoAIngresar, nombreProducto, cantALlevar);
                    nuevaOrden(orden);
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 102:
                    nombreProducto = "Papas al horno";
                    cantALlevar = validarCantidad();
                    orden = new OrdenProduccion(codigoAIngresar, nombreProducto, cantALlevar);
                    nuevaOrden(orden);
                    salida = encargarOtraCosa(cantALlevar);
                    break;
                case 103:
                    nombreProducto = "Tater tots";//bola de papa
                    cantALlevar = validarCantidad();
                    orden = new OrdenProduccion(codigoAIngresar, nombreProducto, cantALlevar);
                    nuevaOrden(orden);
                    salida = encargarOtraCosa(cantALlevar);
                    break;

                default:
                    System.out.println("Ingrese un codigo valido");
                    break;
            }
            
        }
    }

    //Crear nueva orden de produccion
    public static void nuevaOrden(OrdenProduccion ordenProduccion){
        if (ordenProduccion.verificarStock()){
            ordenProduccion.producto.crearProducto();
        }else {
            ordenProduccion.producto.procesarOrdenesPendientes();
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
        if(cantALlevar>=1000){
            System.out.println("Desea comprar algo mas? Si o No");
            String salidaONo = sc.nextLine();
            quiereOno = !salidaONo.equalsIgnoreCase("si");
        }
        return quiereOno;
    }
    
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

