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
import Model.UsuarioDB;
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

    //Llamado a las clases DB
    UsuarioDB userDB = new UsuarioDB();

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
        Usuario user = null;
        try {
            user = userDB.getUserFromDB(this.username);
        } catch (Exception e) {
            this.validationMessage = "Ocurrió un error en el sistema, por favor intente mas tarde";
            e.printStackTrace();
            return "";
        }

        try {
            if (user != null) {
                //Se verifica que haya ingresado la contraseña correcta
                if (userDB.isLoginPasswordCorrect(user, this.password)) {
                    //Se tiene que verificar que el usuario esta habilitado
                    if (userDB.isUserActive(user)) {
                        //Se tiene que verificar si es el primer para que cambie la contraseña
                        if (userDB.isFirstLogin(user)) {
                            userDB.addNewLogin(user);
                            userDB.setLogedInUser(user, perfilSeleccionado);
                            return "CambiarContra.xhtml";
                        } else {
                            //Dependiendo del tipo de perfil se abren las paginas respectivas
                            switch (perfilSeleccionado) {
                                case Administrativo:
                                    //Se tiene que verificar si el usuario tiene ese perfil
                                    if(userDB.getUserProfile(user, perfilSeleccionado) != null){
                                        userDB.setLogedInUser(user, perfilSeleccionado);
                                        userDB.addNewLogin(user);
                                        return "MenuAdministrativo.xhtml";
                                    }
                                    this.validationMessage = "Usted no cuenta con perfil de Administrativo";
                                    break;
                                case Funcionario:
                                    //Se tiene que verificar si el usuario tiene ese perfil
                                    if(userDB.getUserProfile(user, perfilSeleccionado) != null){
                                        userDB.setLogedInUser(user, perfilSeleccionado);
                                        userDB.addNewLogin(user);
                                        return "MenuFuncionario.xhtml";
                                    }
                                    this.validationMessage = "Usted no cuenta con perfil de Funcionario";
                                    break;
                                case Tecnico:
                                    //Se tiene que verificar si el usuario tiene ese perfil
                                    if(userDB.getUserProfile(user, perfilSeleccionado) != null){
                                       userDB.setLogedInUser(user, perfilSeleccionado);
                                        userDB.addNewLogin(user);
                                        return "MenuTecnico.xhtml"; 
                                    }
                                    this.validationMessage = "Usted no cuenta con perfil de Técnico";
                                    break;
                                default:
                                    this.validationMessage = "Debe indicar el perfil con que desea entrar";
                                    return "Login.xhtml";
                            }
                        }
                    } else {
                        this.validationMessage = "Su cuenta no ha sido habilitada";
                        return "";
                    }
                } else {
                    this.validationMessage = "Contraseña Incorrecta";
                    return "";
                }
            } else {
                this.validationMessage = "Usuario no existe o no está registrado";
                return "";
            }
        } catch (Exception e) {
            this.validationMessage = "Ocurrió un error en el sistema, por favor intente mas tarde";
            e.printStackTrace();
        }
        return "";
    }

    public void clearData() {
        this.username = this.password = this.validationMessage = "";
        this.perfilSeleccionado = Perfil.PERFIL;
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
