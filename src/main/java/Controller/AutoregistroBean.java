/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SNMPExceptions;
import Enum.Perfil;
import Enum.TipoIdentificacion;
import Enum.TipoTelefono;
import Model.Barrio;
import Model.BarrioDB;
import Model.Canton;
import Model.CantonDB;
import Model.Distrito;
import Model.DistritoDB;
import Model.PerfilDB;
import Model.Provincia;
import Model.ProvinciaDB;
import Model.Sede;
import Model.SedeDB;
import Model.Telefono;
import Model.TipoIdentificacionDB;
import Model.TipoTelefonoDB;
import Model.Usuario;
import Model.UsuarioDB;
import Model.UsuarioPerfil;
import Utils.Utils;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author danielp
 */
public class AutoregistroBean {

    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private TipoIdentificacion tipoIdSeleccionado;
    private TipoTelefono tipoTelefonoSeleccionado;
    private Perfil perfilSeleccionado;
    private String identificacion;
    private String nombre, apellido1, apellido2;
    private String correo;
    private String numeroTelefono;

    //A este direccion completa me falta hacerle un metodo que lo llene
    private String direccionCompleta;
    private int edad;
    private String otrasDirecciones;
    //Estas dos listas solo tienen perfiles y telefonos que posee el usuario
    private LinkedList<Telefono> telefonos;
    private LinkedList<UsuarioPerfil> perfiles;
    //private LinkedList<Telefono> telefonosIncluidos = new LinkedList<>();
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion
    private ArrayList<Provincia> provincias;
    private ArrayList<Canton> cantones;
    private ArrayList<Distrito> distritos;
    private ArrayList<Barrio> barrios;
    private ArrayList<Sede> sedes;

    //Estos son atributos seleccionados por el usuario de los combos, algo parecido al SelectedItem
    private int provID;
    private int canID;
    private int disID;
    private int barID;
    private Provincia provinciaSeleccionada;
    private Canton cantonSeleccionado;
    private Distrito distritoSeleccionado;
    private Barrio barrioSeleccionado;
    private Sede sede;
    private String sedeID;

    private String validationMessage = "Hola Mundo";
    //Mensaje para desplegar info de validaciones
    private String messageDisplayed;
    private String phoneMessage;
    private String profileMessage;

    //Logica necesaria para las fechas
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fechaNacimiento = myCalendar.getTime();
    //Llamado a clases DB
    UsuarioDB userDB = new UsuarioDB();

    public AutoregistroBean() {
        this.telefonos = new LinkedList<>();
        this.perfiles = new LinkedList<>();
        this.sedes = new ArrayList<>();
        this.provincias = new ArrayList<>();
        this.cantones = new ArrayList<>();
        this.distritos = new ArrayList<>();
        this.barrios = new ArrayList<>();
        this.fillSedes();
        /*this.identificacion = "";
        this.nombre = "";
        this.apellido1 = "";
        this.apellido2 = "";
        this.correo = "";
        this.otrasDirecciones = "";
        this.validationMessage = "";*/
    }

    
    public void cleanData() {
        //this.identificacion = this.nombre = this.apellido1 = this.apellido2 = this.otrasDirecciones = this.correo = this.validationMessage = "";
        //this.fechaNacimiento = null;
        //this.edad = 0;
        /*this.provinciaSeleccionada = null;
        this.cantonSeleccionado = null;
        this.distritoSeleccionado = null;
        this.barrioSeleccionado = null;*/
        //this.telefonos.clear();
        //this.perfiles.clear();
    }

