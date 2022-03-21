/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Activo;
import Model.MovimientoActivo;
import Model.Traslado;
import Model.Usuario;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class ReporteSolicitudActivos {
    //ArrayLists con la info para desplegar en las tablas
    private ArrayList<MovimientoActivo> solicitudesAprobadas;
    private ArrayList<MovimientoActivo> solicitudesNoAprobadas;
    
    //Bro para trabajar los beans en este primer entregable lo que hice fue hacer un array de activos 
    //Luego decidimos como bretearlo 
    private ArrayList<Activo> activos;
    
    //Informacion para las columnas de las tablas de cada activo en los ArrayList
    private Activo activo;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    //String que indica si es Traslado o Prestamo para tambien mostrarlo en la tabla
    private String tipoSolicitud;

    public ReporteSolicitudActivos() {
        this.tipoSolicitud = "";
        fillData();
    }
    
    public void fillData(){
        this.solicitudesAprobadas = new ArrayList<>();
        this.solicitudesNoAprobadas = new ArrayList<>();
        this.activos = new ArrayList<>();
        //Cada metodo se encarga de consultar la DB y llenar su ArrayList respectivo
        getSolicitudesAprobadas();
        getSolicitudesNoAprobadas();
    }

    public ArrayList<MovimientoActivo> getSolicitudesAprobadas() {
        return solicitudesAprobadas;
    }

    public void setSolicitudesAprobadas(ArrayList<MovimientoActivo> solicitudesAprobadas) {
        this.solicitudesAprobadas = solicitudesAprobadas;
    }

    public ArrayList<MovimientoActivo> getSolicitudesNoAprobadas() {
        return solicitudesNoAprobadas;
    }

    public void setSolicitudesNoAprobadas(ArrayList<MovimientoActivo> solicitudesNoAprobadas) {
        this.solicitudesNoAprobadas = solicitudesNoAprobadas;
    }

    public Activo getActivo() {
        return activo;
    }

    public void setActivo(Activo activo) {
        this.activo = activo;
    }

    public Usuario getFuncionarioSolicitante() {
        return funcionarioSolicitante;
    }

    public void setFuncionarioSolicitante(Usuario funcionarioSolicitante) {
        this.funcionarioSolicitante = funcionarioSolicitante;
    }

    public Usuario getTecnicoAprobante() {
        return tecnicoAprobante;
    }

    public void setTecnicoAprobante(Usuario tecnicoAprobante) {
        this.tecnicoAprobante = tecnicoAprobante;
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

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public ArrayList<Activo> getActivos() {
        return activos;
    }

    public void setActivos(ArrayList<Activo> activos) {
        this.activos = activos;
    }
    
    
}