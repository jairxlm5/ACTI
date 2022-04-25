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
import Model.Activo;
import Model.Barrio;
import Model.BarrioDB;
import Model.Canton;
import Model.CantonDB;
import Model.Distrito;
import Model.DistritoDB;
import Model.Funcionario;
import Model.FuncionarioDB;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import Utils.Utils;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

/**
 *
 * @author danielp
 */
public class MantenimientoFuncionariosBean {

    //Este objeto Usuario solo se usa cuando se traen datos de consulta de la base de datos de un Usuario que ya existe
    private Usuario user;
    //Estos son los atributos para relacionarlos con campos de texto en el bean
    private String identificacion;
    private String nombre, apellido1, apellido2;
    private String otrasDirecciones = "";
    private String correo;
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

    private String direccionCompleta;
    private String numeroTelefono;
    private TipoTelefono tipoTelefonoSeleccionado;
    private ArrayList<TipoTelefono> tipoTelefono = new ArrayList<TipoTelefono>();
    private Perfil perfilSeleccionado;

    private String messageDisplayed;
    private String phoneMessage;
    private String profileMessage;
    FuncionarioDB funcionarioDB = new FuncionarioDB();

    //Estas dos listas solo tienen perfiles y telefonos que posee el usuario
    private LinkedList<UsuarioPerfil> perfiles;
    private LinkedList<Telefono> telefonos;
    //Estos ArrayLists son para llenar los datos que aparecen en el combo con la info de la BD
    //Solo estan para desplegar informacion

    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Funcionario> funcionariosFiltrados;
    Usuario newUser;

    //Bro necesitaba la tabla funcionarios para poder cargar la data
    private ArrayList<Provincia> provincias;
    private ArrayList<Canton> cantones;
    private ArrayList<Distrito> distritos;
    private ArrayList<Barrio> barrios;
    private ArrayList<Sede> sedes;

    //Mensaje para desplegar info de validaciones
    private String validationMessage;

    //Logica necesaria para las fechas
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Calendar myCalendar = new GregorianCalendar();
    private Date fechaNacimiento = myCalendar.getTime();
    //Llamado clases del Model
    UsuarioDB userDB = new UsuarioDB();
    Funcionario funcionarioSelecionado = new Funcionario();
    //Este funcionario es el que voy a agarrar para aplicar los botones de la db 
    Funcionario funcionarioParaMantenimiento = new Funcionario();
    private List<FilterMeta> filterBy;
    
    
        //Estos son los atributos para relacionarlos con campos de texto en el bean
    private TipoIdentificacion tipoIdSeleccionado;

 
    public MantenimientoFuncionariosBean() {
        this.telefonos = new LinkedList<>();
        this.perfiles = new LinkedList<>();
        this.sedes = new ArrayList<>();
        this.provincias = new ArrayList<>();
        this.cantones = new ArrayList<>();
        this.distritos = new ArrayList<>();
        this.barrios = new ArrayList<>();
        this.fillSedes();
        
        filterBy = new ArrayList<>();
        
       
    }

    
    public void agregaPerfil(){
        try{
               if (this.identificacion.trim().length() == 0) {
            this.profileMessage = "Ingrese su Número de Identificación";
            return;
        }
        this.perfiles.add(new UsuarioPerfil(this.identificacion, Perfil.Funcionario, Utils.getCurrentDate()));
        
        }catch (Exception e){
            
        }
      
    }
    
    
    
    public void GuardarFuncionario() {
  
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
   
        SedeDB sedeDB = new SedeDB();
        try{
            this.sede = sedeDB.getSede(this.sedeID);
        } catch (Exception e){
            
        }
        if(this.sede == null){
            this.messageDisplayed = "Debe seleccionar una sede";
            return;
        }
        
        //Se tiene que crear el nuevo usuario\
        
        agregaPerfil();
        try{
             this.newUser = userDB.getUserFromDB(identificacion);
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
        
        

        
              
        } catch (Exception e){
            
        }
     


      
    
        //Se tiene que guardar toda la informacion del Usuario en la base de datos
        try {
            userDB.addNewUser(newUser);
            this.messageDisplayed = "Funcionario registrado correctamente";
        } catch (SNMPExceptions e) {
            this.messageDisplayed = "Error al registrar Funcionario" + e.toString() + e.getMessage();
        } catch (SQLException e) {
            this.messageDisplayed = "Error al registrar Funcionario" + e.toString() + e.getMessage();
        }

    }

    


