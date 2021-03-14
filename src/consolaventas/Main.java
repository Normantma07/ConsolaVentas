/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaventas;

import java.util.Scanner;
import consolaventas.login;
import consolaventas.Menu;
import consolaventas.Ventas;
import consolaventas.bitacora;
import consolaventas.log;

/**
 *
 * @author Cesia Coto
 */
public class Main {

    public static void main(String[] args) {
        login l = new login();
        bitacora b = new bitacora();
        Menu m2 = new Menu();
        Ventas[] v = new Ventas[10];
        v[0] = new Ventas();
        int contVentas = 0;
        log lo = new log();
        Catalogo[] catalogo = new Catalogo[10];
        catalogo[0] = new Catalogo(1, "huevos", 0.1, 30);
        catalogo[1] = new Catalogo(2, "pollo", 5.0, 5);
        catalogo[2] = new Catalogo(3, "aceite", 3.00, 60);
        catalogo[3] = new Catalogo(4, "fosforos", 0.5, 100);
        catalogo[4] = new Catalogo(5, "dulces", 0.8, 500);
        catalogo[5] = new Catalogo(6, "margarina", 0.3, 20);
        catalogo[6] = new Catalogo(7, "jabon", 2.25, 25);
        catalogo[7] = new Catalogo(8, "carne", 2.75, 30);
        catalogo[8] = new Catalogo(9, "gaseosa", 1.8, 180);
        catalogo[9] = new Catalogo(10, "desechable", 3.25, 50);

        Scanner t = new Scanner(System.in);
        int op = 0;
        do {
            System.out.println("Bienvenido al sistema XYZ");
            System.out.println("¿Que desea hacer?");
            System.out.println("1. Loguearse con usuario y contraseña");
            System.out.println("2. Salir y cerrar sistema");
            System.out.print("Digite la opcion: ");
            op = t.nextInt();
            switch (op) {
                case 1: {
                    ///LOGUEARSE
                    boolean isValid = l.ingresar();
                    if (isValid) {
                        switch (l.getTipoUsuario()) {
                            case "Admin":
                                b.crearBitacora(l, lo);
                                m2.MenuAdmin(l, v, catalogo, contVentas, lo);
                                break;
                            case "Vendedor":
                                b.crearBitacora(l, lo);
                                m2.MenuAdmin(l, v, catalogo, contVentas, lo);
                                break;
                            case "Invitado":
                                b.crearBitacora(l, lo);
                                m2.MenuAdmin(l, v, catalogo, contVentas, lo);
                                break;
                            default:
                                System.out.println("Error, no se encontro un tipo de usuario valido");
                                break;
                        }
                    }
                }
                break;
                case 2:
                    ///SALIR DEL SISTEMA
                    b.Comprimir(l, lo);
                    System.out.println("Hasta la proxima......");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
            System.out.println();
        } while (op != 2);
    }
}
