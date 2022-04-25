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
import Model.MovimientoActivoDB;
import Model.Sede;
import Model.SedeDB;
import Model.Traslado;
import Model.Usuario;
import Model.UsuarioDB;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.registry.infomodel.User;
import Utils.Utils;
import java.util.List;
import org.primefaces.model.FilterMeta;
/**
 *
 * @author danielp
 */
public class SolicitudTrasladoBean {

    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String idActivo;
    private String nombreSedeOrigen;
  private List<FilterMeta> filterBy;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fechaTraslado;
    private Date fechaActual;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Sede sedeDestino;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Sede> sedes;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    private String infoActivo = "";
    ActivoDB activoDB = new ActivoDB();
    Activo activoBuscado = new Activo();
    private String mensajeBusqueda = "";
    private String sedeOrigenID;
    private String sedeDestinoID;
    private Usuario usuarioActual = new Usuario();
    SedeDB sedeDB = new SedeDB();
    private String motivo;
    MovimientoActivoDB movDB = new MovimientoActivoDB();
    FuncionarioDB funcDB = new FuncionarioDB();

    public SolicitudTrasladoBean() {

        this.fillSedes();
    }

    /**
     * rae la informacion del activo elegido
     *
     * @return Activo
     */
    public void consultaActivo() {

        try {
            this.activoBuscado = this.activoDB.getActivoFromDB(idActivo);
            this.infoActivo
                    = "Nombre Activo: " + this.activoBuscado.getNombre()
                    + "\nDescripcion: " + this.activoBuscado.getDescripcion();

        } catch (SQLException e) {
            mensajeBusqueda = "No existe el activo.";
            this.activoBuscado = null;
        } catch (SNMPExceptions s) {
            mensajeBusqueda = "No existe el activo.";
            this.activoBuscado = null;
        }

    }

    public void fillSedes() {
        try {
            SedeDB sedeDB = new SedeDB();
            this.sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
    }

    /**
     * Este lo que hace es llenar la info sobre el activo, lo ocupaba para poder
     * manejarlo en el xhtml mejor
     */
    public void llenaInfoActivo() {

    }

    /**
     * Llama a los metodos que procesan la solicitud
     */
    public void processRequest() {
        this.validationMessage = "";
        //Validaciones de datos de entrada
        if (this.idActivo.trim().length() == 0) {
            this.validationMessage = "Ingrese el codigo del activo";
            return;
        }

        if (this.fechaTraslado == null) {
            this.validationMessage = "Por favor ingrese la fecha en que desea hacer el traslado";
            return;
        }

        if (activoBuscado != null) {
            //Se tiene que obtener la info del usuario que esta haciendo la solicitud
            try {

                try{
                    this.fechaActual = Utils.getCurrentDate();
                } catch (Exception e){
                    this.validationMessage = "Ocurrió un error al obtener la fecha del sistema";
                    
                }
                UsuarioDB userDB = new UsuarioDB();
                userDB.getLogedInUser();

                Traslado traslado = new Traslado();
                traslado.setActivo(activoBuscado);
                traslado.setAprobado(false);
                traslado.setFechaTraslado(fechaTraslado);
                traslado.setFecha_Solicitud(fechaActual);
                traslado.setSedeDestino(this.sedeDB.getSede(sedeDestinoID));
                traslado.setSedeOrigen(this.sedeDB.getSede(sedeOrigenID));
                traslado.setMotivo(this.motivo);

                //-----------------------------------------------------------------------------------
                //Esto la verdad creo que lo bretie mal 
                Funcionario funcSolicitante = new Funcionario();
                funcSolicitante = funcDB.getFuncFromDB(userDB.getLogedInUser().getIdentificacion());
                traslado.setFuncionarioSolicitante(funcSolicitante);
                //-----------------------------------------------------------------------------------

                movDB.saveMovimientoAct(traslado);
                validationMessage = "Traslado Solicitado exitosamente";

            } catch (SQLException e) {
                validationMessage = "Error al guardar en DB" + e.toString();
            } catch (SNMPExceptions s) {
                validationMessage = "Error al guardar en DB" + s.toString();
            }

        } else {
            this.validationMessage = "El activo no exite o no esta registrado en el sistema";
        }
    }

    /*
    public void cancelar() {
        this.idActivo = "";
        this.nombreSedeOrigen = "";
        this.validationMessage = "";
    }
     */
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

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public MovimientoActivoDB getMovDB() {
        return movDB;
    }

    public void setMovDB(MovimientoActivoDB movDB) {
        this.movDB = movDB;
    }

    public FuncionarioDB getFuncDB() {
        return funcDB;
    }

    public void setFuncDB(FuncionarioDB funcDB) {
        this.funcDB = funcDB;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public SedeDB getSedeDB() {
        return sedeDB;
    }

    public void setSedeDB(SedeDB sedeDB) {
        this.sedeDB = sedeDB;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Activo getActivoBuscado() {
        return activoBuscado;
    }

    public void setActivoBuscado(Activo activoBuscado) {
        this.activoBuscado = activoBuscado;
    }

    public String getIdActivo() {
        return idActivo;
    }

    public String getSedeOrigenID() {
        return sedeOrigenID;
    }

    public void setSedeOrigenID(String sedeOrigenID) {
        this.sedeOrigenID = sedeOrigenID;
    }

    public String getSedeDestinoID() {
        return sedeDestinoID;
    }

    public void setSedeDestinoID(String sedeDestinoID) {
        this.sedeDestinoID = sedeDestinoID;
    }

    public String getMensajeBusqueda() {
        return mensajeBusqueda;
    }

    public void setMensajeBusqueda(String mensajeBusqueda) {
        this.mensajeBusqueda = mensajeBusqueda;
    }

    public void setIdActivo(String idActivo) {
        this.idActivo = idActivo;
    }

    public String getNombreSedeOrigen() {
        return nombreSedeOrigen;
    }

    public void setNombreSedeOrigen(String nombreSedeOrigen) {
        this.nombreSedeOrigen = nombreSedeOrigen;
    }

    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino) {
        this.sedeDestino = sedeDestino;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getInfoActivo() {
        return infoActivo;
    }

    public void setInfoActivo(String infoActivo) {
        this.infoActivo = infoActivo;
    }

// </editor-fold>

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterMeta> filterBy) {
        this.filterBy = filterBy;
    }
    
    
    
}
