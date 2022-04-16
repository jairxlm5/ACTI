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
import Model.Usuario;
import com.sun.accessibility.internal.resources.accessibility;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.model.SelectItem;

/**
 *
 * @author danielp
 */
public class CheckActivosAsignados {

    //ArrayList con las opciones para consulta, la idea es llenar un combo con esto
    private ArrayList<SelectItem> opcionesParaConsulta;
    //Opcion que el usuario elija
    private SelectItem opcionElegida;
    //Estos objetos solo se usa cuando se traen datos de consulta de la base de datos de objetos que ya estan registrados
    private Usuario funcionarioConsultado;
    private Sede sedeConsultada;
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String idUsuario; //El usuario digita este campo para hacer la consulta
    private String nombreUsuario; //Se llena solo, con la info que trae la consulta
    private String codigoSede; //El usuario digita este campo para hacer la consulta
    private String nombreSede; //Se llena solo, con la info que trae la consulta
    //ArrayList que se llena con la lista activos asignados al funcionario o a la sede elegida
    private ArrayList<Activo> activosAsignados;
    private ArrayList<Activo> activosAsignadosFiltrados;
    //Estos son los datos a desplegar en las columnas de la tabla
    private String idActivo;
    private String nombreActivo;
    private String descripcion;
    private double valor;
    private Date fechaAdquisicion;
    private Sede sede;
    private Funcionario funcionario;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    //Variable extra para usarse internamente
    private Map<String, Integer> mapOpciones;

    public CheckActivosAsignados() {
        this.activosAsignados = new ArrayList<>();
        this.idUsuario = "";
        this.nombreUsuario = "";
        this.codigoSede = "";
        this.nombreSede = "";
        this.nombreActivo = "";
        this.descripcion = "";
        this.validationMessage = "";
        this.mapOpciones = new HashMap<>();
        this.mapOpciones.put("Sede", 1);
        this.mapOpciones.put("Funcionario", 2);
        llenaListaOpciones();
    }

    public ArrayList<Activo> consulta() {
        validationMessage = "";
        return getActivosAsignados(mapOpciones.get(this.opcionElegida.getLabel()));
    }

    public ArrayList<Activo> getActivosAsignados() {

        for (int i = 0; i < 100; i++) {
            Activo activo = new Activo();
            Funcionario func = new Funcionario();
            func.setNombre("Daniel");
            activo.setFuncionario(null);
            activo.setDescripcion("addsd");
            Date fecha = new Date();
            activo.setFechaAdquisicion(fecha);
            activo.setIdActivo("" + i);
            activo.setNombre("Activo Numero" + i);
            activo.setValor(500);
            Sede sede = new Sede();
            sede.setCodigo("100");
            sede.setNombre("Central");
            sede.setUbicacion("Alajuela");
            activo.setSede(sede);
            activosAsignados.add(activo);
        }
        return activosAsignados;
    }

    //Obtiene los activos por funcionario para CheckActivosAsignados
    public ArrayList<Activo> getActivosAsignadosXFuncionario() {
        //Se hace la consulta por Funcionario
        if (this.funcionario != null) {
            if (this.funcionario instanceof Funcionario) {
                //Llamado a la consulta y se asigna el resultado a activosAsignados

                try {
                    ActivoDB activoDB = new ActivoDB();
                    activosAsignados = activoDB.getActivosByFunc(funcionario);
                } catch (SQLException e) {

                } catch (SNMPExceptions s) {

                }

                return activosAsignados;
            } else {
                validationMessage = "Esa persona no es un funcionario";
                return null;
            }
        }
        validationMessage = "Esa persona no existe o no esta registrada en el sistema";
        return null;

    }

    public ArrayList<Activo> getActivosAsignados(int opcionSeleccionada) {
        if (opcionSeleccionada == 1) {
            //Se hace la consulta por Sede
            if (this.sede != null) {
                //Llamado a la consulta y se asigna el resultado a activosAsignados

                return activosAsignados;
            }
            validationMessage = "La Sede no existe";
            return null;
        } else {
            if (opcionSeleccionada == 2) {
                //Se hace la consulta por Funcionario
                if (this.funcionario != null) {
                    if (this.funcionario instanceof Funcionario) {
                        //Llamado a la consulta y se asigna el resultado a activosAsignados

                        return activosAsignados;
                    } else {
                        validationMessage = "Esa persona no es un funcionario";
                        return null;
                    }
                }
                validationMessage = "Esa persona no existe o no esta registrada en el sistema";
                return null;
            } else {
                validationMessage = "Opcion Invalida: La consulta es por Sede o Funcionario";
                return null;
            }
        }
    }

    public void llenaListaOpciones() {
        this.opcionesParaConsulta = new ArrayList<>();
        this.opcionesParaConsulta.add(new SelectItem(1, "Sede"));
        this.opcionesParaConsulta.add(new SelectItem(2, "Funcionario"));
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public Usuario getFuncionarioConsultado() {
        return funcionarioConsultado;
    }

    public void setFuncionarioConsultado(Usuario funcionarioConsultado) {
        this.funcionarioConsultado = funcionarioConsultado;
    }

    public Sede getSedeConsultada() {
        return sedeConsultada;
    }

    public void setSedeConsultada(Sede sedeConsultada) {
        this.sedeConsultada = sedeConsultada;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigoSede() {
        return codigoSede;
    }

    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public void setActivosAsignados(ArrayList<Activo> activosAsignados) {
        this.activosAsignados = activosAsignados;
    }

    public String getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(String idActivo) {
        this.idActivo = idActivo;
    }

    public String getNombreActivo() {
        return nombreActivo;
    }

    public void setNombreActivo(String nombreActivo) {
        this.nombreActivo = nombreActivo;
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

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Map<String, Integer> getMapOpciones() {
        return mapOpciones;
    }

    public void setMapOpciones(Map<String, Integer> mapOpciones) {
        this.mapOpciones = mapOpciones;
    }

    public ArrayList<SelectItem> getOpcionesParaConsulta() {
        return opcionesParaConsulta;
    }

    public void setOpcionesParaConsulta(ArrayList<SelectItem> opcionesParaConsulta) {
        this.opcionesParaConsulta = opcionesParaConsulta;
    }

    public SelectItem getOpcionElegida() {
        return opcionElegida;
    }

    public void setOpcionElegida(SelectItem opcionElegida) {
        this.opcionElegida = opcionElegida;
    }

    public ArrayList<Activo> getActivosAsignadosFiltrados() {
        return activosAsignadosFiltrados;
    }

    public void setActivosAsignadosFiltrados(ArrayList<Activo> activosAsignadosFiltrados) {
        this.activosAsignadosFiltrados = activosAsignadosFiltrados;
    }
// </editor-fold>
}
