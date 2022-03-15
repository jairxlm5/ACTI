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
    private String idUsuario;
    private Perfil tipoPerfil;
    private Date fechaSolicitud;
    private Date fechaAprobacion;
    private boolean aprobado;

    public UsuarioPerfil(String idUsuario, Perfil tipoPerfil, Date fechaSolicitud, Date fechaAprobacion, boolean aprobacion) {
        this.idUsuario = idUsuario;
        this.tipoPerfil = tipoPerfil;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.aprobado = aprobacion;
    }

    public UsuarioPerfil() {
        
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
