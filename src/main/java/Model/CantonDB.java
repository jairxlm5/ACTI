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
public class CantonDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public static Canton getCantonFromDB(int id) throws SNMPExceptions, SQLException{
        Canton canton = null;
        String sqlSelect = "";
        try{
            sqlSelect = "Select IDCanton, Nombre From Canton Where IDCanton = " + id;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                int idCanton = rs.getInt("IDCanton");
                String nombre = rs.getString("Nombre");
                //Se trae los distritos que tiene
                ArrayList<Distrito> distritos = DistritoDB.getDistrictsByCanton(id);
                
                //Se crea el objeto
                canton = new Canton(idCanton, nombre, distritos);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return canton;
    }
    
    public static ArrayList<Canton> getCantonesByProvince(int idProvince) throws SNMPExceptions, SQLException{
        ArrayList<Canton> cantones = new ArrayList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select IDCanton, Nombre From Canton Where IDProvincia = " + idProvince;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
              int idCanton = rs.getInt("IDCanton");
              String nombre = rs.getString("Nombre");
              //Se trae los distritos que tiene
              ArrayList<Distrito> distritos = DistritoDB.getDistrictsByCanton(idCanton);
              
              //Se crea el objeto
              Canton canton = new Canton(idCanton, nombre, distritos);
              //Se agrega a la lista
              cantones.add(canton);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return cantones;
    }
}
