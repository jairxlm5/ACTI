/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author danielp
 */
public class Barrio {
    private int id;
    private String nombre;
    private Distrito distrito;

    public Barrio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Barrio(){
        
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

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
