/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import java.sql.SQLException;

/**
 *
 * @author danielp
 */
public class AdministrativoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public void addNewAdmin(String userID) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            sqlCommand = "Insert Into Administrativo Values('" + userID + "')";
            //Se ejecuta la sentencia SQL
            dataAccess.executeSQLCommand(sqlCommand);
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
}
