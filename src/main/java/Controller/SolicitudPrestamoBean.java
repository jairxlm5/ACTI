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
import Model.Prestamo;
import Model.Traslado;
import Model.UsuarioDB;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author danielp
 */
public class SolicitudPrestamoBean {

    //Estos son los atributos para relacionarlos con campos de texto en el bean, los primeros 2 los tiene que ingresar
    //el usuario, los ultimos 3 son solo para desplegar informacion

    private String nombre;
    private String descripcion;

    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    ActivoDB activoDB = new ActivoDB();
    Activo activoBuscado = new Activo();
    FuncionarioDB funcDB = new FuncionarioDB();
    
    //Logica necesaria para las fechas
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fechaRetorno = myCalendar.getTime();
    private Date fechaActual = myCalendar.getTime();
    
    
   private String infoActivo = "";
    private String mensajeBusqueda="";
    private String motivo;
    private String idActivo;


    MovimientoActivoDB movDB = new MovimientoActivoDB();

    public SolicitudPrestamoBean() {
      
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
        if (this.fechaRetorno == null) {
            this.validationMessage = "Por favor ingrese la fecha en que va a regresar el activo";
            return;
        }

        if (activoBuscado != null) {
            //Se tiene que obtener la info del usuario que esta haciendo la solicitud
            
            
            if (activoBuscado != null) {
            //Se tiene que obtener la info del usuario que esta haciendo la solicitud
             try {
           
            UsuarioDB userDB = new UsuarioDB();
            userDB.getLogedInUser();
            
            Prestamo prestamo = new Prestamo();
            prestamo.setActivo(activoBuscado);
            prestamo.setAprobado(false);
            prestamo.setFechaRetorno(fechaRetorno);
            prestamo.setFecha_Solicitud(fechaActual);
            //prestamo.setMotivo(motivo);
            
            
    
            
            //-----------------------------------------------------------------------------------
  
            Funcionario funcSolicitante = new Funcionario();
            funcSolicitante = funcDB.getFuncFromDB(userDB.getLogedInUser().getIdentificacion());
            prestamo.setFuncionarioSolicitante(funcSolicitante);
            //-----------------------------------------------------------------------------------
              
            
            //Aqui se esta cayendo
            movDB.saveMovimientoAct((Prestamo)prestamo);
            
        } catch (SQLException e) {
         validationMessage = "Error al guardar en DB" + e.toString();
        } catch (SNMPExceptions s) {
          validationMessage = "Error al guardar en DB" + s.toString();
        }
            

            
        } else {
            this.validationMessage = "El activo no exite o no esta registrado en el sistema";
        }
    }
    }

       public void consultaActivo(){
        
            try {
            this.activoBuscado = this.activoDB.getActivoFromDB(idActivo);
            this.infoActivo = 
                  "Nombre Activo: " + this.activoBuscado.getNombre()+
                  "Descripcion: "+ this.activoBuscado.getDescripcion();
            
        } catch (SQLException e) {
                     mensajeBusqueda = "No existe el activo.";
                     this.activoBuscado = null;
        } catch (SNMPExceptions s) {
                      mensajeBusqueda = "No existe el activo.";
                      this.activoBuscado = null;
        }
        
    }

         public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }
    
    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
       
           public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
       
    public ActivoDB getActivoDB() {
        return activoDB;
    }

    public void setActivoDB(ActivoDB activoDB) {
        this.activoDB = activoDB;
    }

    public Activo getActivoBuscado() {
        return activoBuscado;
    }

    public void setActivoBuscado(Activo activoBuscado) {
        this.activoBuscado = activoBuscado;
    }

    public String getInfoActivo() {
        return infoActivo;
    }

    public void setInfoActivo(String infoActivo) {
        this.infoActivo = infoActivo;
    }

    public String getMensajeBusqueda() {
        return mensajeBusqueda;
    }

    public void setMensajeBusqueda(String mensajeBusqueda) {
        this.mensajeBusqueda = mensajeBusqueda;
    }
       
       
       
    public String getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(String idActivo) {
        this.idActivo = idActivo;
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

// </editor-fold>
}
