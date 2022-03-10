/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Enum.Perfil;
import Model.Usuario;

/**
 *
 * @author danielp
 */
public class LoginBean {
    //Toda referencia a Object se va a cambiar por el nombre de la clase respectiva cuando esta ya esten creadas
    private String username;
    private String password;
    private Perfil perfilSeleccionado;
    private String validationMessage;
    //private ArrayList<Object> tiposDePerfil; //Se llenara con objetos de la clase TipoPerfil y se usara para llenar el combo en el Login
    
    public LoginBean(){
        //this.tiposDePerfil = new ArrayList<>();
    }
    
    public String processLoginData(){
        this.validationMessage = "";
        if(this.username.equals("")){
            this.validationMessage = "Ingrese su numero de identificacion";
            return "";
        }
        if(this.password.equals("")){
            this.validationMessage = "Ingrese su clave";
            return "";
        }
        //Llamado a metodos que validan con la base de datos la informacion indicada por el usuario iniciando sesion
        Usuario user = getUserFromDB(); //Este metodo se trae el usuario de la base de datos
        /*passwordCorrect();
        userHasProfile();
        */
        
        //Dependiendo del tipo de perfil se abren las paginas respectivas
        switch(perfilSeleccionado){
            case Administrativo:
                return "MenuAdministrativo.xhtml";
            case Funcionario:
                //Se tiene que verificar si la cuenta ya esta habilitada
                return "MenuFuncionario.xhtml";
            case Tecnico:
                //Se tiene que validar si la cuenta ya esta habilitada
                return "MenuTecnico.xhtml";
            default:
                return "Debe indicar el perfil con que desea entrar";
        }
        
    }
    
    public Usuario getUserFromDB(){//Temporalmente esta definido como Object, luego se debe cambiar a la clase Usuario
        return null;
    }
    
    public boolean passwordCorrect(byte[] passwordStored){
        return false;
    }
    
    public boolean userHasProfile(){
        return false;
    }
    
    public boolean isAccountActivated(){
        return false;
    }
    
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
    
}
