/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Activo;
import Model.MovimientoActivo;
import Model.MovimientoActivoDB;
import Model.Prestamo;
import Model.PrestamoDB;
import Model.Sede;
import Model.Traslado;
import Model.TrasladoDB;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

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
    private Activo activoSeleccionado;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    private Date fechaTraslado;
    private Sede sedeOrigen, sedeDestino;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    
    
    
    
    //Este traslado es el que el usuario clickea Siempre que el usuario le de click se va a estar actualizando aqui en el bean 
    private Traslado trasladoSeleccionado;

    public AprobacionTrasladoBean() {
        this.solicitudesDeTraslado = new ArrayList<>();
        
        this.validationMessage = "";
    }

    /**
     * Metodo que llama a los metodos que aprueban un traslado de activo
     */
    public void aprobarTraslado() {
        if(this.activoSeleccionado != null){
            //Se tiene que crear un objeto MovimientoActivo
            try{
                MovimientoActivoDB movActDB = new MovimientoActivoDB();
                MovimientoActivo movAct = movActDB.getMovActFromDB(this.activoSeleccionado.getIdActivo(), motivo, fecha_Solicitud);
                movActDB.aproveMovimientoAct(movAct);
            } catch (Exception e){
                this.validationMessage = "Error al aprobar traslado";
            }
        }
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

     public void onRowSelect(SelectEvent<Traslado> event) {
         
         trasladoSeleccionado =(Traslado) event.getObject();
        FacesMessage msg = new FacesMessage("Traslado Selected", String.valueOf(trasladoSeleccionado.getMotivo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<Traslado> event) {
        FacesMessage msg = new FacesMessage("Product Unselected", String.valueOf(event.getObject().getMotivo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
      public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }
    
    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    
    
       public Traslado getTrasladoSeleccionado() {
        return trasladoSeleccionado;
    }

    public void setTrasladoSeleccionado(Traslado trasladoSeleccionado) {
        this.trasladoSeleccionado = trasladoSeleccionado;
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
    
    public Activo getActivoSeleccionado() {
        return activoSeleccionado;
    }

    public void setActivoSeleccionado(Activo activoSeleccionado) {
        this.activoSeleccionado = activoSeleccionado;
    }

    
// </editor-fold>

    
}
