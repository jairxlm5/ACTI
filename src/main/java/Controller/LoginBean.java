/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Enum.Perfil;
import Model.PerfilDB;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class LoginBean {

    //Toda referencia a Object se va a cambiar por el nombre de la clase respectiva cuando esta ya esten creadas
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String username = "";
    private String password = "";
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Perfil perfilSeleccionado;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public LoginBean() {

    }

    public ArrayList<Perfil> getPerfilesDB() {
        ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
        try {
            PerfilDB perfilDB = new PerfilDB();
            perfiles = perfilDB.getAllPerfiles();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return perfiles;
    }

    public String processLoginData() {
        this.validationMessage = "";
        if (this.username.trim().length() == 0) {
            this.validationMessage = "Ingrese su numero de identificacion";
            return "";
        }

        if (this.password.trim().length() == 0) {
            this.validationMessage = "Ingrese su clave";
            return "";
        }
        //Llamado a metodos que validan con la base de datos la informacion indicada por el usuario iniciando sesion
        //  Usuario user = getUserFromDB(); //Este metodo se trae el usuario de la base de datos
        /*passwordCorrect();
        userHasProfile();
         */

        //Se tiene que guardar la info de la sesion
        //Dependiendo del tipo de perfil se abren las paginas respectivas
        switch (perfilSeleccionado) {
            case Administrativo:
                return "MenuAdministrativo.xhtml";
            case Funcionario:
                //Se tiene que verificar si la cuenta ya esta habilitada
                return "MenuFuncionario.xhtml";
            case Tecnico:
                //Se tiene que validar si la cuenta ya esta habilitada
                return "MenuTecnico.xhtml";
            default:
                this.validationMessage = "Debe indicar el perfil con que desea entrar";
                return "Login.xhtml";
        }

    }

    public void clearData() {
        this.username = this.password = this.validationMessage = "";
        this.perfilSeleccionado = Perfil.PERFIL;
    }

    public Usuario getUserFromDB() {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    /*
    public ArrayList<Object> getTiposDePerfil() {
        return tiposDePerfil;
    }

    public void setTiposDePerfil(ArrayList<Object> tiposDePerfil) {
        this.tiposDePerfil = tiposDePerfil;
    }
     */
// </editor-fold>
}
