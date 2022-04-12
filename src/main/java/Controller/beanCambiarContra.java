/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author JAW
 */
public class beanCambiarContra {
    String claveAutogenerada;
    String codigoSeguridad;
    String claveNueva;

    public beanCambiarContra(String claveAutogenerada, String codigoSeguridad, String claveNueva) {
        this.claveAutogenerada = claveAutogenerada;
        this.codigoSeguridad = codigoSeguridad;
        this.claveNueva = claveNueva;
    }

    public beanCambiarContra() {
    }

    public String getClaveAutogenerada() {
        return claveAutogenerada;
    }

    public void setClaveAutogenerada(String claveAutogenerada) {
        this.claveAutogenerada = claveAutogenerada;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }
    
    
}
