/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.TipoIdentificacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class TecnicoDB {
    private static final DataAccess dataAccess = new DataAccess();
    private SedeDB sedeDB = new SedeDB();
    private UsuarioPerfilDB usuarioPerfilDB = new UsuarioPerfilDB();
    private TelefonoDB telefonoDB = new TelefonoDB();
    
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
            sqlSelect = "Select Usuario.ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, Otras_Direcciones,\n" +
            "   Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, Barrio, Logins, Aprobacion,\n" +
            "   Fecha_Aprobacion From Usuario Inner Join Tecnico On Usuario.ID = '" + userID + "'";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                //String id = rs.getString("ID");
                //tecUser = new Funcionario();
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
                tecUser = new Tecnico();
                tecUser.setIdentificacion(identificacion);
                tecUser.setTipoID(tipoID);
                tecUser.setNombre(nombre);
                tecUser.setApellido1(apellido1);
                tecUser.setApellido2(apellido2);
                tecUser.setFechaNacimiento(fechaNacimiento);
                tecUser.setOtrasDirecciones(otrasDirecciones);
                tecUser.setCorreo(correo);
                tecUser.setSede(sede);
                tecUser.setCodSeguridad(codSeguridad);
                tecUser.setClave(clave);
                tecUser.setLogins(logins);
                tecUser.setProvincia(prov);
                tecUser.setCanton(canton);
                tecUser.setDistrito(distrito);
                tecUser.setBarrio(barrio);
                tecUser.setPerfiles(perfiles);
                tecUser.setTelefonos(telefonos);
                tecUser.setAprobado(aprobado);
                tecUser.setFechaAprobacion(fechaAprobacion);
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
