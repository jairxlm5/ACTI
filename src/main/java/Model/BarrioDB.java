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
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class BarrioDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public static Barrio getBarrioFromDB(int id) throws SQLException, SNMPExceptions{
        Barrio barrio = null;
        String sqlSelect = "";
        try{
            //Se crea la sentencia del Select
            sqlSelect = "Select IDBarrio, Nombre From Barrio Where IDBarrio = " + id;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            
            if(rs.next()){
                int idBarrio = rs.getInt("IDBarrio");
                String nombre = rs.getString("Nombre");
                
                //Se crea el objeto
                barrio = new Barrio(idBarrio, nombre);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return barrio;
    }
    
    public static ArrayList<Barrio> getBarriosByDistrito(int idDistrito) throws SNMPExceptions, SQLException{
        ArrayList<Barrio> barrios = new ArrayList<>();
        String sqlSelect = "";
        try{
            //Sentencia del Select
            sqlSelect = "Select IDBarrio, Nombre From Barrio Where IDDistrito = " + idDistrito;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            
            while(rs.next()){
                int idBarrio = rs.getInt("IDBarrio");
                String nombre = rs.getString("Nombre");
                //Se crea el objeto
                Barrio barrio = new Barrio(idBarrio, nombre);
                //Se agrega a la lista
                barrios.add(barrio);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return barrios;
    }
}
