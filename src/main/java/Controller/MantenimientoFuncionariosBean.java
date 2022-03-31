/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Enum.Perfil;
import Model.Barrio;
import Model.Canton;
import Model.Distrito;
import Model.Funcionario;
import Model.Provincia;
import Model.Sede;
import Model.Telefono;
import Model.Usuario;
import Model.UsuarioPerfil;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private String otrasDirecciones;
    private String correo;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Provincia provinciaSeleccionada;
    private Canton cantonSeleccionado;
    private Distrito distritoSeleccionado;
    private Barrio barrioSeleccionado;
    private Sede sede;
    //Estas dos listas solo tienen perfiles y telefonos que posee el usuario
    private LinkedList<UsuarioPerfil> perfiles;  
    private LinkedList<Telefono> telefonos;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
  
      private ArrayList<Funcionario> funcionarios;
       private ArrayList<Funcionario> funcionariosFiltrados;
    //Bro necesitaba la tabla funcionarios para poder cargar la data
    
    
    private ArrayList<Provincia> provincias;
    private ArrayList<Canton> cantones;
    private ArrayList<Distrito> distritos;
    private ArrayList<Barrio> barrios;
    private ArrayList<Sede> sedes;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    
          //Logica necesaria para las fechas
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fechaNacimiento = myCalendar.getTime();
    
    
    public MantenimientoFuncionariosBean() {
        this.perfiles = new LinkedList<>();
        this.telefonos = new LinkedList<>();
        this.funcionarios = new ArrayList();
         this.funcionariosFiltrados = new ArrayList();
        this.identificacion = "";
        this.nombre = "";
        this.apellido1 = "";
        this.apellido2 = "";
        this.correo = "";
        this.otrasDirecciones = "";
        this.validationMessage = "";
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
        Usuario newUser = new Usuario(identificacion, nombre, apellido1, apellido2, fechaNacimiento, provinciaSeleccionada, 
                cantonSeleccionado, distritoSeleccionado, barrioSeleccionado, otrasDirecciones, correo, sede, 
                perfiles, telefonos);
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
        Usuario newUser = new Usuario(identificacion, nombre, apellido1, apellido2, fechaNacimiento, provinciaSeleccionada, 
                cantonSeleccionado, distritoSeleccionado, barrioSeleccionado, otrasDirecciones, correo, sede, 
                perfiles, telefonos);
        
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
        this.provinciaSeleccionada = null;
        this.cantonSeleccionado = null;
        this.distritoSeleccionado = null;
        this.barrioSeleccionado = null;
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
        this.distritos = new ArrayList<>();
        this.barrios = new ArrayList<>();
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

    public Provincia getProvinciaSeleccionada() {
        return provinciaSeleccionada;
    }

    public void setProvinciaSeleccionada(Provincia provincia) {
        this.provinciaSeleccionada = provincia;
    }

    public Canton getCantonSeleccionado() {
        return cantonSeleccionado;
    }

    public void setCantonSeleccionado(Canton canton) {
        this.cantonSeleccionado = canton;
    }

    public Distrito getDistritoSeleccionado() {
        return distritoSeleccionado;
    }

    public void setDistritoSeleccionado(Distrito distritoSeleccionado) {
        this.distritoSeleccionado = distritoSeleccionado;
    }

    public Barrio getBarrioSeleccionado() {
        return barrioSeleccionado;
    }

    public void setBarrioSeleccionado(Barrio barrioSeleccionado) {
        this.barrioSeleccionado = barrioSeleccionado;
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

    public LinkedList<UsuarioPerfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(LinkedList<UsuarioPerfil> perfiles) {
        this.perfiles = perfiles;
    }

    public LinkedList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(ArrayList<Provincia> provincias) {
        this.provincias = provincias;
    }

    public ArrayList<Canton> getCantones() {
        return cantones;
    }

    public void setCantones(ArrayList<Canton> cantones) {
        this.cantones = cantones;
    }

    public ArrayList<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(ArrayList<Distrito> distritos) {
        this.distritos = distritos;
    }

    public ArrayList<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(ArrayList<Barrio> barrios) {
        this.barrios = barrios;
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

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ArrayList<Funcionario> getFuncionariosFiltrados() {
        return funcionariosFiltrados;
    }

    public void setFuncionariosFiltrados(ArrayList<Funcionario> funcionariosFiltrados) {
        this.funcionariosFiltrados = funcionariosFiltrados;
    }
    
    
    
}
