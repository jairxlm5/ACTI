/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utils.Utils;
import Utils.ACTIException;
import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.Perfil;
import Enum.TipoIdentificacion;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
    private SedeDB sedeDB = new SedeDB();
    
    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //Objeto Usuario para usar en la logica
    private Usuario selectedUser = null;

    public void addNewUser(Usuario newUser) throws SNMPExceptions, SQLException {
        String sqlCommand = "";
        try {
            //Se arma la sentencia del Insert
            StringBuilder str = new StringBuilder();
            /*str.append("Insert Into Usuario "
                    + "(ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, Otras_Direcciones,"
                    + "+ Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, Barrio, Logins, Aprobacion)"+                  
                    "Values (");*/
            str.append("Insert Into Usuario (ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, "
                    + "Otras_Direcciones, Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, "
                    + "Barrio, Logins) Values (");
            str.append("'").append(newUser.getIdentificacion()).append("', ");
            str.append(newUser.getTipoID().ordinal()).append(", ");
            str.append("'").append(newUser.getNombre()).append("', ");
            str.append("'").append(newUser.getApellido1()).append("', ");
            str.append("'").append(newUser.getApellido2()).append("', ");
            str.append("'").append(simpleDateFormat.format(newUser.getFechaNacimiento())).append("', ");
            str.append(newUser.getCanton().getId()).append(", ");
            str.append("'").append(newUser.getOtrasDirecciones()).append("',");
            str.append("'").append(newUser.getCorreo()).append("' ,");
            str.append("'").append(newUser.getSede().getCodigo()).append("', ");
            str.append(newUser.getCodSeguridad()).append(", ");
            str.append("'").append(newUser.getClave()).append("', ");
            str.append(newUser.getProvincia().getId()).append(", ");
            str.append(newUser.getDistrito().getId()).append(", ");
            str.append(newUser.getBarrio().getId()).append(", ");
            str.append(0).append(" )");
            /*str.append(0).append("");
            str.append("'").append(simpleDateFormat.format(newUser.getFechaNacimiento())).append("')");*/

            sqlCommand = str.toString();
            System.out.println(sqlCommand);
            //Se ejecuta la sentencia SQL
            dataAccess.executeSQLCommand(sqlCommand);

            //Se guardan los distintos perfiles para el usuario
            for (UsuarioPerfil profile : newUser.getPerfiles()) {
                //Se revisa el tipo de perfil, se construye el respectivo tipo y se guarda
                switch (profile.getTipoPerfil()) {
                    case Administrativo:
                        AdministrativoDB adminDB = new AdministrativoDB();
                        adminDB.addNewAdmin(newUser.getIdentificacion());
                        activateAccount(newUser);
                        System.out.println("Si llego");
                        break;
                    case Funcionario:
                        FuncionarioDB funcDB = new FuncionarioDB();
                        funcDB.addNewFunc(newUser.getIdentificacion());
                        break;
                    case Tecnico:
                        TecnicoDB tecDB = new TecnicoDB();
                        tecDB.addNewTec(newUser.getIdentificacion());
                        break;
                    default:
                        throw new Exception("El usuario " + newUser.getIdentificacion() + " no eligio un perfil");
                }
                usuarioPerfilDB.saveUserProfile(profile);
            }
            //Se guardan los telefonos que el usuario registro 
            for (Telefono phone : newUser.getTelefonos()) {
                telefonoDB.savePhone(phone);
            }

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    public void addNewUserFUNC(Usuario newUser) throws SNMPExceptions, SQLException {
        String sqlCommand = "";
        try {
            //Se arma la sentencia del Insert
            StringBuilder str = new StringBuilder();
            /*str.append("Insert Into Usuario "
                    + "(ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, Otras_Direcciones,"
                    + "+ Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, Barrio, Logins, Aprobacion)"+                  
                    "Values (");*/
            str.append("Insert Into Usuario (ID, TipoID, Nombre, Apellido1, Apellido2, Fecha_De_Nacimiento, Canton, "
                    + "Otras_Direcciones, Correo_Electronico, Sede, Codigo_Seguridad, Clave, Provincia, Distrito, "
                    + "Barrio, Logins) Values (");
            str.append("'").append(newUser.getIdentificacion()).append("', ");
            str.append(newUser.getTipoID().ordinal()).append(", ");
            str.append("'").append(newUser.getNombre()).append("', ");
            str.append("'").append(newUser.getApellido1()).append("', ");
            str.append("'").append(newUser.getApellido2()).append("', ");
            str.append("'").append(simpleDateFormat.format(newUser.getFechaNacimiento())).append("', ");
            str.append(3).append(", ");
            str.append("'").append(newUser.getOtrasDirecciones()).append("',");
            str.append("'").append(newUser.getCorreo()).append("' ,");
            str.append("'").append(newUser.getSede().getCodigo()).append("', ");
            str.append(newUser.getCodSeguridad()).append(", ");
            str.append("'").append(newUser.getClave()).append("', ");
            str.append(2).append(", ");
            str.append(3).append(", ");
            str.append(203).append(", ");
            str.append(0).append(" )");
            /*str.append(0).append("");
            str.append("'").append(simpleDateFormat.format(newUser.getFechaNacimiento())).append("')");*/

            sqlCommand = str.toString();
            System.out.println(sqlCommand);
            //Se ejecuta la sentencia SQL
            dataAccess.executeSQLCommand(sqlCommand);

            //Se guardan los distintos perfiles para el usuario
            for (UsuarioPerfil profile : newUser.getPerfiles()) {
                //Se revisa el tipo de perfil, se construye el respectivo tipo y se guarda
                switch (profile.getTipoPerfil()) {
                    case Administrativo:
                        AdministrativoDB adminDB = new AdministrativoDB();
                        adminDB.addNewAdmin(newUser.getIdentificacion());
                        activateAccount(newUser);
                        System.out.println("Si llego");
                        break;
                    case Funcionario:
                        FuncionarioDB funcDB = new FuncionarioDB();
                        funcDB.addNewFunc(newUser.getIdentificacion());
                        break;
                    case Tecnico:
                        TecnicoDB tecDB = new TecnicoDB();
                        tecDB.addNewTec(newUser.getIdentificacion());
                        break;
                    default:
                        throw new Exception("El usuario " + newUser.getIdentificacion() + " no eligio un perfil");
                }
                usuarioPerfilDB.saveUserProfile(profile);
            }
            //Se guardan los telefonos que el usuario registro 
            for (Telefono phone : newUser.getTelefonos()) {
                telefonoDB.savePhone(phone);
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
            sqlSelect = "Select * From Usuario Where ID like '" + id + "'";
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
                LinkedList<UsuarioPerfil> perfiles = usuarioPerfilDB.getAccountsByUser(id);
                LinkedList<Telefono> telefonos = telefonoDB.getUserPhoneNumbers(id);

                //Se asigna la data a las variables
                user = new Usuario();
                user.setIdentificacion(identificacion);
                user.setTipoID(tipoID);
                user.setNombre(nombre);
                user.setApellido1(apellido1);
                user.setApellido2(apellido2);
                user.setFechaNacimiento(fechaNacimiento);
                user.setOtrasDirecciones(otrasDirecciones);
                user.setCorreo(correo);
                user.setSede(sede);
                user.setCodSeguridad(codSeguridad);
                user.setClave(clave);
                user.setLogins(logins);
                user.setProvincia(prov);
                user.setCanton(canton);
                user.setDistrito(distrito);
                user.setBarrio(barrio);
                user.setPerfiles(perfiles);
                user.setTelefonos(telefonos);
                user.setAprobado(aprobado);
                user.setFechaAprobacion(fechaAprobacion);

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
     * Actualiza la informacion del usuario en la DB, realiza el Update
     *
     * @param userToUpdate
     */
    public void updateUser(Usuario userToUpdate) throws SQLException, SNMPExceptions {
        String sqlCommand = "";
        try {
            StringBuilder str = new StringBuilder();
            str.append("Update Usuario Set ");
            str.append("TipoID = ").append(userToUpdate.getTipoID().ordinal()).append(", ");
            str.append("Nombre = '").append(userToUpdate.getNombre()).append("', ");
            str.append("Apellido1 = '").append(userToUpdate.getApellido1()).append("', ");
            str.append("Apellido2 = '").append(userToUpdate.getApellido2()).append("', ");
            str.append("Fecha_De_Nacimiento = '").append(simpleDateFormat.format(userToUpdate.getFechaNacimiento())).append("',");
            str.append("Canton = ").append(userToUpdate.getCanton().getId()).append(", ");
            str.append("Otras_Direcciones = '").append(userToUpdate.getOtrasDirecciones()).append("', ");
            str.append("Correo_Electronico = '").append(userToUpdate.getCorreo()).append("', ");
            str.append("Sede = '").append(userToUpdate.getSede().getCodigo()).append("', ");
            str.append("Codigo_Seguridad = ").append(userToUpdate.getCodSeguridad()).append(", ");
            str.append("Clave = '").append(userToUpdate.getClave()).append("', ");
            str.append("Provincia = ").append(userToUpdate.getProvincia().getId()).append(", ");
            str.append("Distrito = ").append(userToUpdate.getDistrito().getId()).append(", ");
            str.append("Barrio = ").append(userToUpdate.getBarrio().getId()).append(", ");
            str.append("Logins = ").append(userToUpdate.getLogins()).append(", ");
            if(userToUpdate.isAprobado()){
                str.append("Aprobacion = ").append(1).append(", ");
            } else {
                str.append("Aprobacion = ").append(0).append(", ");
            }
            str.append("Fecha_Aprobacion = '").append(simpleDateFormat.format(userToUpdate.getFechaAprobacion())).append("' ");
            str.append("Where ID Like '").append(userToUpdate.getIdentificacion()).append("'");

            sqlCommand = str.toString();
            //Se ejecuta la instruccion SQL
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
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
     *
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
     * @param user
     * @param passwordInserted
     * @return
     * @throws Exception
     */
    public boolean isLoginPasswordCorrect(Usuario user, String passwordInserted) throws Exception {
        if (user != null) {
            //Se obtiene el Hash de la clave ingresada en el inicio de sesion
            passwordInserted = Utils.getHashedPaswd(passwordInserted);
            //Se tiene que extraer el Hash de los bytes de la clave guardada en la DB
            String passwordInDB = user.getClave();
            return passwordInserted.equals(passwordInDB);
        } else {
            throw new ACTIException("Usuario no ha sido seleccionado");
        }
    }

    /**
     * Verifica que el usuario tenga el tipo de perfil con el que esta deseando
     * ingresar y lo retorna si lo encuentra, de lo contrario retorna null
     * @param user
     * @param selectedLoginProfile
     * @return UsuarioPerfil
     */
    public UsuarioPerfil getUserProfile(Usuario user, Perfil selectedLoginProfile) {
        for (UsuarioPerfil registeredProfile : user.getPerfiles()) {
            if (registeredProfile.getTipoPerfil() == selectedLoginProfile) {
                return registeredProfile;
            }
        }
        return null;
    }

    /**
     * Lleva el registro de cada login que hace el usuario, cada vez que inicia
     * sesion se tiene que aumentar el valor en 1 y tambien actualizar la info
     * en la DB
     * @param user
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public void addNewLogin(Usuario user) throws SQLException, SNMPExceptions {
        user.setLogins(user.getLogins() + 1);
        //Se llama al update
        updateUser(user);
    }
    
    /**
     * Retorna true si es el primer login del usuario
     * @param user
     * @return boolean
     */
    public boolean isFirstLogin(Usuario user){
        return user.getLogins() == 0;
    }

    /**
     * Verifica que cuando el usuario cambia su clave, esta cumpla con los
     * requerimientos que el sistema pide
     *
     * @param password
     * @return String
     */
    public String passwordPassRequirements(String password) {
        //La clave debe tener minimo 8 caracteres
        if (password.length() < 8) {
            return "La contrase??a debe tener minimo 8 caracteres";
        }

        //La clave debe tener un maximo de 12 caracteres
        if (password.length() > 12) {
            return "La contrase??a debe tener maximo 12 caracteres";
        }

        int letters = 0;
        int nums = 0;
        int mayusculas = 0;
        int minusculas = 0;

        for (Character caracter : password.toCharArray()) {
            //Si es un digito
            if (Character.isDigit(caracter)) {
                nums += 1;
            } else {
                //Si es una letra
                if (Character.isLetter(caracter)) {
                    letters += 1;
                    if (Character.isUpperCase(caracter)) {
                        mayusculas += 1;
                    } else {
                        minusculas += 1;
                    }
                } else {
                    //Es un caracter especial y estos no se permiten
                    return "La contrase??a no debe tener caracteres especiales";
                }
            }

        }

        //la clave debe tener letras
        if (letters == 0) {
            return "La contrase??a debe tener letras";
        }
        //La clave debe tener numeros
        if (nums == 0) {
            return "La contrase??a debe incluir numeros";
        }
        //La clave debe incluir letras mayusculas y minusculas
        if (mayusculas == 0) {
            return "La contrase??a debe incluir letras may??sculas";
        }
        if (minusculas == 0) {
            return "La contrase??a debe incluir letras min??sculas";
        }

        return "Passed"; //Este es el mensaje que retorna si la clave cumple con todos los requerimientos
    }

    /**
     * Retorna True si el perfil esta activo
     *
     * @param user
     * @return boolean
     */
    public boolean isUserActive(Usuario user) {
        return user.isAprobado();
    }

    /**
     * Este metodo se encarga de activar la cuenta de un usuario y llamar al
     * metodo que hace el update en la DB, solo el usuario Administrador puede
     * hacer esto
     *
     * @param userToActivate
     * @throws SQLException
     * @throws SNMPExceptions
     * @throws ParseException
     */
    
    
    
    //Este se usa cuando se hace click en el boton de activar Funcionarios SOLICITUD DE FUNCIONARIOS NUEVOS
    
    public void activateAccount(Usuario userToActivate) throws SQLException, SNMPExceptions, ParseException {
        userToActivate.setAprobado(true);
        userToActivate.setFechaAprobacion(Utils.getCurrentDate());
        //Llamar al Update
        updateUser(userToActivate);
    }

    /**
     * Este metodo se encarga de desactivar la cuenta de un usuario y llamar al
     * metodo que hace el update en la DB, solo el usuario Administrador puede
     * hacer esto
     *
     * @param user
     * @throws SQLException
     * @throws SNMPExceptions
     * @throws ParseException
     */
    
    
    
    public void deactivateAccount(Usuario user) throws SQLException, SNMPExceptions, ParseException {
        user.setAprobado(false);
        //Llamar al Update
        updateUser(user);
    }

    /**
     * Calcula la edad del usuario en base a su fecha de nacimiento
     *
     * @param fechaNacimiento
     * @return int
     * @throws ParseException
     * @throws NullPointerException
     */
    
    
    //EXTRA  
    public int calculateAge(Date fechaNacimiento) throws ParseException, NullPointerException{
        try {
            //Las fechas se tienen que convertir a LocalDate
            LocalDate currentDate = Utils.getCurrentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate birthdate = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            return Period.between(birthdate, currentDate).getYears();
            
        } catch (NullPointerException err){
            throw new NullPointerException("La fecha de nacimiento no se digit??");
        }
    }

    /**
     * Obtiene una lista con todos los usuarios que no estan aprobados para
     * iniciar sesion
     *
     * @return ArrayList
     * @throws SQLException
     * @throws SNMPExceptions
     */
    
    
    //LLena solicitud de funcionarios NUEVOS
    public ArrayList<Usuario> getDisabledUsersFromDB() throws SQLException, SNMPExceptions {
        ArrayList<Usuario> disabledUsers = new ArrayList<>();
        String sqlSelect = "";
        try {
            
            sqlSelect = "Select ID From Usuario Where Aprobacion is null or Aprobacion = 0";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                Usuario user = getUserFromDB(rs.getString("ID"));
                disabledUsers.add(user);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return disabledUsers;
    }
    
    /**
     * Retorna el nombre del tipo de perfil con el que el usuario ingreso
     * @return String
     */
    public String getLogedInProfile(){
        String perfil = this.getCurrentSession().get("Perfil").toString();
        return perfil;
    }
    
    /**
     * Retorna el usuario que esta activo en el sistema
     * @return Usuario
     */
    public Usuario getLogedInUser(){
        Usuario currentUser = (Usuario)this.getCurrentSession().get("UsuarioActual");
        return currentUser;
    }
    
    /**
     * Retorna la informacion de la sesion actual
     * @return Map<String, Object>
     */
    public Map<String, Object> getCurrentSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> session = context.getSessionMap();
        return session;
    }
    
    /**
     * Guarda la informaci??n del usuario que inici?? sesion en el sistema para poder
     * accederse desde cualquier parte del programa
     * @param user
     * @param perfilSeleccionado 
     * @throws NullPointerException 
     */
    public void setLogedInUser(Usuario user, Perfil perfilSeleccionado) throws NullPointerException{
        if(user != null){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioActual", user);
            //Tambien se tiene que guardar el tipo de usuario que es
            switch(perfilSeleccionado){
                case Administrativo:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Perfil", "Administrativo");
                    break;
                case Funcionario:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Perfil", "Funcionario");
                    break;
                case Tecnico:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Perfil", "Tecnico");
            }
        }
        else {
            throw new NullPointerException("El usuario viene sin datos");
        }
    }
}
