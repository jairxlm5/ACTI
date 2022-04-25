/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author danielp
 */
public class MovimientoActivo {
    private Activo activo;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    
    public MovimientoActivo(Activo activo, Date fecha_Solicitud, String motivo, 
                                boolean aprobado) {
        this.activo = activo;
        this.fecha_Solicitud = fecha_Solicitud;
        this.motivo = motivo;
        this.aprobado = aprobado;
    }
    
    public MovimientoActivo(){
        
    }


    public Activo getActivo() {
        return activo;
    }

    public void setActivo(Activo activo) {
        this.activo = activo;
    }

    public Funcionario getFuncionarioSolicitante() {
        return (Funcionario)funcionarioSolicitante;
    }

    public void setFuncionarioSolicitante(Funcionario funcionarioSolicitante) {
        this.funcionarioSolicitante = funcionarioSolicitante;
    }

    public Date getFecha_Solicitud() {
        return fecha_Solicitud;
    }

    public void setFecha_Solicitud(Date fecha_Solicitud) {
        this.fecha_Solicitud = fecha_Solicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Tecnico getTecnicoAprobante() {
        return (Tecnico)tecnicoAprobante;
    }

    public void setTecnicoAprobante(Tecnico tecnicoAprobante) {
        this.tecnicoAprobante = tecnicoAprobante;
    }
    
    
    
}