    public void saveNewUser() {
        this.messageDisplayed = "";

        //Primero validar que todos los datos se hayan ingresado
        if (this.identificacion.trim().length() == 0) {
            this.messageDisplayed = "Ingrese su Número de Identificación";
            return;
        }
        if (this.nombre.trim().length() == 0) {
            this.messageDisplayed = "Ingrese su Nombre";
            return;
        }
        if (this.apellido1.trim().length() == 0) {
            this.messageDisplayed = "Ingrese su Primer Apellido";
            return;
        }
        if (this.apellido2.trim().length() == 0) {
            this.messageDisplayed = "Ingrese su Segundo Apellido";
            return;
        }
        if (this.fechaNacimiento == null) {
            this.messageDisplayed = "Indique su Fecha de Nacimiento";
            return;
        }
        if (this.correo.trim().length() == 0) {
            this.messageDisplayed = "Ingrese su Correo Electrónico";
            return;
        }
        if (this.telefonos.isEmpty()) {
            this.messageDisplayed = "Debe registrar al menos un teléfono";
            return;
        }
        if (this.perfiles.isEmpty()) {
            this.messageDisplayed = "Debe usar al menos un tipo de perfil";
            return;
        }
        if (this.provinciaSeleccionada == null) {
            this.messageDisplayed = "Debe seleccionar una provincia";
            return;
        }
        if (this.cantonSeleccionado == null) {
            this.messageDisplayed = "Debe seleccionar un cantón";
            return;
        }
        if (this.distritoSeleccionado == null) {
            this.messageDisplayed = "Debe seleccionar un distrito";
            return;
        }
        if (this.barrioSeleccionado == null) {
            this.messageDisplayed = "Debe seleccionar un barrio";
            return;
        }
        SedeDB sedeDB = new SedeDB();
        try{
            this.sede = sedeDB.getSede(this.sedeID);
        } catch (Exception e){
            
        }
        if(this.sede == null){
            this.messageDisplayed = "Debe seleccionar una sede";
            return;
        }
        
        //Se tiene que crear el nuevo usuario
        Usuario newUser = new Usuario(identificacion, nombre, apellido1, apellido2, fechaNacimiento, provinciaSeleccionada,
                cantonSeleccionado, distritoSeleccionado, barrioSeleccionado, otrasDirecciones, correo, sede,
                perfiles, telefonos);
        newUser.setTipoID(this.tipoIdSeleccionado);
        
        //Una vez creado el usuario se tiene que enviar un correo con un codigo de seguridad y la clave de primer ingreso
        int codSeguridad = userDB.generateSecurityCode();
        newUser.setCodSeguridad(codSeguridad);
        String generatedPass = userDB.generateFirstPasswd();

        try{
            Utils.sendLoginInfoEmail(newUser, generatedPass);
        } catch (Exception e){
            this.messageDisplayed = "Ocurrió un error al enviar el correo, por favor intente de nuevo" + e.getMessage() + " "+  e.toString();
            e.printStackTrace();
            return;
        }
        //Se registran los bytes de la clave
        try {
            generatedPass = Utils.getHashedPaswd(generatedPass);
        } catch (Exception e) {
            this.messageDisplayed = "Error al registrar contraseña";
        }
        newUser.setClave(generatedPass);

        //Se tiene que guardar toda la informacion del Usuario en la base de datos
        try {
            userDB.addNewUser(newUser);
            this.messageDisplayed = "Usuario registrado correctamente";
        } catch (SNMPExceptions e) {
            this.messageDisplayed = "Error al registrar usuario" + e.toString() + e.getMessage();
        } catch (SQLException e) {
            this.messageDisplayed = "Error al registrar usuario" + e.toString() + e.getMessage();
        }

    }

