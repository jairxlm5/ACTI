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
import Model.SedeDB;
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
public class AprobacionPrestamoBean {

    //ArrayList que se llena con la lista de solicitudes de prestamos de activos
    private ArrayList<Prestamo> solicitudesDePrestamo;
    private ArrayList<Prestamo> solicitudesDePrestamoFiltradas;
    //Estos son los datos a desplegar en las columnas de la tabla
    private Activo activo;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    private Date fechaRetorno;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    Prestamo prestamoSeleccionado = new Prestamo();
         
    
    
    public AprobacionPrestamoBean() {
        this.solicitudesDePrestamo = new ArrayList<>();
        this.validationMessage = "";
    }

    /**
     * Metodo que llama a los metodos que aprueban un prestamo de activo
     */
    public void apruebaPrestamo() {

    }

    /**
     * Rechaza el prestamo
     */
    public void rechazaPrestamo() {

    }

    /**
     * Retorna una lista con todas las solicitudes de prestamos que no han sido
     * aprobadas
     *
     * @return ArrayList
     */
    public ArrayList<Prestamo> getSolicitudesDePrestamo() {
        //Esta variable se llena con los datos que vienen de la DB
        ArrayList<Prestamo> prestamosDB = new ArrayList<>();

        try {
            PrestamoDB prestamoDB = new PrestamoDB();
            prestamosDB = prestamoDB.getAllPrestamos();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }

        for (Prestamo prestamo : prestamosDB) {
            //Se agregan solo los prestamos que no han sido aprobados
            if (!prestamo.isAprobado()) {
                this.solicitudesDePrestamo.add(prestamo);
            }
        }
        return solicitudesDePrestamo;
    }
    

    public ArrayList<Sede> getSedesDB() {
        ArrayList<Sede> sedes = new ArrayList<>();
        try {
            SedeDB sedeDB = new SedeDB();
            sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return sedes;
    }


      public void onRowSelect(SelectEvent<Prestamo> event) {
         
         
         this.prestamoSeleccionado = event.getObject();
        FacesMessage msg = new FacesMessage("Product Selected", String.valueOf(prestamoSeleccionado.getMotivo()));
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<Prestamo> event) {
        FacesMessage msg = new FacesMessage("Product Unselected", String.valueOf(event.getObject().getMotivo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\

        public Prestamo getPrestamoSeleccionado() {
        return prestamoSeleccionado;
    }

    public void setPrestamoSeleccionado(Prestamo prestamoSeleccionado) {
        this.prestamoSeleccionado = prestamoSeleccionado;
    }
    
    
    public void setSolicitudesDePrestamo(ArrayList<Prestamo> solicitudesDePrestamo) {
        this.solicitudesDePrestamo = solicitudesDePrestamo;
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

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public ArrayList<Prestamo> getSolicitudesDePrestamoFiltradas() {
        return solicitudesDePrestamoFiltradas;
    }

    public void setSolicitudesDePrestamoFiltradas(ArrayList<Prestamo> solicitudesDePrestamoFiltradas) {
        this.solicitudesDePrestamoFiltradas = solicitudesDePrestamoFiltradas;
    }
// </editor-fold>

}
