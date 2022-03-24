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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author danielp
 */
public class UsuarioDB {

    private static final DataAccess dataAccess = new DataAccess();
    private UsuarioPerfilDB usuarioPerfilDB = new UsuarioPerfilDB();
    private TelefonoDB telefonoDB = new TelefonoDB();
    private AdministrativoDB adminDB = new AdministrativoDB();
    private FuncionarioDB funcDB = new FuncionarioDB();
    private TecnicoDB tecDB = new TecnicoDB();

    //Objeto Usuario para usar en la logica
    private Usuario selectedUser = null;

    public void addNewUser(Usuario newUser) throws SNMPExceptions, SQLException {
        String sqlCommand = "";
        try {
            //Se arma la sentencia del Insert
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
                    } else {
                        throw new Exception("El usuario " + newUser.getIdentificacion() + " no eligio un perfil");
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

                //Se tiene que verificar que tipo de Usuario es para crearlo
                if (adminDB.getAdminFromDB(id) != null) {
                    user = new Administrativo();
                } else {
                    if (funcDB.getFuncFromDB(id) != null) {
                        user = new Funcionario();
                    } else {
                        if (tecDB.getTecFromDB(id) != null) {
                            user = new Tecnico();
                        } else {
                            throw new Exception("Error: El usuario " + id + " no tiene perfil registrado");
                        }
                    }
                }

                //Se asigna la data a las variables
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

                //Se asigna al objeto selectedUser
                selectedUser = user;

            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return user;
    }

    /**
     * Encripta la clave con un Hash para guardarse de esa manera en la DB
     *
     * @param password
     * @return
     * @throws Exception
     */
    public String getHashedPaswd(String password) throws Exception {
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

    /**
     * Genera la clave para el primer inicio de sesion
     *
     * @return
     */
    public String generateFirstPasswd() {
        //Lista con 10 numeros aleatorios
        ArrayList<Integer> randNums = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            //Numeros entre 33 y 125
            int randomNumber = random.nextInt((125 - 33) + 1) + 33;
            randNums.add(randomNumber);
        }
        String autoPasswd = "";
        for (Integer num : randNums) {
            //Se agrega el caracter ASCII de cada numero a la clave autogenerada
            autoPasswd += (char) (int) num;
        }
        return autoPasswd;
    }

    /**
     * Genera el codigo de seguridad de 6 digitos para el usuario
     * @return
     */
    public int generateSecurityCode() {
        //Lista que contiene los 6 digitos para el codigo de seguridad
        String securityCodeDigits = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            //Numeros entre 0 y 9
            int digit = random.nextInt((9 - 0) + 1) + 0;
            securityCodeDigits += digit;
        }
        /*Hay que garantizar que se genere un numero de 6 digitos
        Si el primer digito es 0 se debe agregar otro digito mas al final porque el 0
        a la izquierda no se cuenta */
        if (securityCodeDigits.charAt(0) == '0') {
            int digit = random.nextInt((9 - 0) + 1) + 0;
            securityCodeDigits += digit;
        }
        return Integer.parseInt(securityCodeDigits);
    }

    /**
     * Verifica que la clave ingresada el iniciar sesion sea la misma que la 
     * guardada en la base de datos
     * @param passwordInserted
     * @return
     * @throws Exception 
     */
    public boolean isPasswordCorrect(String passwordInserted) throws Exception {
        if (selectedUser != null) {
            //Se obtiene el Hash de la clave ingresada en el inicio de sesion
            passwordInserted = getHashedPaswd(passwordInserted);
            //Se tiene que extraer el Hash de los bytes de la clave guardada en la DB
            String passwordInDB = new String(selectedUser.getClave(), StandardCharsets.UTF_8);
            return passwordInserted.equals(passwordInDB);
        } else {
            throw new Exception("Usuario no ha sido seleccionado");
        }
    }
    
    public boolean userHasProfile(){
        return false;
    }
    
    public boolean isProfileActive(){
        return false;
    }

}
