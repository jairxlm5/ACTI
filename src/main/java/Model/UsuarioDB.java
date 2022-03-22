/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.TipoIdentificacion;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author danielp
 */
public class UsuarioDB {

    private static final DataAccess dataAccess = new DataAccess();
    private UsuarioPerfilDB usuarioPerfilDB = new UsuarioPerfilDB();
    private TelefonoDB telefonoDB = new TelefonoDB();

    public void addNewUser(Usuario newUser) throws SNMPExceptions, SQLException {
        String sqlCommand = "";
        try {
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Usuario Values (");
            str.append("'").append(newUser.getIdentificacion()).append("' ,");
            str.append(newUser.getTipoID().ordinal()).append(",");
            str.append("'").append(newUser.getNombre()).append("' ,");
            str.append("'").append(newUser.getApellido1()).append("' ,");
            str.append("'").append(newUser.getApellido2()).append("' ,");
            str.append(newUser.getFechaNacimiento()).append(",");
            str.append(newUser.getCanton().getId()).append(",");
            str.append("'").append(newUser.getOtrasDirecciones()).append("' ,");
            str.append("'").append(newUser.getCorreo()).append("' ,");
            str.append("'").append(newUser.getSede().getCodigo()).append("' ,");
            str.append(newUser.getCodSeguridad()).append(",");
            str.append(newUser.getClave()).append(",");
            str.append(newUser.getProvincia().getId()).append(",");
            str.append(newUser.getDistrito().getId()).append(",");
            str.append(newUser.getBarrio().getId()).append(",");

            sqlCommand = str.toString();
            //Se ejecuta la sentencia SQL
            dataAccess.executeSQLCommand(sqlCommand);

            //Dependiendo del tipo de Usuario se inserta en su respectiva tabla
            if (newUser instanceof Administrativo) {
                AdministrativoDB adminDB = new AdministrativoDB();
                adminDB.addNewAdmin(newUser.getIdentificacion());
            } else {
                if (newUser instanceof Funcionario) {
                    FuncionarioDB funcDB = new FuncionarioDB();
                    funcDB.addNewFunc(newUser.getIdentificacion());
                } else {
                    if (newUser instanceof Tecnico) {
                        TecnicoDB tecDB = new TecnicoDB();
                        tecDB.addNewTec(newUser.getIdentificacion());
                    }
                }
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public Usuario getUserFromDB(String id) throws SNMPExceptions, SQLException {
        String sqlSelect = "";
        Usuario user = null;
        try {
            //Sentencia de Select
            sqlSelect = "Select * From Usuario Where ID like " + id;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if (rs.next()) {
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
                byte[] clave = rs.getBytes("Clave");
                //Asignacion de objetos al usuario
                Provincia prov = ProvinciaDB.getProvinceFromDB(rs.getInt("Provincia"));
                Canton canton = CantonDB.getCantonFromDB(rs.getInt("Canton"));
                Distrito distrito = DistritoDB.getDistrictFromDB(rs.getInt("Distrito"));
                Barrio barrio = BarrioDB.getBarrioFromDB(rs.getInt("Barrio"));
                LinkedList<UsuarioPerfil> perfiles = usuarioPerfilDB.getAccountsOfUser(id);
                LinkedList<Telefono> telefonos = telefonoDB.getUserPhoneNumbers(id);

                //Se crea el usuario
                user = new Usuario();
                user.setIdentificacion(identificacion);
                user.setTipoID(tipoID);
                user.setNombre(nombre);
                user.setApellido1(apellido1);
                user.setApellido2(apellido2);
                user.setFechaNacimiento(fechaNacimiento);
                user.setOtrasDirecciones(otrasDirecciones);
                user.setCorreo(correo);
                user.setCodSeguridad(codSeguridad);
                user.setClave(clave);
                user.setProvincia(prov);
                user.setCanton(canton);
                user.setDistrito(distrito);
                user.setBarrio(barrio);
                user.setPerfiles(perfiles);
                user.setTelefonos(telefonos);
                
                //Se tiene que verificar que tipo de Usuario es
                

            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return user;
    }

    public static String getHashedPaswd(String password) throws Exception {
        //Primero obtener los bytes
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        //Luego obtener el hash en codigo hexadecimal y convertirlo a String
        StringBuilder hexString = new StringBuilder(2 * hashedBytes.length);
        for (int i = 0; i < hashedBytes.length; i++) {
            String hex = Integer.toHexString(0xff & hashedBytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
