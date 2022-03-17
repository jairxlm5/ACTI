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
public class Canton {
    private int id;
    private String nombre;
    private Provincia provincia;
    private ArrayList<Distrito> distritos;

    public Canton(int id, String nombre, Provincia provincia, ArrayList<Distrito> distritos) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.distritos = distritos;
    }
    
    public Canton(){
        
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public ArrayList<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(ArrayList<Distrito> distritos) {
        this.distritos = distritos;
    }
    
    
}
