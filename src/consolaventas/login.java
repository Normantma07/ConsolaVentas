/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaventas;

import java.io.*;
import java.nio.file.*;
import consolaventas.log;
import java.util.Scanner;

/**
 *
 * @author Cesia Coto
 */
public class login {

    //atributos privados
    private Scanner scn = new Scanner(System.in);
    private String usuario;
    private String contrasena;
    private String tipoUsuario;
    private boolean primerInicio;
    private log lo;

    public String[][] getDbUsers() {
        return dbUsers;
    }

    public String getUsuario() {
        return usuario;
    }
    private String[][] dbUsers = new String[9][4];
    private int cont;

    //Primer constructor
    public login() {
        this.usuario = "";
        this.contrasena = "";
        this.tipoUsuario = "";
        this.cont = 0;
        this.primerInicio = true;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    ///Usado para mostrar todos los usuarios guardados en la matriz y comproba que funciona, 
    ///luego servira para que el admin cambie contraseña
    public void mostrar() {
        System.out.println("ID\tUsuario\tContraseña");
        for (int i = 0; i < cont; i++) {
            System.out.println(this.dbUsers[i][0] + "\t" + this.dbUsers[i][2] + "\t" + this.dbUsers[i][3]);
        }
    }

    ///ingresar permite hacer el login, si este es correcto devuelve verdadero, de lo contrario falso
    public boolean ingresar() {
        boolean resultado = false; // para comprobar si cuenta y pass no coindicen con ninguna línea 
        String record = null;
        FileReader in = null;
        //Se comprueba que el archivo con los usuarios exista, si no existe se puede agregar mientras el
        //programa aun esta corriendo antes de presionar enter
        if (!Files.exists(Paths.get("users.txt"))) {
            System.out.println("El archivo de texto no se encontro, devuelvalo a su lugar");
            System.out.println("Presione enter para continuar...");
            new java.util.Scanner(System.in).nextLine();
        }
        try {
            in = new FileReader("users.txt");
            BufferedReader br = new BufferedReader(in);

            //Leer usuario y contraseña
            System.out.println("Bienvenido al sistema de ventas.");
            System.out.println("Favor ingresar credenciales.");
            System.out.print("Usuario: ");
            this.usuario = scn.nextLine();
            System.out.print("Contrasena: ");
            this.contrasena = scn.nextLine();
            if(this.primerInicio)
            {
                while ((record = br.readLine()) != null) //el while leera todo el documento
                {
                    String[] palabra = record.split(","); // dividir en palabras delimitadas por espacios
                    //aca abajo se rellena la matriz de usuarios
                    this.dbUsers[cont][0] = Integer.toString(cont + 1);
                    this.dbUsers[cont][1] = palabra[0];
                    this.dbUsers[cont][2] = palabra[1];
                    this.dbUsers[cont][3] = palabra[2];
                    this.cont++;
                }
                this.primerInicio = false;
            }
            
            resultado = validarCredenciales(this.usuario, this.contrasena);
            if (!resultado)//Cuando no coincide usuario y contraseña
            {
                System.out.println("Usuario o contraseña incorrecto.");
            } else {
                System.out.println("Bienvenido otra vez " + this.usuario);
            }
            in.close();
        } catch (IOException e) {
            e.getCause();
            lo.setContenido(e.getMessage());
            lo.escribirLog(this.usuario);
        }
        return resultado;
    }

    public boolean validarCredenciales(String user, String pass) {
        boolean resultado = false;
        int i = 0;
        while (i < this.dbUsers.length && !resultado) {
            if (user.equals(this.dbUsers[i][2]) && pass.equals(this.dbUsers[i][3])) {
                this.tipoUsuario = this.dbUsers[i][1];
                resultado = true;
            } else {
                i++;
            }
        }
        return resultado;
    }

    public boolean cambiarContraseña() {
        boolean resultado = false;
        String pass;
        String id;
        int cod;
        System.out.println("Escriba su contrasena de administrador");
        pass = this.scn.nextLine();
        if (pass.equals(this.contrasena)) {
            mostrar();
            System.out.println("Digite el id del usuario a cambiar contraseña");
            id = this.scn.nextLine();
            cod = Integer.parseInt(id);
            if ((cod <= this.cont) && (cod > 0)) {
                System.out.println("Digite la nueva contrasena de " + this.dbUsers[Integer.parseInt(id) - 1][2]);
                pass = this.scn.nextLine();
                this.dbUsers[Integer.parseInt(id) - 1][3] = pass;
                resultado = true;
                System.out.println("La nueva contrasena del usuario " + this.dbUsers[Integer.parseInt(id) - 1][2] + " es " + this.dbUsers[Integer.parseInt(id) - 1][3]);
            } else {
                System.out.println("Error, el usuario con ese id no se encuentra");
            }
        } else {
            System.out.println("Error, su contrasena no coincide.");
        }
        return resultado;
    }
}
