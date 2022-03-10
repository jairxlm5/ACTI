/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class Usuario {
    private String identificacion;
    private String nombre, apellido2, apellido3;
    private Date fechaNacimiento;
    private Object provincia;
    private Object canton;
    private String otrasDirecciones;
    private String correo;
    private Object sede;
    private LinkedList<Object> perfiles;
    private LinkedList<Object> telefonos;
    private int codSeguridad;
    private byte[] clave;

    public Usuario(String identificacion, String nombre, String apellido2, String apellido3, Date fechaNacimiento, Object provincia,
            Object canton, String otrasDirecciones, String correo, Object sede, LinkedList<Object> perfiles, LinkedList<Object> telefonos) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido2 = apellido2;
        this.apellido3 = apellido3;
        this.fechaNacimiento = fechaNacimiento;
        this.provincia = provincia;
        this.canton = canton;
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

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellido3() {
        return apellido3;
    }

    public void setApellido3(String apellido3) {
        this.apellido3 = apellido3;
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

    public Object getSede() {
        return sede;
    }

    public void setSede(Object sede) {
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
