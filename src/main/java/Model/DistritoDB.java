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
public class DistritoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public static Distrito getDistrictFromDB(int id) throws SNMPExceptions, SQLException{
        Distrito district = null;
        String sqlSelect = "";
        try{
            //Se crea la sentencia del Select
            sqlSelect = "Select IDDistrito, Nombre From Distrito Where IDDistrito = " + id;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            
            if(rs.next()){
                int idDistrito = rs.getInt("IDDistrito");
                String nombre = rs.getString("Nombre");
                //Se obtiene los barrios del distrito
                ArrayList<Barrio> barrios = BarrioDB.getBarriosByDistrito(id);
                
                //Se crea el objeto
                district = new Distrito(idDistrito, nombre, barrios);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return district;
    }
    
    public static ArrayList<Distrito> getDistrictsByCanton(int idCanton) throws SNMPExceptions, SQLException{
        ArrayList<Distrito> districts = new ArrayList<>();
        String sqlSelect = "";
        try{
            //Sentencia del Select
            sqlSelect = "Select IDDistrito, Nombre From Distrito Where IDCanton = " + idCanton;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            
            while(rs.next()){
                int idDistrict = rs.getInt("IDDistrito");
                String nombre = rs.getString("Nombre");
                //Se obtiene los barrios del distrito
                ArrayList<Barrio> barrios = BarrioDB.getBarriosByDistrito(idDistrict);
                //Se crea el objeto
                Distrito district = new Distrito(idDistrict, nombre, barrios);
                //Se agrega a la lista
                districts.add(district);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return districts;
    }
}
