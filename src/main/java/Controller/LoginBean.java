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
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String username;
    private String password;
    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private Perfil perfilSeleccionado;
    //Mensaje para desplegar info de validaciones
    private String validationMessage;
    
    public LoginBean(){
        
    }
    
    public String processLoginData(){
        this.validationMessage = "";
        if(this.username.trim().length() == 0){
            this.validationMessage = "Ingrese su numero de identificacion";
            return "";
        }
        if(this.password.trim().length() == 0){
            this.validationMessage = "Ingrese su clave";
            return "";
        }
        //Llamado a metodos que validan con la base de datos la informacion indicada por el usuario iniciando sesion
        Usuario user = getUserFromDB(); //Este metodo se trae el usuario de la base de datos
        /*passwordCorrect();
        userHasProfile();
        */
        
        //Se tiene que guardar la info de la sesion
        
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
    
    public Usuario getUserFromDB(){
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
    
    /*
      METODOS GET Y SET
    */
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
