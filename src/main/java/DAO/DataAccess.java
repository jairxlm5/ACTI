/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/*
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
*/
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Properties;
import javax.naming.NamingException;

/**
 *
 * @author danielp
 */
public class DataAccess implements Serializable{
    //Motor de DB
    static final int SQL_SERVER = 1;
    
    //Constantes que identifican resultados
    static final int OPERACION_EFECTUADA = 1;
    static final int ERROR_EJECUCION = 0;
    @SuppressWarnings("compatibility:-5753742077201878016")
    private static final long serialVersionUID = 1L;
    private transient Connection dbConn = null;
    private transient Statement stmt = null;
    
    public DataAccess(){
        super();
    }
    
    /**
     * Ejecuta una instruccion SQL, NO retorna datos 
     * @param sqlCommand
     * @return
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public int executeSQLCommand(String sqlCommand) throws SNMPExceptions, SQLException, NamingException, ClassNotFoundException{
        //Driver para conexion a DB
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dbConn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ACTI", "sa", "123456");
        try{
            //Creacion del statement
            stmt = dbConn.createStatement();
            stmt.execute(sqlCommand);
            return OPERACION_EFECTUADA;
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        finally{
            try{
                stmt.close();
            }
            catch(SQLException e){
                 throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
            }
        }
    }
    
    /**
     * Ejecuta una instruccion SQL y retorna un ResultSet
     * @param sqlCommand
     * @return ResultSet
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public ResultSet executeSQLReturnsRS(String sqlCommand) throws SNMPExceptions, SQLException, NamingException, ClassNotFoundException{
        //ResultSet que se llenara con la data traida de la base de datos
        ResultSet rs = null;
        //Driver para conexion a DB
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dbConn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ACTI", "sa", "123456");
        try{
            //Creacion del statement
            stmt = dbConn.createStatement();
            //Ejecuta la instruccion SQL
            rs = stmt.executeQuery(sqlCommand);
            return rs;
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                  e.getMessage(), e.getErrorCode());
        }
    } 
}
