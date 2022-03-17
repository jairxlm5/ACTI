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
    private Usuario usuario;
    private TipoTelefono tipo;

    public Telefono(String numero, Usuario usuario, TipoTelefono tipo) {
        this.numero = numero;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoTelefono getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefono tipo) {
        this.tipo = tipo;
    }
    
    
}
