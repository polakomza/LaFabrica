package com.mycompany.lafabrica;

import java.util.ArrayList;
//import javax.swing.JOptionPane;
public class LaFabrica {

    public static void main(String[] args) {

        ArrayList<MateriaPrima> mat_prima = MateriaPrima.crearListaProductos();

        for (MateriaPrima i : mat_prima) {
            System.out.println(i);
            //JOptionPane.showInputDialog(null,"ñoquis");
        }

    }
}

