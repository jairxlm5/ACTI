/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.TipoTelefono;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class TelefonoDB {
    private static final DataAccess dataAccess = new DataAccess();
    private UsuarioDB usuarioDB = new UsuarioDB();
    
    public LinkedList<Telefono> getUserPhoneNumbers(String userID) throws SNMPExceptions, SQLException{
        LinkedList<Telefono> telefonos = new LinkedList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select Numero_Telefonico, Usuario, "
                    + "Tipo_Telefono From Telefono Where Usuario like " + userID;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                String numero = rs.getString("Numero_Telefonico");
                String user = rs.getString("Usuario");
                TipoTelefono tipoTelefono = TipoTelefono.values()[rs.getInt("Tipo_Telefono")];
                
                //Se construye el objeto
                Telefono telefono = new Telefono(numero, user, tipoTelefono);
                //Se agrega a la lista
                telefonos.add(telefono);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return telefonos;
    }
}
