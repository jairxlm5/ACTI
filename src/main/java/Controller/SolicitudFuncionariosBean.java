/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Funcionario;
import Model.Usuario;
import Model.UsuarioPerfil;
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class SolicitudFuncionariosBean {
    //Este objeto almacena el usuario seleccionado
    private Usuario selectedUser;
    //Lista con todos los usuarios registrados en el sistema
    private ArrayList<Usuario> disabledUsers;
    
    //Tuve que meter este ArrayList para lo del primer avance luego podemos ver como bretearlo 
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private ArrayList<Funcionario> funcionariosFiltrados = new ArrayList<>();
    
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public SolicitudFuncionariosBean() {
        this.getDisabledUsers();
        this.validationMessage = "";
    }
    
    /**
     * Para habilitar la cuenta de usuario seleccionada
     */
    public void enableAccount(){
        if(this.selectedUser != null){
            //Llamado a proceso para habilitar cuenta
        }
    }
    
    /**
     * Para rechazar una solicitud de cuenta
     */
    public void rejectRequest(){
        
    }
    
    /**
     * Trae todos los usuarios que no tienen su cuenta habilitada
     * @return ArrayList<Usuario>
     */
    public ArrayList<Usuario> getDisabledUsers() {
        this.disabledUsers = new ArrayList<>();
        ArrayList<Usuario> allUsers = new ArrayList<>();
        //Se almacena el resultado de la consulta en la lista allUsers
        
        for (Usuario user : allUsers) {
            //Si el usuario no esta aprobado se agrega a la lista
            if(!user.isAprobado()){
                this.disabledUsers.add(user);
            }
        }
        return disabledUsers;
    }

    public Usuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void setDisabledUsers(ArrayList<Usuario> disabledUsers) {
        this.disabledUsers = disabledUsers;
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