    public void editFuncionario() {
        //Se tienen que aplicar casi el mismo proceso a cuando se registra un usuario nuevo
        this.validationMessage = "";
        //Primero validar que todos los datos se hayan ingresado
        if (this.identificacion.trim().length() == 0) {
            this.validationMessage = "Ingrese su Nombre";
            return;
        }
        if (this.apellido1.trim().length() == 0) {
            this.validationMessage = "Ingrese su Primer Apellido";
            return;
        }
        if (this.apellido2.trim().length() == 0) {
            this.validationMessage = "Ingrese su Segundo Apellido";
            return;
        }
        if (this.fechaNacimiento == null) {
            this.validationMessage = "Indique su Fecha de Nacimiento";
            return;
        }
        if (this.correo.trim().length() == 0) {
            this.validationMessage = "Ingrese su Correo Electrónico";
            return;
        }
        if (this.telefonos.isEmpty()) {
            this.validationMessage = "Debe registrar al menos un teléfono";
            return;
        }
        if (this.perfiles.isEmpty()) {
            this.validationMessage = "Debe usar al menos un tipo de perfil";
            return;
        }
        //Se tiene que crear un objeto usuario con la informacion editada
        agregaPerfil();
        
        Usuario newUser = new Usuario(identificacion, nombre, apellido1, apellido2, fechaNacimiento,null,
                null,null, null, otrasDirecciones, correo, sede,
                perfiles, telefonos);
        newUser.setTipoID(this.tipoIdSeleccionado);
        
        //Se actualiza la informacion en la BD
            try {
            userDB.updateUser(user);
            this.messageDisplayed = "Funcionario Editado correctamente";
        } catch (SNMPExceptions e) {
            this.messageDisplayed = "Error al registrar Funcionario" + e.toString() + e.getMessage();
        } catch (SQLException e) {
            this.messageDisplayed = "Error al registrar Funcionario" + e.toString() + e.getMessage();
        }
        
        
    }


    public void cleanData() {
        //lo deje asi para arreglar una vara
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
            this.distritos = DistritoDB.getDistrictsByCanton(this.cantonSeleccionado.getId(), provID);
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

    public void fillSedes() {
        try {
            SedeDB sedeDB = new SedeDB();
            this.sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
    }

    public ArrayList<Funcionario> getFuncionariosDB() {
        try {
            FuncionarioDB funcDB = new FuncionarioDB();
            this.funcionarios = funcDB.getAllFuncionarios();

        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return funcionarios;
    }

    public ArrayList<TipoTelefono> getTipoTelefono() {
        ArrayList<TipoTelefono> phoneTypes = new ArrayList<TipoTelefono>();
        try {
            TipoTelefonoDB phoneTypeDB = new TipoTelefonoDB();
            phoneTypes = phoneTypeDB.getPhoneTypes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        tipoTelefono = phoneTypes;
        return tipoTelefono;
    }

    public ArrayList<Sede> getSedesDB() {
        ArrayList<Sede> sedes = new ArrayList<>();
        try {
            SedeDB sedeDB = new SedeDB();
            sedes = sedeDB.getAllSedes();
        } catch (SQLException e) {

        } catch (SNMPExceptions s) {

        }
        return sedes;

    }

    public void onRowSelect(SelectEvent<Funcionario> event) {
        FacesMessage msg = new FacesMessage("Product Selected", String.valueOf(event.getObject().getNombre()));

        this.identificacion = event.getObject().getIdentificacion();
        this.nombre = event.getObject().getNombre();
        this.apellido1 = event.getObject().getApellido1();
        this.apellido2 = event.getObject().getApellido2();
        this.fechaNacimiento = event.getObject().getFechaNacimiento();
        this.correo = event.getObject().getCorreo();
        this.telefonos = event.getObject().getTelefonos();
        this.perfiles = event.getObject().getPerfiles();
        this.direccionCompleta = event.getObject().getOtrasDirecciones();
        this.sedeID = event.getObject().getSede().getCodigo();
        
        
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<Funcionario> event) {
        FacesMessage msg = new FacesMessage("Product Unselected", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
      public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showSticky() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
    }


    
    
    

// <editor-fold defaultstate="collapsed" desc="METODOS GET Y SET">
    
    
        public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterMeta> filterBy) {
        this.filterBy = filterBy;
    }
    
    
    public void setTipoTelefono(ArrayList<TipoTelefono> tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getMessageDisplayed() {
        return messageDisplayed;
    }

    public void setMessageDisplayed(String messageDisplayed) {
        this.messageDisplayed = messageDisplayed;
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

    public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

    public Funcionario getFuncionarioParaMantenimiento() {
        return funcionarioParaMantenimiento;
    }

    public void setFuncionarioParaMantenimiento(Funcionario funcionarioParaMantenimiento) {
        this.funcionarioParaMantenimiento = funcionarioParaMantenimiento;
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

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
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

    public String getSedeID() {
        return sedeID;
    }

    public void setSedeID(String sedeID) {
        this.sedeID = sedeID;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(Calendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public UsuarioDB getUserDB() {
        return userDB;
    }

    public void setUserDB(UsuarioDB userDB) {
        this.userDB = userDB;
    }

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

    public Provincia getProvinciaSeleccionada() {
        return provinciaSeleccionada;
    }

    public void setProvinciaSeleccionada(Provincia provincia) {
        this.provinciaSeleccionada = provincia;
    }

    public Canton getCantonSeleccionado() {
        return cantonSeleccionado;
    }

    public Usuario getUser() {
        return null;
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

    public String getOtrasDirecciones() {
        return otrasDirecciones;
    }

    public void setOtrasDirecciones(String otrasDirecciones) {
        this.otrasDirecciones = otrasDirecciones;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public void setPerfiles(LinkedList<UsuarioPerfil> perfiles) {
        this.perfiles = perfiles;
    }

    public LinkedList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(LinkedList<Telefono> telefonos) {
        this.telefonos = telefonos;
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

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public ArrayList<Funcionario> getFuncionariosFiltrados() {
        return funcionariosFiltrados;
    }

    public void setFuncionariosFiltrados(ArrayList<Funcionario> funcionariosFiltrados) {
        this.funcionariosFiltrados = funcionariosFiltrados;
    }

// </editor-fold>
}
