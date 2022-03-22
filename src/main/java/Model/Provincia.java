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
public class Provincia {
    private int id;
    private String nombre;
    private ArrayList<Canton> cantones;

    public Provincia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.cantones = new ArrayList<>();
    }
    
    public Provincia(){
        this.cantones = new ArrayList<>();
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

    public ArrayList<Canton> getCantones() {
        return cantones;
    }

    public void setCantones(ArrayList<Canton> cantones) {
        this.cantones = cantones;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
