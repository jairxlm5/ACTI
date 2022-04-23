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
import Model.Sede;
import Model.SedeDB;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author danielp
 */
public class RegistroActivosBean {

    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String idActivo;
    private String nombre;
    private String descripcion;
    private double valor;
    private Date fechaAdquisicion;
    private String sedeID;
    private String funcID;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Sede> sedes;
    private ArrayList<Funcionario> funcionarios;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Sede sedeSeleccionada;
    private Funcionario funcionario;
    //Mensaje para desplegar info de validaciones
    private String validationMessage = "";

    //Logica necesaria para las fechas
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fecha = myCalendar.getTime();

    ActivoDB activoDB = new ActivoDB();

    public RegistroActivosBean() {
        this.funcionarios = new ArrayList<>();
        this.sedes = new ArrayList<>();
        this.idActivo = "";
        this.nombre = "";
        this.descripcion = "";
        this.validationMessage = "";
        this.fillSedes();
        this.fillFuncionarios();

    }

    /**
     * Verifica si el funcionario existe para asignar el activo a nombre suyo
     *
     * @return Funcionario
     */
    public Funcionario getFuncionarioFromDB() {
        return null;
    }

    public void guardaActivo() {
        this.validationMessage = "";
        if (this.idActivo.trim().length() == 0) {
            this.validationMessage = "Debe indicar el codigo del activo";
            return;
        }
        if (this.nombre.trim().length() == 0) {
            this.validationMessage = "Por favor ingrese el nombre del activo";
            return;
        }
        if (this.descripcion.trim().length() == 0) {
            this.validationMessage = "Por favor ingrese una descripcion para el activo";
            return;
        }
        if (this.valor == 0) {
            this.validationMessage = "Debe indicar el valor que tiene el activo";
            return;
        }
        if (this.fechaAdquisicion == null) {
            this.validationMessage = "Debe indicar la fecha en que se adquirió el activo";
            return;
        }
        /*
        if (this.sedeSeleccionada == null) {
            this.validationMessage = "Por favor indique la sede a la que pertenece el activo";
            return;
        }*/
        FuncionarioDB funcDB = new FuncionarioDB();
        SedeDB sedeDB = new SedeDB();
        Funcionario funcAsignado = null;
        try{
            funcAsignado = funcDB.getFuncFromDB(this.funcID);
            this.sedeSeleccionada = sedeDB.getSede(this.sedeID);
        } catch (Exception e){
            this.validationMessage = "Ocurrió un error en el sistema al cargar los datos";
        }
        if (funcAsignado != null) {
            //Se construye el objeto activo
            Activo activo = new Activo(idActivo, nombre, descripcion, valor, fechaAdquisicion, sedeSeleccionada, funcAsignado);

            try {
                activoDB.saveActivo(activo);
                this.validationMessage = "Activo registrado correctamente";
            } catch (SNMPExceptions e) {
                this.validationMessage = "Error al registrar Activo" + e.toString() + e.getMessage();
            } catch (SQLException e) {
                this.validationMessage = "Error al registrar Activo" + e.toString() + e.getMessage();
            }

            //Se registra el activo en la BD
        } else {
            this.validationMessage = "El funcionario elegido no existe o no esta registrado en el sistema";
        }

    }

    public void showMessage() {
        this.validationMessage = "Prueba";
    }

    public void fillSedes() {
        try {
            SedeDB sedeDB = new SedeDB();
            this.sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
    }

    public void fillFuncionarios() {
        try {
            FuncionarioDB funcDB = new FuncionarioDB();
            this.funcionarios = funcDB.getAllFuncionarios();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
    }

// <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public String getFuncID() {
        return funcID;
    }

    public void setFuncID(String funcID) {
        this.funcID = funcID;
    }

    public String getSedeID() {
        return sedeID;
    }

    public void setSedeID(String sedeID) {
        this.sedeID = sedeID;
    }

    public String getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(String idActivo) {
        this.idActivo = idActivo;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public Sede getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(Sede sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
// </editor-fold>
}
