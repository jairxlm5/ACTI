/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Sede;
import Model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class MantenimientoFuncionariosBean {
    //Este objeto Usuario solo se usa cuando se traen datos de consulta de la base de datos de un Usuario que ya existe
    private Usuario user;
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String identificacion;
    private String nombre, apellido1, apellido2;
    private Date fechaNacimiento;
    private String otrasDirecciones;
    private String correo;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Object provincia;
    private Object canton;
    private Sede sede;
    //Estas dos listas solo tienen perfiles y telefonos que posee el usuario
    private LinkedList<Object> perfiles;  
    private LinkedList<Object> telefonos;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Object> provincias;
    private ArrayList<Object> cantones;
    private ArrayList<Sede> sedes;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public MantenimientoFuncionariosBean() {
        this.perfiles = new LinkedList<>();
        this.telefonos = new LinkedList<>();
        this.fillComboLists();
    }
    
    /**
     * Obtiene la informacion de un usuario ya registrado en el sistema ya sea para editarlo y simplemente ver
     * los datos
     * @return Usuario
     */
    public Usuario getUser(){
        return null;
    }
    
    /**
     * Guarda un usuario nuevo
     */
    public void saveNewUser(){
        this.validationMessage = "";
        //Primero validar que todos los datos se hayan ingresado
        if(this.identificacion.trim().length() == 0){
            this.validationMessage = "Ingrese su Nombre";
            return;
        }
        if(this.apellido1.trim().length() == 0){
            this.validationMessage = "Ingrese su Primer Apellido";
            return;
        }
        if(this.apellido2.trim().length() == 0){
            this.validationMessage = "Ingrese su Segundo Apellido";
            return;
        }
        if(this.fechaNacimiento == null){
            this.validationMessage = "Indique su Fecha de Nacimiento";
            return;
        }
        if(this.otrasDirecciones.trim().length() == 0){
            this.validationMessage = "Indique su Dirección";
            return;
        }
        if(this.correo.trim().length() == 0){
            this.validationMessage = "Ingrese su Correo Electrónico";
            return;
        }
        if(this.telefonos.isEmpty()){
            this.validationMessage = "Debe registrar al menos un teléfono";
            return;
        }
        if(this.perfiles.isEmpty()){
            this.validationMessage = "Debe usar al menos un tipo de perfil";
            return;
        }
        //Se tiene que crear el nuevo usuario
        Usuario newUser = new Usuario(identificacion, nombre, apellido2, apellido1, fechaNacimiento, provincia, canton, 
                otrasDirecciones, correo, sede, perfiles, telefonos);
        //Una vez creado el usuario se tiene que enviar un correo con un codigo de seguridad y la clave de primer ingreso
        
        //Se tiene que guardar toda la informacion del Usuario en la base de datos
    }
    
    /**
     * Edita la informacion de un usuario ya existente
     */
    public void editUser(){
        //Se tienen que aplicar casi el mismo proceso a cuando se registra un usuario nuevo
        this.validationMessage = "";
        //Primero validar que todos los datos se hayan ingresado
        if(this.identificacion.trim().length() == 0){
            this.validationMessage = "Ingrese su Nombre";
            return;
        }
        if(this.apellido1.trim().length() == 0){
            this.validationMessage = "Ingrese su Primer Apellido";
            return;
        }
        if(this.apellido2.trim().length() == 0){
            this.validationMessage = "Ingrese su Segundo Apellido";
            return;
        }
        if(this.fechaNacimiento == null){
            this.validationMessage = "Indique su Fecha de Nacimiento";
            return;
        }
        if(this.otrasDirecciones.trim().length() == 0){
            this.validationMessage = "Indique su Dirección";
            return;
        }
        if(this.correo.trim().length() == 0){
            this.validationMessage = "Ingrese su Correo Electrónico";
            return;
        }
        if(this.telefonos.isEmpty()){
            this.validationMessage = "Debe registrar al menos un teléfono";
            return;
        }
        if(this.perfiles.isEmpty()){
            this.validationMessage = "Debe usar al menos un tipo de perfil";
            return;
        }
        //Se tiene que crear un objeto usuario con la informacion editada
        Usuario newUser = new Usuario(identificacion, nombre, apellido2, apellido1, fechaNacimiento, provincia, canton, 
                otrasDirecciones, correo, sede, perfiles, telefonos);
        
        //Se actualiza la informacion en la BD
    }
    
    /**
     * Para deshabilitar una cuenta de usuario
     */
    public void disableUser(){
        
    }
    
    /**
     * Limpia la informacion
     */
    public void cleanData(){
        this.identificacion = this.nombre = this.apellido1 = this.apellido2 = this.correo = this.otrasDirecciones = "";
        this.fechaNacimiento = null;
        this.provincia = null;
        this.canton = null;
        this.sede = null;
        this.perfiles.clear();
        this.telefonos.clear();
        this.user = null;
        this.validationMessage = "";
        //Los ArrayLists no se limpian
    }
    
    /**
     * Llena los listas que llenan los combos en el xhtml
     */
    public void fillComboLists(){
        this.cantones = new ArrayList<>();
        this.provincias = new ArrayList<>();
        this.sedes = new ArrayList<>();
    }
    
    /*
      METODOS GET Y SET
    */

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Object getProvincia() {
        return provincia;
    }

    public void setProvincia(Object provincia) {
        this.provincia = provincia;
    }

    public Object getCanton() {
        return canton;
    }

    public void setCanton(Object canton) {
        this.canton = canton;
    }

    public String getOtrasDirecciones() {
        return otrasDirecciones;
    }

    public void setOtrasDirecciones(String otrasDirecciones) {
        this.otrasDirecciones = otrasDirecciones;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public LinkedList<Object> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(LinkedList<Object> perfiles) {
        this.perfiles = perfiles;
    }

    public LinkedList<Object> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Object> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Object> getProvincias() {
        return provincias;
    }

    public void setProvincias(ArrayList<Object> provincias) {
        this.provincias = provincias;
    }

    public ArrayList<Object> getCantones() {
        return cantones;
    }

    public void setCantones(ArrayList<Object> cantones) {
        this.cantones = cantones;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
    
}
