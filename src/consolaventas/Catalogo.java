/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaventas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Cesia Coto
 */
public class Catalogo {
     public static void main(String[] args) {
     // TODO code application logic here
        System.out.println("Bienvenido al Catalogo de Productos\n");
        
        
        //Definimos una ArrayList
        List list = new ArrayList();
        
        //Añadimos elementos
        list.add("huevos c/u $0.10");
        list.add("pollo c/u $5.00");
        list.add("aceite c/u $3.00");
        list.add("fosforos c/u $0.50");
        list.add("dulces c/u $0.80");
        list.add("margarina c/u $0.30");
        list.add("jabon c/u $2.25");
        list.add("carne c/u $2.75"); 
        list.add("gaseosa c/u $1.80");
        list.add("desechable c/u $3.25");        
        
    //Obtenemos un Iterador y recorremos la lisla
    Iterator iter = list.iterator();
    while (iter.hasNext())
        System.out.println(iter.next());
   }
    
}
