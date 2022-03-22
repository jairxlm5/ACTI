/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Enum.Perfil;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class Funcionario extends Usuario{
    
    public Funcionario(String identificacion, String nombre, String apellido1, String apellido2, Date fechaNacimiento, Provincia provincia, 
                    Canton canton, Distrito distrito, Barrio barrio, String otrasDirecciones, String correo, Sede sede, 
                    LinkedList<UsuarioPerfil> perfiles, LinkedList<Telefono> telefonos){
        super(identificacion, nombre, apellido1, apellido2, fechaNacimiento, provincia, canton, 
                distrito, barrio, otrasDirecciones, correo, sede, perfiles, telefonos);
    }
    
    public Funcionario(){
        super();
    }
}
