/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class Distrito {
    private int id;
    private String nombre;
    private Canton canton;
    private ArrayList<Barrio> barrios;

    public Distrito(int id, String nombre, ArrayList<Barrio> barrios) {
        this.id = id;
        this.nombre = nombre;
        this.barrios = barrios;
    }
    
    public Distrito(){
        this.barrios = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    public ArrayList<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(ArrayList<Barrio> barrios) {
        this.barrios = barrios;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
