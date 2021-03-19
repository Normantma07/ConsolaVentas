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

    private int idVenta;
    private Catalogo[] lst;
    private int contProd;
    private float totalVenta;
    private boolean pedirDisculpas;
    private boolean hacerDescuento;
    private String fecha, dia, mes, anio;
    private Calendar c;
    private log lo;

    public Ventas() {
        this.idVenta = 0;
        this.lst = new Catalogo[10];
        this.contProd = 0;
        this.totalVenta = 0;
        this.pedirDisculpas = false;
        this.hacerDescuento = false;
    }

    public void mostrarCatalogoVenta(Catalogo[] catalogo) {
        System.out.println("id nombre precio");
        for (int i = 0; i < 10; i++) {
            System.out.println(catalogo[i].Getid() + "-" + catalogo[i].GetName() + " c/u $" + catalogo[i].GetPrecio());
        }
    }

    public void mostrarCatalogoExistencia(Catalogo[] catalogo) {
        System.out.println("id nombre cantidad");
        for (int i = 0; i < 10; i++) {
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
            if (isNumber && id > 0 && id <= 10) {
                System.out.println("id nombre cantidad");
                System.out.println(catalogo[id - 1].Getid() + "-" + catalogo[id - 1].GetName() + " " + catalogo[id - 1].GetUnidades());
            } else {
                if (isNumber) {
                    System.out.println("Error, el id del producto seleccionado no existe.");
                } else {
                    while (cont < 10 && !encontrado) {
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

    public boolean agregarProductos(Catalogo[] catalogo, int contVentas, login l, log lo) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Digite la cadena de texto en donde indique los productos y cantidades de la venta");
        String palabra = scn.nextLine();
        int index = 0;
        int tmp = 0;
        boolean resultado = true;
        String[] productos = palabra.split(",");

        if (productos.length % 2 == 0) {
            while (index < productos.length && resultado) {
                try {

                    this.lst[contProd] = new Catalogo(catalogo[Integer.parseInt(productos[index]) - 1].Getid(), catalogo[Integer.parseInt(productos[index]) - 1].GetName(), catalogo[Integer.parseInt(productos[index]) - 1].GetPrecio(), catalogo[Integer.parseInt(productos[index]) - 1].GetUnidades());
                    if (catalogo[Integer.parseInt(productos[index]) - 1].GetUnidades() < Integer.parseInt(productos[index + 1])) {
                        tmp = catalogo[Integer.parseInt(productos[index]) - 1].GetUnidades();
                        this.lst[contProd].setUnidades(tmp);
                        tmp = Integer.parseInt(productos[index]) - 1;
                        catalogo[tmp].setUnidades(0);
                        this.pedirDisculpas = true;
                    } else {
                        tmp = Integer.parseInt(productos[index + 1]);
                        this.lst[contProd].setUnidades(tmp);
                        tmp = catalogo[Integer.parseInt(productos[index]) - 1].GetUnidades() - Integer.parseInt(productos[index + 1]);
                        catalogo[Integer.parseInt(productos[index]) - 1].setUnidades(tmp);
                    }
                    this.contProd++;
                    tmp = this.contProd - 1;
                    this.lst[tmp].setId(this.contProd);
                    index = index + 2;
                } catch (Exception e) {
                    System.out.println("Error, ha ingresado de manera incorrecta la cadena de caracteres");
                    resultado = false;
                    String m = e.getMessage();
                    lo.setContenido(m);
                    lo.escribirLog(l.getUsuario());
                }
            }
            if (resultado) {
                int i = 0;
                while (i < contProd) {
                    this.totalVenta += this.lst[i].GetPrecio() * this.lst[i].GetUnidades();
                    i++;
                }
                if (this.totalVenta > 20) {
                    this.hacerDescuento = true;
                    this.totalVenta = (float) (this.totalVenta * 0.97);
                }
                contVentas++;
                this.idVenta = contVentas;
                facturar(l);
            }
        } else {
            System.out.println("Error, el numero de digitos ingresados deben de ser pares");
        }
        return resultado;
    }

    public void facturar(login l) {
        String contenido;
        System.out.println("Factura de compra");
        System.out.println("Supermercado don Diego");

        System.out.println("-----------------------");
        contenido = "Factura de compra" + "\nSupermercado don Diego" + "\n-----------------------";
        for (int i = 0; i < this.contProd; i++) {
            System.out.println(lst[i].GetName() + "---" + lst[i].GetUnidades() + "x$" + lst[i].GetPrecio() + " = $" + lst[i].GetUnidades() * lst[i].GetPrecio());
            contenido += "\n" + lst[i].GetName() + "---" + lst[i].GetUnidades() + "x$" + lst[i].GetPrecio() + " = $" + lst[i].GetUnidades() * lst[i].GetPrecio();
        }
        System.out.println("\tTotal de factura: $" + this.totalVenta);
        System.out.println("-----------------------");
        contenido += "\n\tTotal de factura: $" + this.totalVenta + "\n-----------------------";
        if (this.hacerDescuento) {
            System.out.println("-Se aplico un descuento del 3% al");
            System.out.println("total porque la compra excedia los $20");
            contenido += "\n-Se aplico un descuento del 3% al" + "\ntotal porque la compra excedia los $20";
        }
        if (this.pedirDisculpas) {
            System.out.println("-Lo sentimos, alguno de los productos ");
            System.out.println("seleccionado no cumplia con la cantidad ");
            System.out.println("de existencias solicitadas, se le entrego ");
            System.out.println("toda la cantidad que habia en existencia");
            contenido += "\n-Lo sentimos, alguno de los productos " + "\nseleccionado no cumplia con la cantidad " + "\nde existencias solicitadas, se le entrego " + "\ntoda la cantidad que habia en existencia";
        }
        this.c = new GregorianCalendar();
        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        anio = Integer.toString(c.get(Calendar.YEAR));
        fecha = dia + "-" + mes + "-" + anio;
        try {
            File file = new File("factura/FC" + this.idVenta + "_" + fecha + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            lo.setContenido(e.getMessage());
            lo.escribirLog(l.getUsuario());
        }
    }
}
