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
public class SedeDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    /**
     * Trae la informacion de la sede elegida
     * @param codigo
     * @return Sede
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public Sede getSede(String codigo) throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        Sede sede = null;
        try{
            sqlSelect = "Select Codigo, Nombre, Ubicacion From Sede Where Codigo Like '" + codigo + "'";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String code = rs.getString("Codigo");
                String nombre = rs.getString("Nombre");
                String ubicacion = rs.getString("Ubicacion");
                sede = new Sede(code, nombre, ubicacion);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return sede;
    }
    
    /**
     * Retorna una lista de todas las sedes en la DB
     * @return ArrayList
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Sede> getAllSedes() throws SQLException, SNMPExceptions{
        ArrayList<Sede> sedes = new ArrayList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select * From Sede";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre");
                String ubicacion = rs.getString("Ubicacion");
                Sede sede = new Sede(codigo, nombre, ubicacion);
                sedes.add(sede);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return sedes;
    }
}
