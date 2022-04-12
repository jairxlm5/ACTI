/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.Perfil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class FuncionarioDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    public void addNewFunc(String userID) throws SQLException, SNMPExceptions{
        String sqlCommand = "";
        try{
            sqlCommand = "Insert Into Funcionario Values ('" + userID + "')";
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
    
  //Metodo que hice mientras creo el codigo, aqui se tiene que hacer la llamada a traerse Funcionarios 
    public ArrayList<Funcionario> getAllFuncionarios() throws SNMPExceptions, SQLException {
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
       
        return funcionarios;
    }
    
    
      //Metodo que hice mientras creo el codigo, aqui se tiene que hacer la llamada a traerse Funcionarios 
    public ArrayList<Funcionario> getAllFuncionariosNuevos() throws SNMPExceptions, SQLException {
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
       
        return funcionarios;
    }
    
    /*
            public ArrayList<Perfil> getAllPerfiles() throws SNMPExceptions, SQLException {
        ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
        String sqlSelect = "";
        try {
            sqlSelect = "Select ID From Tipo_Perfil";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                int id = rs.getInt("ID");
                //Verifica si el Enum existe
                if (Perfil.values()[id] != null) {
                    perfiles.add(Perfil.values()[id]);
                }
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return perfiles;
    }
    */
    
    
    public Funcionario getFuncFromDB(String userID) throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        Funcionario funcUser = null;
        try{
            sqlSelect = "Select ID From Funcionario Where ID Like " + userID;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                String id = rs.getString("ID");
                funcUser = new Funcionario();
                funcUser.setIdentificacion(id);
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return funcUser;
    }
}
