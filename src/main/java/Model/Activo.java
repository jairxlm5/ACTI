/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author danielp
 */
public class Activo {
    private String idActivo;
    private String nombre;
    private String descripcion;
    private double valor;
    private Date fechaAdquisicion;
    private Sede sede;
    private Funcionario funcionario;

    public Activo(String idActivo, String nombre, String descripcion, double valor, Date fechaAdquisicion, Sede sedeSeleccionada,
            Funcionario funcionario) {
        this.idActivo = idActivo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaAdquisicion = fechaAdquisicion;
        this.sede = sedeSeleccionada;
        this.funcionario = funcionario;
    }

    public Activo() {
        
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

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
