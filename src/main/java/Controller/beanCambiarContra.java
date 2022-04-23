/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import Model.UsuarioDB;
import Utils.Utils;

/**
 *
 * @author JAW
 */
public class beanCambiarContra {

    private String claveAutogenerada;
    private int codigoSeguridad;
    private String claveNueva;
    private String message;

    /*
    public beanCambiarContra(String claveAutogenerada, String codigoSeguridad, String claveNueva) {
        this.claveAutogenerada = claveAutogenerada;
        this.codigoSeguridad = codigoSeguridad;
        this.claveNueva = claveNueva;
    }
     */
    public beanCambiarContra() {
        
    }

    public void changePass() {
        this.message = "";
        //Validaciones
        if (this.codigoSeguridad == 0 || String.valueOf(this.codigoSeguridad).trim().length() != 6) {
            this.message = "El código de seguridad debe tener 6 dígitos";
            return;
        }
        if (this.claveAutogenerada.trim().length() == 0) {
            this.message = "Ingrese la contraseña enviada a su correo";
            return;
        }
        if (this.claveNueva.trim().length() == 0) {
            this.message = "Por favor digite su nueva contraseña";
            return;
        }
        //Se verifica si el codigo de seguridad y la clave son correctas
        UsuarioDB userDB = new UsuarioDB();
        Usuario user = userDB.getLogedInUser();
        if (user.getCodSeguridad() != this.codigoSeguridad) {
            this.message = "Código de Seguridad Incorrecto";
            return;
        }
        try {
            if (!user.getClave().equals(Utils.getHashedPaswd(this.claveAutogenerada))) {
                this.message = "Clave Autogenerada Incorrecta";
                return;
            }
            //Se verifica que cumpla con los requisitos del sistema
            String passwordPassRequirements = userDB.passwordPassRequirements(this.claveNueva);
            if(!passwordPassRequirements.equals("Passed")){
                this.message = passwordPassRequirements;
                return;
            }
            this.claveNueva = Utils.getHashedPaswd(this.claveNueva);
        } catch (Exception e) {
            this.message = "Ocurrio un error con la clave";
            return;
        }
        //Se hace el proceso de cambio de clave
        user.setClave(claveNueva);
        //Se actualiza en la DB
        try{
            userDB.updateUser(user);
            this.message = "Su contraseña ha sido modificada, regrese para ingresar a su cuenta con la nueva contraseña";
            return;
        } catch (Exception e){
            this.message = "Error al cambiar contraseña " + e.getMessage() + e.toString();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public String getClaveAutogenerada() {
        return claveAutogenerada;
    }

    public void setClaveAutogenerada(String claveAutogenerada) {
        this.claveAutogenerada = claveAutogenerada;
    }

    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

// </editor-fold>
}
