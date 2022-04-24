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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class PrestamoDB {
    private static final DataAccess dataAccess = new DataAccess();
    
    private ActivoDB activoDB = new ActivoDB();
    private UsuarioDB userDB = new UsuarioDB();
    private MovimientoActivoDB movActDB = new MovimientoActivoDB();
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    /**
     * Guarda la informacion de un prestamo de activo en la DB
     * @param prestamo
     * @throws SNMPExceptions
     * @throws SQLException 
     */
    public void savePrestamo(Prestamo prestamo) throws SNMPExceptions, SQLException{
        String sqlCommand = "";
        try{
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Prestamos Values (");
            str.append("'").append(prestamo.getActivo().getIdActivo()).append("', ");
            str.append("'").append(prestamo.getFuncionarioSolicitante().getIdentificacion()).append("', ");

            //AQUI ES DONDE SE CAE AL TRATAR DE GUARDAR PRESTAMO
            str.append("'").append(simpleDateFormat.format(prestamo.getFecha_Solicitud())).append("', ");
            str.append("'").append(simpleDateFormat.format(prestamo.getFechaRetorno())).append("' )");
            
            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
    
    /**
     * Obtiene los datos de un prestamo de la DB
     * @param idActivo
     * @param idFunc
     * @param fechaSolicitud
     * @return Prestamo
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public Prestamo getPrestamoFromDB(String idActivo, String idFunc, Date fechaSolicitud) throws SQLException, SNMPExceptions{
        Prestamo prestamo = null;
        String sqlSelect = "";
        try{
            sqlSelect = "Select IDActivo, IDSolicitante, Fecha_Solicitud, Fecha_Retorno From Prestamos"
                    + " Where IDActivo Like " + idActivo + " and IDSolicitante Like " + idFunc + " and "
                    + "Fecha_Solicitud = '" + simpleDateFormat.format(fechaSolicitud) + "'";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                //Se traen los datos de la tabla Movimiento_Activo correspondientes al prestamo
                MovimientoActivo movAct = movActDB.getMovActFromDB(idActivo, idFunc, fechaSolicitud);
                prestamo = new Prestamo();
                prestamo.setActivo(movAct.getActivo());
                prestamo.setAprobado(movAct.isAprobado());
                prestamo.setFecha_Solicitud(movAct.getFecha_Solicitud());
                prestamo.setFuncionarioSolicitante(movAct.getFuncionarioSolicitante());
                prestamo.setMotivo(movAct.getMotivo());
                prestamo.setTecnicoAprobante(movAct.getTecnicoAprobante()); 
                prestamo.setFechaRetorno(rs.getDate("Fecha_Retorno"));
                
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return prestamo;
    }
    
    /**
     * Retorna una lista con todos los prestamos registrados
     * @return
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Prestamo> getAllPrestamos() throws SQLException, SNMPExceptions{
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String sqlSelect = "";
        try{
            sqlSelect = "Select * From Prestamos";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                Activo activo = activoDB.getActivoFromDB(rs.getString("IDActivo"));
                Usuario funcSolicitante = userDB.getUserFromDB(rs.getString("IDSolicitante"));
                Date requestDate = rs.getDate("Fecha_Solicitud");
                
                //Info de Movimiento Activo
                MovimientoActivo movAct = movActDB.getMovActFromDB(activo.getIdActivo(), 
                                        funcSolicitante.getIdentificacion(), requestDate);
                Prestamo prestamo = new Prestamo();
                prestamo.setActivo(movAct.getActivo());
                prestamo.setAprobado(movAct.isAprobado());
                prestamo.setFecha_Solicitud(movAct.getFecha_Solicitud());
                prestamo.setFuncionarioSolicitante(movAct.getFuncionarioSolicitante());
                prestamo.setMotivo(movAct.getMotivo());
                prestamo.setTecnicoAprobante(movAct.getTecnicoAprobante());
                prestamo.setFechaRetorno(rs.getDate("Fecha_Retorno"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return prestamos;
    }
    
    /**
     * Retorna una lista con todos los prestamos que no han sido aprobados
     * @return ArrayList<Prestamo>
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Prestamo> getPrestamosNoAprobados() throws SQLException, SNMPExceptions{
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        //Se tienen que obtener todos los movimientos de activo que no han sido aprobados
        ArrayList<MovimientoActivo> movsNoAprobados = movActDB.getMovActsNoAprobados();
        
        //Se tiene que verificar cuales son prestamos para agregarlos a la lista
        for(MovimientoActivo movAct : movsNoAprobados){
            String idActivo = movAct.getActivo().getIdActivo();
            String idFunc = movAct.getFuncionarioSolicitante().getIdentificacion();
            Date fechaSolicitud = movAct.getFecha_Solicitud();
            Prestamo prestamo = this.getPrestamoFromDB(idActivo, idFunc, fechaSolicitud);
            
            //Si es un prestamo se agrega a la lista
            if(prestamo != null){
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }
}
