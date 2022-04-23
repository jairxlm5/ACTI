/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.Perfil;
import Enum.TipoIdentificacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

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
        UsuarioDB userDB = new UsuarioDB();
        SedeDB sedeDB = new SedeDB();
        UsuarioPerfilDB usuarioPerfilDB = new UsuarioPerfilDB();
        TelefonoDB telefonoDB = new TelefonoDB();
        String sqlSelect = "";
        Funcionario funcUser = null;
        try{
            sqlSelect = "Select ID From Funcionario";
            /*sqlSelect = "Select Usuario.ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, Otras_Direcciones,\n" +
            "   Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, Barrio, Logins, Aprobacion,\n" +
            "   Fecha_Aprobacion From Usuario Inner Join Funcionario On Usuario.ID = Funcionario.ID";*/
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect); 
            
            while(rs.next()){
                funcionarios.add(getFuncFromDB(rs.getString("ID")));
            }
        }
        catch(SQLException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        }
        catch(Exception e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
      
        
        return funcionarios;
    }
    
    public Funcionario getFuncFromDB(String userID) throws SQLException, SNMPExceptions{
        String sqlSelect = "";
        SedeDB sedeDB = new SedeDB();
        UsuarioPerfilDB usuarioPerfilDB = new UsuarioPerfilDB();
        TelefonoDB telefonoDB = new TelefonoDB();
        Funcionario funcUser = null;
        try{
            //sqlSelect = "Select ID From Funcionario Where ID Like " + userID;
            sqlSelect = "Select Usuario.ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, Otras_Direcciones,\n" +
            "   Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, Barrio, Logins, Aprobacion,\n" +
            "   Fecha_Aprobacion From Usuario Inner Join Funcionario On Usuario.ID = '" + userID + "'";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                //String id = rs.getString("ID");
                funcUser = new Funcionario();
                //Variables para asignar al usuario
                String identificacion = rs.getString("ID");
                TipoIdentificacion tipoID = TipoIdentificacion.values()[rs.getInt("TipoID")];
                String nombre = rs.getString("Nombre");
                String apellido1 = rs.getString("Apellido1");
                String apellido2 = rs.getString("Apellido2");
                Date fechaNacimiento = rs.getDate("Fecha_De_Nacimiento");
                String otrasDirecciones = rs.getString("Otras_Direcciones");
                String correo = rs.getString("Correo_Electronico");
                int codSeguridad = rs.getInt("Codigo_Seguridad");
                String clave = rs.getString("Clave");
                int logins = rs.getInt("Logins");
                boolean aprobado = rs.getBoolean("Aprobacion");
                Date fechaAprobacion = rs.getDate("Fecha_Aprobacion");
                //Asignacion de objetos al usuario
                Sede sede = sedeDB.getSede(rs.getString("Sede"));
                Provincia prov = ProvinciaDB.getProvinceFromDB(rs.getInt("Provincia"));
                Canton canton = CantonDB.getCantonFromDB(rs.getInt("Canton"), prov.getId());
                Distrito distrito = DistritoDB.getDistrictFromDB(rs.getInt("Distrito"), prov.getId(), canton.getId());
                Barrio barrio = BarrioDB.getBarrioFromDB(rs.getInt("Barrio"), prov.getId(), canton.getId(), distrito.getId());
                LinkedList<UsuarioPerfil> perfiles = usuarioPerfilDB.getAccountsByUser(identificacion);
                LinkedList<Telefono> telefonos = telefonoDB.getUserPhoneNumbers(identificacion);

                //Se asigna la data a las variables
                funcUser = new Funcionario();
                funcUser.setIdentificacion(identificacion);
                funcUser.setTipoID(tipoID);
                funcUser.setNombre(nombre);
                funcUser.setApellido1(apellido1);
                funcUser.setApellido2(apellido2);
                funcUser.setFechaNacimiento(fechaNacimiento);
                funcUser.setOtrasDirecciones(otrasDirecciones);
                funcUser.setCorreo(correo);
                funcUser.setSede(sede);
                funcUser.setCodSeguridad(codSeguridad);
                funcUser.setClave(clave);
                funcUser.setLogins(logins);
                funcUser.setProvincia(prov);
                funcUser.setCanton(canton);
                funcUser.setDistrito(distrito);
                funcUser.setBarrio(barrio);
                funcUser.setPerfiles(perfiles);
                funcUser.setTelefonos(telefonos);
                funcUser.setAprobado(aprobado);
                funcUser.setFechaAprobacion(fechaAprobacion);
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
