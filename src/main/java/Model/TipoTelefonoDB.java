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
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class TipoTelefonoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public ArrayList<TipoTelefono> getPhoneTypes() throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        ArrayList<TipoTelefono> phoneTypes = new ArrayList<>();
        try{
            sqlSelect = "Select ID From Tipo_Telefono";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                int id = rs.getInt("ID");
                //Verifica si el Enum existe
                if(TipoTelefono.values()[id] != null){
                    phoneTypes.add(TipoTelefono.values()[id]);
                }
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return phoneTypes;
    }
}
