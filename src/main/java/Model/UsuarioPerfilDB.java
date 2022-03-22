/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.Perfil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class UsuarioPerfilDB {
    private static final DataAccess dataAccess = new DataAccess();
    private static UsuarioDB usuarioDB = new UsuarioDB();
    
    public LinkedList<UsuarioPerfil> getAccountsOfUser(String userID) throws SNMPExceptions, SQLException{
        LinkedList<UsuarioPerfil> accountList = new LinkedList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select Usuario, Tipo_Perfil, Fecha_Solicitud, Fecha_Aprobacion, Num_Solicitud, "
                    + "Aprobacion From Solicitud_Usuario_Perfil Where Usuario like " + userID;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                String user = rs.getString("Usuario");
                Perfil tipoPerfil = Perfil.values()[rs.getInt("Tipo_Perfil")];
                Date fechaSolicitud = rs.getDate("Fecha_Solicitud");
                Date fechaAprobacion = rs.getDate("Fecha_Aprobacion");
                int numSolicitud = rs.getInt("Num_Solicitud");
                boolean aprobado = rs.getBoolean("Aprobacion");
                
                //Se crea el objeto
                UsuarioPerfil userAccount = new UsuarioPerfil();
                userAccount.setIdUsuario(user);
                userAccount.setTipoPerfil(tipoPerfil);
                userAccount.setFechaSolicitud(fechaSolicitud);
                userAccount.setFechaAprobacion(fechaAprobacion);
                userAccount.setNumSolicitud(numSolicitud);
                userAccount.setAprobado(aprobado);
                
                //Se agrega a la lista
                accountList.add(userAccount);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return accountList;
    }
}
