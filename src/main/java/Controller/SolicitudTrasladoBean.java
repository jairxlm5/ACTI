/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Activo;
import Model.Sede;
import Model.SedeDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class SolicitudTrasladoBean {

    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String idActivo;
    private String nombreSedeOrigen;
    private Date fechaTraslado;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Sede sedeDestino;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Sede> sedes;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    private String infoActivo = "";

    public SolicitudTrasladoBean() {
        this.idActivo = "";
        this.nombreSedeOrigen = "";
        this.validationMessage = "";
        fillLists();
    }

    /**
     * rae la informacion del activo elegido
     *
     * @return Activo
     */
    public Activo getActivoFromDB() {
        return null;
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

        Activo activoElegido = getActivoFromDB();
        if (activoElegido != null) {
            //Se tiene que obtener la info del usuario que esta haciendo la solicitud
        } else {
            this.validationMessage = "El activo no exite o no esta registrado en el sistema";
        }
    }

    public void fillLists() {
        this.sedes = new ArrayList<>();
        //Se llena la lista con las sedes guardadas en la DB
    }

    public void cancelar() {
        this.idActivo = "";
        this.nombreSedeOrigen = "";
        this.validationMessage = "";
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

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public String getIdActivo() {
        return idActivo;
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
}