    //Devuelve los perfiles del enum
    public ArrayList<Perfil> getPerfiles() {
        ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
        try {
            PerfilDB perfilDB = new PerfilDB();
            perfiles = perfilDB.getAllPerfiles();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return perfiles;
    }

    //Devuelve los tipos de identificacion del enum
    public ArrayList<TipoIdentificacion> getTipoIdentificacion() {
        ArrayList<TipoIdentificacion> tiposID = new ArrayList<>();
        try {
            TipoIdentificacionDB tipoIdDB = new TipoIdentificacionDB();
            tiposID = tipoIdDB.getIdTypes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return tiposID;
    }

    //Devuelve los tipos de telefonos
    public ArrayList<TipoTelefono> getTiposTelefono() {
        ArrayList<TipoTelefono> phoneTypes = new ArrayList<TipoTelefono>();
        try {
            TipoTelefonoDB phoneTypeDB = new TipoTelefonoDB();
            phoneTypes = phoneTypeDB.getPhoneTypes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return phoneTypes;
    }

    //Agrega un telefono a la lista
    public void addTelefono() {
        this.phoneMessage= "";
        if (this.identificacion.trim().length() == 0) {
            this.phoneMessage = "Ingrese su Número de Identificación";
            return;
        }
        if (this.numeroTelefono.trim().length() == 0) {
            this.phoneMessage = "Digite el número telefónico";
            return;
        }

        //Se agrega a la lista de telefonos
        this.telefonos.add(new Telefono(this.numeroTelefono, this.identificacion, this.tipoTelefonoSeleccionado));
        this.phoneMessage = "Teléfono Agregado";
    }

    //Agrega un perfil a la lista
    public void addPerfil() {
        this.profileMessage = "";
        if (this.identificacion.trim().length() == 0) {
            this.profileMessage = "Ingrese su Número de Identificación";
            return;
        }

        //Se agrega a la lista de perfiles
        try {
            this.perfiles.add(new UsuarioPerfil(this.identificacion, this.perfilSeleccionado, Utils.getCurrentDate()));
            this.profileMessage = "Perfil agregado";
        } catch (Exception e) {
            this.profileMessage = "Ocurrió un error al registrar el perfil" + e.getMessage();
        }
    }

    public ArrayList<Provincia> getProvincias() {
        try {
            return ProvinciaDB.getAllProvincesFromDB();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    public void fillCantones(AjaxBehaviorEvent event) {
        try {
            //Se construye la provincia
            this.provinciaSeleccionada = ProvinciaDB.getProvinceFromDB(this.provID);
            this.cantones = CantonDB.getCantonesByProvince(this.provinciaSeleccionada.getId());
            System.out.println("Cantones cargados");
            
        } catch (Exception e) {
            
        }
    }

    public void fillDistritos(AjaxBehaviorEvent event) {
        try {
            //Se construye el canton
            this.cantonSeleccionado = CantonDB.getCantonFromDB(canID, provID);
            this.distritos = DistritoDB.getDistrictsByCanton(this.cantonSeleccionado.getId(),provID);
            System.out.println("Distritos Agregados");
        } catch (Exception e) {

        }
    }

    public void fillBarrios(AjaxBehaviorEvent event) {
        try {
            //Se construye el distrito
            this.distritoSeleccionado = DistritoDB.getDistrictFromDB(disID, provID, canID);
            this.barrios = BarrioDB.getBarriosByDistrito(this.distritoSeleccionado.getId(), 
                                       provID, canID);
            this.provinciaSeleccionada = ProvinciaDB.getProvinceFromDB(this.provID);
            this.cantonSeleccionado = CantonDB.getCantonFromDB(canID, provID);
            this.barrioSeleccionado = BarrioDB.getBarrioFromDB(this.barID, this.provID, this.canID, this.disID);
            System.out.println("Datos de ubicacion agregados");
        } catch (Exception e) {

        }
    }
    
    public void fillSedes(){
        try {
            SedeDB sedeDB = new SedeDB();
            this.sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
    }
    
    /*public ArrayList<Sede> getSedes() {
        try {
            SedeDB sedeDB = new SedeDB();
            this.sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return this.sedes;
    }*/
    
    public ArrayList<Sede> getSedes(){
        return sedes;
    }

    //<editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">\
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getOtrasDirecciones() {
        return otrasDirecciones;
    }

    public void setOtrasDirecciones(String otrasDirecciones) {
        this.otrasDirecciones = otrasDirecciones;
    }

    public LinkedList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public void setPerfiles(LinkedList<UsuarioPerfil> perfiles) {
        this.perfiles = perfiles;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public TipoIdentificacion getTipoIdSeleccionado() {
        return tipoIdSeleccionado;
    }

    public void setTipoIdSeleccionado(TipoIdentificacion tipoIdSeleccionado) {
        this.tipoIdSeleccionado = tipoIdSeleccionado;
    }

    public String getSedeID() {
        return sedeID;
    }

    public void setSedeID(String sedeID) {
        this.sedeID = sedeID;
    }

    public void setProvincias(ArrayList<Provincia> provincias) {
        this.provincias = provincias;
    }

    public ArrayList<Canton> getCantones() {
        return cantones;
    }

    public void setCantones(ArrayList<Canton> cantones) {
        this.cantones = cantones;
    }

    public ArrayList<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(ArrayList<Distrito> distritos) {
        this.distritos = distritos;
    }

    public ArrayList<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(ArrayList<Barrio> barrios) {
        this.barrios = barrios;
    }

    public Provincia getProvinciaSeleccionada() {
        return provinciaSeleccionada;
    }

    public void setProvinciaSeleccionada(Provincia provincia) {
        this.provinciaSeleccionada = provincia;
    }

    public Canton getCantonSeleccionado() {
        return cantonSeleccionado;
    }

    public void setCantonSeleccionado(Canton canton) {
        this.cantonSeleccionado = canton;
    }

    public Distrito getDistritoSeleccionado() {
        return distritoSeleccionado;
    }

    public void setDistritoSeleccionado(Distrito distritoSeleccionado) {
        this.distritoSeleccionado = distritoSeleccionado;
    }

    public Barrio getBarrioSeleccionado() {
        return barrioSeleccionado;
    }

    public void setBarrioSeleccionado(Barrio barrioSeleccionado) {
        this.barrioSeleccionado = barrioSeleccionado;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public String getMessageDisplayed() {
        return messageDisplayed;
    }

    public void setMessageDisplayed(String messageDisplayed) {
        this.messageDisplayed = messageDisplayed;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public TipoTelefono getTipoTelefonoSeleccionado() {
        return tipoTelefonoSeleccionado;
    }

    public void setTipoTelefonoSeleccionado(TipoTelefono tipoTelefonoSeleccionado) {
        this.tipoTelefonoSeleccionado = tipoTelefonoSeleccionado;
    }

    public int getProvID() {
        return provID;
    }

    public void setProvID(int provID) {
        this.provID = provID;
    }
    
    public int getCanID() {
        return canID;
    }

    public void setCanID(int canID) {
        this.canID = canID;
    }

    public int getDisID() {
        return disID;
    }

    public void setDisID(int disID) {
        this.disID = disID;
    }

    public int getBarID() {
        return barID;
    }

    public void setBarID(int barID) {
        this.barID = barID;
    }
    
    public String getPhoneMessage() {
        return phoneMessage;
    }

    public void setPhoneMessage(String phoneMessage) {
        this.phoneMessage = phoneMessage;
    }
    
    public String getProfileMessage() {
        return profileMessage;
    }

    public void setProfileMessage(String profileMessage) {
        this.profileMessage = profileMessage;
    }
    
    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
// </editor-fold>  

    
}
