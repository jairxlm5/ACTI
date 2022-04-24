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
    private Activo activoSeleccionado;
    private Usuario funcionarioSolicitante, tecnicoAprobante;
    private Date fecha_Solicitud;
    private String motivo;
    private boolean aprobado;
    private Date fechaRetorno;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    private Prestamo prestamoSeleccionado;

    public AprobacionPrestamoBean() {
        this.solicitudesDePrestamo = new ArrayList<>();
        this.getSolicitudesDePrestamo();
    }

    /**
     * Metodo que llama a los metodos que aprueban un prestamo de activo
     */
    public void apruebaPrestamo() {
        if (this.prestamoSeleccionado != null) {
            try {
                MovimientoActivoDB movActDB = new MovimientoActivoDB();
                this.activoSeleccionado = this.prestamoSeleccionado.getActivo();
                this.motivo = this.prestamoSeleccionado.getMotivo();
                this.fecha_Solicitud = this.prestamoSeleccionado.getFecha_Solicitud();
                MovimientoActivo movAct = movActDB.getMovActFromDB(this.activoSeleccionado.getIdActivo(), motivo, fecha_Solicitud);
                movActDB.aproveMovimientoAct(movAct);
            } catch (Exception e) {
                this.validationMessage = "Error al aprobar prestamo";
            }
        } else {
            this.validationMessage = "Debe seleccionar el Prestamo a aprobar";
        }
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
        try {
            PrestamoDB prestamoDB = new PrestamoDB();
            this.solicitudesDePrestamo = prestamoDB.getPrestamosNoAprobados();
        } catch (SQLException e) {
            this.validationMessage = "Ocurrió un error al cargar los prestamos";
        } catch (SNMPExceptions s) {
            this.validationMessage = "Ocurrió un error al cargar los prestamos";
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

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
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

    public Activo getActivoSeleccionado() {
        return activoSeleccionado;
    }

    public void setActivoSeleccionado(Activo activoSeleccionado) {
        this.activoSeleccionado = activoSeleccionado;
    }

// </editor-fold>
}
