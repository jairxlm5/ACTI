/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Activo;
import Model.Prestamo;
import Model.PrestamoDB;
import Model.Sede;
import Model.Traslado;
import Model.TrasladoDB;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class AprobacionTrasladoBean {

    //ArrayList que se llena con la lista de solicitudes de traslados de activos
    private ArrayList<Traslado> solicitudesDeTraslado;
    private ArrayList<Traslado> solicitudesDeTrasladoFiltradas;
    //Estos son los datos a desplegar en las columnas de la tabla
    private Activo activo;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    private Date fechaTraslado;
    private Sede sedeOrigen, sedeDestino;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public AprobacionTrasladoBean() {
        this.solicitudesDeTraslado = new ArrayList<>();
        this.validationMessage = "";
    }

    /**
     * Metodo que llama a los metodos que aprueban un traslado de activo
     */
    public void aprobarTraslado() {

    }

    /**
     * Metodo que rechaza el traslado
     */
    public void rechazarTraslado() {

    }

    /**
     * Retorna una lista con todas las solicitudes de traslados que no han sido
     * aprobadas
     *
     * @return ArrayList
     */
    public ArrayList<Traslado> getSolicitudesDeTraslado() {
        //Esta variable se llena con los datos que vienen de la DB
        ArrayList<Traslado> trasladosDB = new ArrayList<>();

        try {
            TrasladoDB trasladoDB = new TrasladoDB();
            trasladosDB = trasladoDB.getAllTraslados();

        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }

        for (Traslado traslado : trasladosDB) {
            //Se agregan solo los traslados que no se han aprobado
            if (!traslado.isAprobado()) {
                this.solicitudesDeTraslado.add(traslado);
            }
        }
        return solicitudesDeTraslado;
    }

    public void setSolicitudesDeTraslado(ArrayList<Traslado> solicitudesDeTraslado) {
        this.solicitudesDeTraslado = solicitudesDeTraslado;
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
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

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public Sede getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(Sede sedeOrigen) {
        this.sedeOrigen = sedeOrigen;
    }

    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino) {
        this.sedeDestino = sedeDestino;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public ArrayList<Traslado> getSolicitudesDeTrasladoFiltradas() {
        return solicitudesDeTrasladoFiltradas;
    }

    public void setSolicitudesDeTrasladoFiltradas(ArrayList<Traslado> solicitudesDeTrasladoFiltradas) {
        this.solicitudesDeTrasladoFiltradas = solicitudesDeTrasladoFiltradas;
    }
// </editor-fold>

}
