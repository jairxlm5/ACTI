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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class UsuarioPerfilDB {

    private static final DataAccess dataAccess = new DataAccess();
    //private static UsuarioDB usuarioDB = new UsuarioDB();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Obtiene todos los perfiles de un usuario
     * @param userID
     * @return LinkedList
     * @throws SNMPExceptions
     * @throws SQLException 
     */
    public LinkedList<UsuarioPerfil> getAccountsByUser(String userID) throws SNMPExceptions, SQLException {
        LinkedList<UsuarioPerfil> accountList = new LinkedList<>();
        String sqlSelect = "";
        try {
            sqlSelect = "Select Usuario, Tipo_Perfil, Fecha_Solicitud, Num_Solicitud "
                    + "From Solicitud_Usuario_Perfil Where Usuario like " + userID;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                String user = rs.getString("Usuario");
                Perfil tipoPerfil = Perfil.values()[rs.getInt("Tipo_Perfil")];
                Date fechaSolicitud = rs.getDate("Fecha_Solicitud");
                int numSolicitud = rs.getInt("Num_Solicitud");

                //Se crea el objeto
                UsuarioPerfil userAccount = new UsuarioPerfil();
                userAccount.setIdUsuario(user);
                userAccount.setTipoPerfil(tipoPerfil);
                userAccount.setFechaSolicitud(fechaSolicitud);
                userAccount.setNumSolicitud(numSolicitud);

                //Se agrega a la lista
                accountList.add(userAccount);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return accountList;
    }

    /**
     * Guarda un perfil en la DB
     *
     * @param profile
     */
    public void saveUserProfile(UsuarioPerfil profile) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try {
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Solicitud_Usuario_Perfil "
                    + "(Usuario, Tipo_Perfil, Fecha_Solicitud) Values (");
            str.append("'").append(profile.getIdUsuario()).append("', ");
            str.append(profile.getTipoPerfil().ordinal()).append(", ");
            str.append("'").append(simpleDateFormat.format(profile.getFechaSolicitud())).append("' )");
            
            sqlCommand = str.toString();
            //Se ejecuta el SQL
            dataAccess.executeSQLCommand(sqlCommand);
            
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Actualiza la informacion de un perfil en la DB
     * @param profileToUpdate 
     */
    /*
    public void updateUserProfile(UsuarioPerfil profileToUpdate) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            StringBuilder str = new StringBuilder();
            str.append("Update Solicitud_Usuario_Perfil Set ");
            str.append("Fecha_Aprobacion = ").append(profileToUpdate.getFechaAprobacion());
            str.append("Aprobacion = ").append(profileToUpdate.isAprobado());
            str.append("Where Num_Solicitud = ").append(profileToUpdate.getNumSolicitud());
            
            sqlCommand = str.toString();
            //Se ejecuta el SQL
            dataAccess.executeSQLCommand(sqlCommand);
            
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }*/
}
