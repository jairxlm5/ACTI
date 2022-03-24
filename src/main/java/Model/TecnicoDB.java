/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author danielp
 */
public class TecnicoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public void addNewTec(String userID) throws SQLException, SNMPExceptions{
        String sqlCommand ="";
        try{
            sqlCommand = "Insert Into Tecnico Values ('" + userID + "')";
            //Se ejecuta el comando SQL
            dataAccess.executeSQLCommand(sqlCommand);
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    public Tecnico getTecFromDB(String userID) throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        Tecnico tecUser = null;
        try{
            sqlSelect = "Select ID From Tecnico Where ID Like " + userID;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String id = rs.getString("ID");
                tecUser = new Tecnico();
                tecUser.setIdentificacion(id);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return tecUser;
    }
}
