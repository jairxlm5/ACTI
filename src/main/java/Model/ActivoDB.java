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
import java.util.Date;

/**
 *
 * @author danielp
 */
public class ActivoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    private SedeDB sedeDB = new SedeDB();
    private UsuarioDB userDB = new UsuarioDB();
    
    /**
     * Guarda los datos de un activo en la DB
     * @param activo
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public void saveActivo(Activo activo) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Activo Values (");
            str.append("'").append(activo.getIdActivo()).append("', ");
            str.append("'").append(activo.getNombre()).append("', ");
            str.append("'").append(activo.getDescripcion()).append("', ");
            str.append(activo.getValor()).append(", ");
            str.append("'").append(activo.getSede().getCodigo()).append("', ");
            str.append(activo.getFechaAdquisicion()).append(", ");
            str.append("'").append(activo.getFuncionario().getIdentificacion()).append("')");
            
            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Obtiene un activo de la DB
     * @param idActivo
     * @return Activo
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public Activo getActivoFromDB(String idActivo) throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        Activo activo = null;
        try{
            sqlSelect = "Select * From Activo Where ID Like " + idActivo;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String id = rs.getString("ID");
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                double valor = rs.getDouble("Valor");
                Sede sede = sedeDB.getSede(rs.getString("Sede"));
                Date fechaAdquisicion = rs.getDate("Fecha_Adquisicion");
                Funcionario func = (Funcionario)userDB.getUserFromDB(rs.getString("Funcionario"));
                
                activo = new Activo(id, nombre, descripcion, valor, fechaAdquisicion, sede, func);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return activo;
    }
    
    /**
     * Actualiza los datos de un activo en la DB
     * @param activo
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public void updateActivo(Activo activo) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            StringBuilder str = new StringBuilder();
            str.append("Update Activo Set ");
            str.append("Nombre = '").append(activo.getNombre()).append("', ");
            str.append("Descripcion = '").append(activo.getDescripcion()).append("', ");
            str.append("Valor = ").append(activo.getValor()).append(", ");
            str.append("Sede = '").append(activo.getSede().getCodigo()).append("', ");
            str.append("Fecha_Adquisicion = ").append(activo.getFechaAdquisicion()).append(", ");
            str.append("Funcionario = '").append(activo.getFuncionario().getIdentificacion()).append("' ");
            str.append("Where ID Like ").append(activo.getIdActivo());
            
            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Elimina un activo de la DB
     * @param activo
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public void deleteActivo(Activo activo) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            sqlCommand = "Delete From Activo Where ID Like " + activo.getIdActivo();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Obtiene todos los activos registrados a nombre del funcionario indicado
     * @param funcionario
     * @return ArrayList
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Activo> getActivosByFunc(Funcionario funcionario) throws SQLException, SNMPExceptions{
        ArrayList<Activo> activos = new ArrayList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select * From Activo Where Funcionario Like " + funcionario.getIdentificacion();
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String id = rs.getString("ID");
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                double valor = rs.getDouble("Valor");
                Sede sede = sedeDB.getSede(rs.getString("Sede"));
                Date fechaAdquisicion = rs.getDate("Fecha_Adquisicion");
                Funcionario func = (Funcionario)userDB.getUserFromDB(rs.getString("Funcionario"));
                
                Activo activo = new Activo(id, nombre, descripcion, valor, fechaAdquisicion, sede, func);
                activos.add(activo);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return activos;
    }
    
    /**
     * Obtiene todos los activos registrados que se encuentran en la sede indicada
     * @param sedeSeleccionada
     * @return ArrayList
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Activo> getActivosBySede(Sede sedeSeleccionada) throws SQLException, SNMPExceptions{
        ArrayList<Activo> activos = new ArrayList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select * From Activo Where Sede Like " + sedeSeleccionada.getCodigo();
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String id = rs.getString("ID");
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                double valor = rs.getDouble("Valor");
                Sede sede = sedeDB.getSede(rs.getString("Sede"));
                Date fechaAdquisicion = rs.getDate("Fecha_Adquisicion");
                Funcionario func = (Funcionario)userDB.getUserFromDB(rs.getString("Funcionario"));
                
                Activo activo = new Activo(id, nombre, descripcion, valor, fechaAdquisicion, sede, func);
                activos.add(activo);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return activos;
    }
}
