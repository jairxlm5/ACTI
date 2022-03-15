/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Enum.TipoIdentificacion;
import Model.Sede;
import Model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class AutoregistroBean {
    //Toda referencia a Object se va a cambiar por el nombre de la clase respectiva cuando esta ya esten creadas
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private TipoIdentificacion tipoIdSeleccionado;
    private String identificacion;
    private String nombre, apellido1, apellido2;
    private String correo;
    private Date fechaNacimiento;
    private int edad;
    private String otrasDirecciones;
    //Estas dos listas solo tienen perfiles y telefonos que posee el usuario
    private LinkedList<Object> telefonos;
    private LinkedList<Object> perfiles;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Object> provincias;
    private ArrayList<Object> cantones;
    private ArrayList<Sede> sedes;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Object provincia, canton;
    private Sede sede;
    //Mensaje para desplegar info de validaciones
    private String validationMessage = "";
    
    public AutoregistroBean(){
        this.telefonos = new LinkedList<>();
        this.perfiles = new LinkedList<>();
        this.sedes = new ArrayList<>();
        this.provincias = new ArrayList<>();
        this.cantones = new ArrayList<>();
    }
    
    public void cleanData(){
        this.identificacion = this.nombre = this.apellido1 = this.apellido2 = this.otrasDirecciones = this.correo = this.validationMessage = "";
        this.fechaNacimiento = null;
        this.edad = 0;
        this.telefonos.clear();
        this.perfiles.clear();
    }
    
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
     * Llena los ArrayList que a su vez llenan los combos en la pagina xhtml
     */
    public void fillCombos(){
        
    }

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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getOtrasDirecciones() {
        return otrasDirecciones;
    }

    public void setOtrasDirecciones(String otrasDirecciones) {
        this.otrasDirecciones = otrasDirecciones;
    }

    public LinkedList<Object> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Object> telefonos) {
        this.telefonos = telefonos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }
    
    public LinkedList<Object> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(LinkedList<Object> perfiles) {
        this.perfiles = perfiles;
    }
    
    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public TipoIdentificacion getTipoIdSeleccionado() {
        return tipoIdSeleccionado;
    }

    public void setTipoIdSeleccionado(TipoIdentificacion tipoIdSeleccionado) {
        this.tipoIdSeleccionado = tipoIdSeleccionado;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
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
    
    
    
}
    
