/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Activo;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class SolicitudPrestamoBean {
    //Estos son los atributos para relacionarlos con campos de texto en el bean, los primeros 2 los tiene que ingresar
    //el usuario, los ultimos 3 son solo para desplegar informacion
    private String idActivo; 
    private Date fechaRetorno;
    
    private String nombre;
    private String descripcion;
    private String nombreSede;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public SolicitudPrestamoBean() {
        this.idActivo = "";
        this.nombre = "";
        this.descripcion = "";
        this.nombreSede = "";
        this.validationMessage = "";
    }

    /**
     * Trae la informacion del activo elegido
     * @return Activo
     */
    public Activo getActivoFromDB() {
        return null;
    }
    
    /**
     * Llama a los metodos que procesan la solicitud
     */
    public void processRequest(){
        this.validationMessage = "";
        //Validaciones de datos de entrada
        if(this.idActivo.trim().length() == 0){
            this.validationMessage = "Ingrese el codigo del activo";
            return;
        }
        if(this.fechaRetorno == null){
            this.validationMessage = "Por favor ingrese la fecha en que va a regresar el activo";
            return;
        }
        
        Activo activoElegido = getActivoFromDB();
        if(activoElegido != null){
            //Se tiene que obtener la info del usuario que esta haciendo la solicitud
        } 
        else{
            this.validationMessage = "El activo no exite o no esta registrado en el sistema";
        }
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

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
    
    
    
}
