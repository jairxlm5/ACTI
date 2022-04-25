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
import Model.UsuarioDB;
import Model.UsuarioPerfil;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.FilterMeta;

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

    
      private List<FilterMeta> filterBy;
        private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Usuario> usuariosFiltrados = new ArrayList<>();
    private ArrayList<Usuario> usuariosParaMostrar = new ArrayList<>();
    private Usuario usuarioSelecionado = new Usuario();
     private Usuario usuario = new Usuario();

    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    public SolicitudFuncionariosBean() {
        this.getDisabledUsers();
        fillUsers();
    }

    //Para habilitar la cuenta de usuario seleccionada
    public void enableAccount() throws ParseException {
        if (this.usuarioSelecionado != null) {

            UsuarioDB userDB = new UsuarioDB();

            Usuario newUser = usuarioSelecionado;

            try {

                userDB.activateAccount(newUser);
             this.validationMessage = "Usuario activado con exito.";
            } catch (SQLException e) {
this.validationMessage = "Error" + e.toString();
            } catch (SNMPExceptions s) {
this.validationMessage = "Error" + s.toString();
            }

        }
    }

    //Para rechazar una solicitud de cuenta
    public void rejectRequest() throws ParseException {
           if (this.usuarioSelecionado != null) {

            UsuarioDB userDB = new UsuarioDB();
            Usuario newUser = usuarioSelecionado;

            try {

                userDB.deactivateAccount(newUser);
             
            } catch (SQLException e) {

            } catch (SNMPExceptions s) {

            }

        }
    }

    //Trae todos los usuarios que no tienen su cuenta habilitada
    public ArrayList<Usuario> getDisabledUsers() {
        this.disabledUsers = new ArrayList<>();
        ArrayList<Usuario> allUsers = new ArrayList<>();
        //Se almacena el resultado de la consulta en la lista allUsers
       
        
        
        for (Usuario user : allUsers) {
            //Si el usuario no esta aprobado se agrega a la lista
            if (!user.isAprobado()) {
                this.disabledUsers.add(user);
            }
        }
        return disabledUsers;
    }

    //Este es para poder traerse Las solicitudes de nuevos funcionarios
    public void fillUsers() {
        try {
            UsuarioDB userDB = new UsuarioDB();
            this.usuariosParaMostrar = userDB.getDisabledUsersFromDB();
          
         } catch (SQLException e) {

            } catch (SNMPExceptions s) {

            }
    }

    public void onRowSelect(SelectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Product Selected", String.valueOf(event.getObject().getNombre()));
        this.usuarioSelecionado =  ((Usuario) event.getObject());
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<Usuario> event) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterMeta> filterBy) {
        this.filterBy = filterBy;
    }


    
    
    
    
    
    
    

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public Usuario getSelectedUser() {
        return selectedUser;
    }
    
        public ArrayList<Usuario> getUsuariosParaMostrar() {
        return usuariosParaMostrar;
    }

    public void setUsuariosParaMostrar(ArrayList<Usuario> usuariosParaMostrar) {
        this.usuariosParaMostrar = usuariosParaMostrar;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(ArrayList<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

  

    public void setUsuarioSelecionado(Funcionario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
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


// </editor-fold>
}
