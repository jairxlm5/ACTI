/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Funcionario;
import Model.FuncionarioDB;
import Model.Usuario;
import Model.UsuarioPerfil;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

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
    private Funcionario funcionarioSelecionado = new Funcionario();
    
    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public SolicitudFuncionariosBean() {
        this.getDisabledUsers();
        this.validationMessage = "";
    }
    
    
    //Para habilitar la cuenta de usuario seleccionada
    public void enableAccount(){
        if(this.funcionarioSelecionado != null){
            //Llamado a proceso para habilitar cuenta
            
            
            
        }
    }
    
    
    //Para rechazar una solicitud de cuenta
    public void rejectRequest(){
          if(this.funcionarioSelecionado != null){
            //Llamado a proceso para habilitar cuenta
        }
    }
    
 
     //Trae todos los usuarios que no tienen su cuenta habilitada
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
    
    
    //Este es para poder traerse Las solicitudes de nuevos funcionarios
        public ArrayList<Funcionario> getSolicitudNewFuncionariosDB(){
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
       /* try {
            FuncionarioDB funcionarioDB = new FuncionarioDB();
            funcionarios = funcionarioDB.getAllFuncionariosNuevos();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }*/
        return funcionarios;
    }


       public void onRowSelect(SelectEvent<Funcionario> event) {
        FacesMessage msg = new FacesMessage("Product Selected", String.valueOf(event.getObject().getNombre()));
        
        this.funcionarioSelecionado.setIdentificacion(event.getObject().getIdentificacion());
        this.funcionarioSelecionado.setNombre(event.getObject().getNombre());
        this.funcionarioSelecionado.setApellido1(event.getObject().getApellido1());
        this.funcionarioSelecionado.setApellido2(event.getObject().getApellido2());
        this.funcionarioSelecionado.setFechaNacimiento(event.getObject().getFechaNacimiento());
        this.funcionarioSelecionado.setCorreo(event.getObject().getNombre());
        this.funcionarioSelecionado.setTelefonos(event.getObject().getTelefonos());
        
        
    
               // this.funcionarioSelecionado.setAprobado(true);
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<Funcionario> event) {
        FacesMessage msg = new FacesMessage("Product Unselected", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
      public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }

    
    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
 public Usuario getSelectedUser() {
        return selectedUser;
    }

         
    public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
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
    
// </editor-fold>

   
    
}
