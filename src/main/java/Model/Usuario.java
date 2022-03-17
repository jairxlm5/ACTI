/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Enum.Perfil;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class Usuario {
    //Toda referencia a Object se va a cambiar por el nombre de la clase respectiva cuando esta ya esten creadas
    private String identificacion;
    private String nombre, apellido1, apellido2;
    private Date fechaNacimiento;
    private Provincia provincia;
    private Canton canton;
    private Distrito distrito;
    private Barrio barrio;
    private String otrasDirecciones;
    private String correo;
    private Sede sede;
    private LinkedList<Perfil> perfiles;
    private LinkedList<Telefono> telefonos;
    private int codSeguridad;
    private byte[] clave;

    public Usuario(String identificacion, String nombre, String apellido1, String apellido2, Date fechaNacimiento, Provincia provincia, 
                    Canton canton, Distrito distrito, Barrio barrio, String otrasDirecciones, String correo, Sede sede, 
                    LinkedList<Perfil> perfiles, LinkedList<Telefono> telefonos) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.barrio = barrio;
        this.otrasDirecciones = otrasDirecciones;
        this.correo = correo;
        this.sede = sede;
        this.perfiles = perfiles;
        this.telefonos = telefonos;
    }

    public Usuario(){
        
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
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

    public LinkedList<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(LinkedList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public LinkedList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public int getCodSeguridad() {
        return codSeguridad;
    }

    public void setCodSeguridad(int codSeguridad) {
        this.codSeguridad = codSeguridad;
    }

    public byte[] getClave() {
        return clave;
    }

    public void setClave(byte[] clave) {
        this.clave = clave;
    }
    
    
}
