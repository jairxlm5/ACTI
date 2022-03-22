/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Enum.Perfil;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class UsuarioPerfil {
    private String usuarioID;
    private Perfil tipoPerfil;
    private Date fechaSolicitud;
    private Date fechaAprobacion;
    private int numSolicitud;
    private boolean aprobado;

    public UsuarioPerfil(String idUsuario, Perfil tipoPerfil, Date fechaSolicitud, Date fechaAprobacion, boolean aprobacion) {
        this.usuarioID = idUsuario;
        this.tipoPerfil = tipoPerfil;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.aprobado = aprobacion;
    }

    public UsuarioPerfil() {
        
    }

    public String getIdUsuario() {
        return usuarioID;
    }

    public void setIdUsuario(String usuario) {
        this.usuarioID = usuario;
    }

    public Perfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(Perfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public int getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(int numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    
    @Override
    public String toString(){
        return usuarioID + " " + tipoPerfil.toString();
    }
    
}
