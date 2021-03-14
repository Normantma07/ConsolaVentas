/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaventas;

import java.io.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.GregorianCalendar;
import consolaventas.Catalogo;
import consolaventas.log;


/**
 *
 * @author Cesia Coto
 */
public class Ventas {

    private log lo;

    public void mostrarCatalogoExistencia(Catalogo[] catalogo) {
        System.out.println("id nombre cantidad");
        for (int i = 0; i < 20; i++) {
            System.out.println(catalogo[i].Getid() + "-" + catalogo[i].GetName() + " " + catalogo[i].GetUnidades());
        }
    }

    public void consultarInventario(Catalogo[] catalogo, login l) {
        Scanner scn = new Scanner(System.in);
        String palabra;
        int id = 0, cont = 0;
        boolean isNumber = false;
        boolean encontrado = false;
        System.out.println("Introduce el nombre del producto o el id para buscar un producto en concreto");
        System.out.println("o introduce 'TODOS' si deseas ver las existencias de todos los productos");
        palabra = scn.nextLine();
        palabra = palabra.toLowerCase();
        if (palabra.equals("todos")) {
            mostrarCatalogoExistencia(catalogo);
        } else {
            try {
                id = Integer.parseInt(palabra);
                isNumber = true;
            } catch (Exception e) {
                e.getCause();
                lo.setContenido(e.getMessage());
                lo.escribirLog(l.getUsuario());

            }
            if (isNumber && id > 0 && id <= 20) {
                System.out.println("id nombre cantidad");
                System.out.println(catalogo[id - 1].Getid() + "-" + catalogo[id - 1].GetName() + " " + catalogo[id - 1].GetUnidades());
            } else {
                if (isNumber) {
                    System.out.println("Error, el id del producto seleccionado no existe.");
                } else {
                    while (cont < 20 && !encontrado) {
                        if (catalogo[cont].GetName().equals(palabra)) {
                            encontrado = true;

                        } else {
                            cont++;
                        }
                    }
                    if (encontrado) {
                        System.out.println("id nombre cantidad");
                        System.out.println(catalogo[cont].Getid() + "-" + catalogo[cont].GetName() + " " + catalogo[cont].GetUnidades());
                    } else {
                        System.out.println("Error, la entrada de datos no corresponde con ningun producto");
                    }
                }
            }
        }
    }

}
