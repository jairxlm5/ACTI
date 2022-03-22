/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Enum.TipoTelefono;

/**
 *
 * @author danielp
 */
public class Telefono {
    private String numero;
    private String idUsuario;
    private TipoTelefono tipo;

    public Telefono(String numero, String usuario, TipoTelefono tipo) {
        this.numero = numero;
        this.idUsuario = usuario;
        this.tipo = tipo;
    }
    
    public Telefono(){
        
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoTelefono getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefono tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        return numero;
    }
}
