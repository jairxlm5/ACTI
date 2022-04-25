/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Activo;
import Model.ActivoDB;
import Model.Funcionario;
import Model.FuncionarioDB;
import Model.Prestamo;
import Model.Sede;
import Model.Traslado;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.FilterMeta;

/**
 *
 * @author danielp
 */
public class ReporteActivosPTA {
    
    //Bro para trabajar los beans en este primer entregable lo que hice fue hacer un array de activos 
    //Luego decidimos como bretearlo 
    private ArrayList<Activo> activos;
    private ArrayList<Activo> activosfiltrados;
    
      private List<FilterMeta> filterBy;
    //ArrayLists con la info para desplegar en las tablas
    private ArrayList<Prestamo> prestamos;
    private ArrayList<Traslado> traslados;
    //Informacion General de los dos tipos
    private Activo activo;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    //Informacion especifica de Prestamos
    private Date fechaRetorno;
    //Informacion especifica de Traslados
    private Date fechaTraslado;
    private Sede sedeOrigen, sedeDestino;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    
    public ReporteActivosPTA(){
        this.motivo = "";
        this.validationMessage = "";
        llenaData();
    }
    
    public void llenaData(){
        this.prestamos = new ArrayList<>();
        this.traslados = new ArrayList<>();
        this.activos = new ArrayList<>();
        //Cada metodo se encarga de consultar la DB y llenar su ArrayList respectivo
        getPrestamos();
        getTraslados();
    }

       public ArrayList<Activo> getActivoDB(){
        ArrayList<Activo> activos = new ArrayList<>();
        try {
           ActivoDB activoDB = new ActivoDB();
           activos = activoDB.getAllActivos();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return activos;
    }
       
         public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }
      
       // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
       
           public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public ArrayList<Traslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(ArrayList<Traslado> traslados) {
        this.traslados = traslados;
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

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
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

    public ArrayList<Activo> getActivos() {
        return activos;
    }

    public void setActivos(ArrayList<Activo> activos) {
        this.activos = activos;
    }

    public ArrayList<Activo> getActivosfiltrados() {
        return activosfiltrados;
    }

    public void setActivosfiltrados(ArrayList<Activo> activosfiltrados) {
        this.activosfiltrados = activosfiltrados;
    }
    
    
    

// </editor-fold>

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterMeta> filterBy) {
        this.filterBy = filterBy;
    }

}
