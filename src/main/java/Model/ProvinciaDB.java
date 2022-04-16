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
public class ProvinciaDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public static Provincia getProvinceFromDB(int id) throws SQLException, SNMPExceptions{
        Provincia prov = null;
        String sqlSelect = "";
        try{
            //Sentencia del Select
            sqlSelect = "Select From Provincia Where ID = " + id;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                int idProv = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                //Se trae los cantones que tiene
                ArrayList<Canton> cantones = CantonDB.getCantonesByProvince(id);
                
                //Se crea el objeto
                prov = new Provincia();
                prov.setId(idProv);
                prov.setNombre(nombre);
                prov.setCantones(cantones);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return prov;
    }
    
    public static ArrayList<Provincia> getAllProvincesFromDB() throws SNMPExceptions, SQLException{
        ArrayList<Provincia> provs = new ArrayList<Provincia>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select ID, Nombre From Provincia";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            
            while(rs.next()){
                int idProv = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                
                
                //Le tuve que comentar lo de obtener cantones daba error
                
                
             //   ArrayList<Canton> cantones = CantonDB.getCantonesByProvince(idProv);
                
                Provincia prov = new Provincia(idProv, nombre);
                //prov.setCantones(cantones);
                provs.add(prov);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return provs;
    }
}
