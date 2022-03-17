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
    private Usuario usuario;
    private Perfil tipoPerfil;
    private Date fechaSolicitud;
    private Date fechaAprobacion;
    private boolean aprobado;

    public UsuarioPerfil(Usuario idUsuario, Perfil tipoPerfil, Date fechaSolicitud, Date fechaAprobacion, boolean aprobacion) {
        this.usuario = idUsuario;
        this.tipoPerfil = tipoPerfil;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.aprobado = aprobacion;
    }

    public UsuarioPerfil() {
        
    }

    public Usuario getIdUsuario() {
        return usuario;
    }

    public void setIdUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    
}
