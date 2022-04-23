/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Utils.ACTIException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class MovimientoActivoDB {

    private static final DataAccess dataAccess = new DataAccess();

    private ActivoDB activoDB = new ActivoDB();
    private UsuarioDB userDB = new UsuarioDB();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * @param movAct
     * @throws SQLException
     * @throws SNMPExceptions
     * @throws NullPointerException
     */
    public void saveMovimientoAct(MovimientoActivo movAct) throws SQLException, SNMPExceptions, NullPointerException {
        String sqlCommand = "";
        PrestamoDB prestamoDB = new PrestamoDB();
        TrasladoDB trasladoDB = new TrasladoDB();
        try {
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Movimiento_Activo (IDActivo, IDSolicitante, Fecha_Solicitud, Motivo, Aprobacion) Values (");
            str.append("'").append(movAct.getActivo().getIdActivo()).append("', ");
            str.append("'").append(movAct.getFuncionarioSolicitante().getIdentificacion()).append("', ");
            str.append("'").append(simpleDateFormat.format(movAct.getFecha_Solicitud())).append("', ");
            str.append("'").append(movAct.getMotivo()).append("', ");
            if(movAct.isAprobado()){
                str.append(1).append(" )");
            } else {
                str.append(0).append(" )");
            }
            

            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);

            //Dependiento del tipo de movimiento se guarda en su respectiva tabla
            if (movAct instanceof Prestamo) {
                prestamoDB.savePrestamo((Prestamo) movAct);
            } else {
                if (movAct instanceof Traslado) {
                    trasladoDB.saveTraslado((Traslado) movAct);
                } else {
                    //Si llega aca es porque no se especifico si era Prestamo o Traslado
                    //En ese caso se tendria que eliminar el MovimientoActivo que se acaba de registrar
                    deleteMovimientoAct(movAct);
                    throw new ACTIException("Se tiene que especificar si la solicitud es de Prestamo o Traslado");
                }
            }

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    /**
     * Elimina los datos de un Movimiento de Activo
     *
     * @param movAct
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public void deleteMovimientoAct(MovimientoActivo movAct) throws SQLException, SNMPExceptions {
        String sqlCommand = "";
        try {
            sqlCommand = "Delete From Movimiento_Activo Where IDActivo Like '" + movAct.getActivo().getIdActivo() + "' and"
                    + " IDSolicitante Like '" + movAct.getFuncionarioSolicitante().getIdentificacion() + "' and"
                    + " Fecha_Solicitud = '" + simpleDateFormat.format(movAct.getFecha_Solicitud()) + "'";
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    /**
     * Actualiza la informacion de un movimiento de activo en la DB
     *
     * @param movAct
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public void updateMovimientoAct(MovimientoActivo movAct) throws SQLException, SNMPExceptions {
        String sqlCommand = "";
        try {
            StringBuilder str = new StringBuilder();
            str.append("Update Movimiento_Activo Set ");
            if(movAct.isAprobado()){
                str.append("Aprobacion = ").append(1).append(", ");
            } else {
                str.append("Aprobacion = ").append(0).append(", ");
            }
            str.append("IDTecnicoAprobante = '").append(movAct.getTecnicoAprobante().getIdentificacion()).append("', ");
            str.append("Where IDActivo Like '").append(movAct.getActivo().getIdActivo()).append("' ");
            str.append(" and IDSolicitante Like '").append(movAct.getFuncionarioSolicitante().getIdentificacion()).append("' ");
            str.append(" and Fecha_Solicitud = '").append(simpleDateFormat.format(movAct.getFecha_Solicitud())).append("'");

            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public MovimientoActivo getMovActFromDB(String idActivo, String idFunc, Date fechaSolicitud) throws SQLException, SNMPExceptions{
        MovimientoActivo movAct = null;
        TecnicoDB tecDB = new TecnicoDB();
        FuncionarioDB funcDB = new FuncionarioDB();
        String sqlSelect = "";
        try {
            sqlSelect = "Select IDActivo, IDSolicitante, Fecha_Solicitud, Motivo, Aprobacion, IDTecnicoAprobante "
                    + "From Movimiento_Activo Where IDActivo Like '" + idActivo + "' and IDSolicitante Like '" + idFunc
                    + "' and " + "Fecha_Solicitud = '" + simpleDateFormat.format(fechaSolicitud) + "'";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if(rs.next()){
                Activo activo = activoDB.getActivoFromDB(rs.getString("IDActivo"));
                Usuario funcSolicitante = funcDB.getFuncFromDB(rs.getString("IDSolicitante")); 
                Date requestDate = rs.getDate("Fecha_Solicitud");
                String motivo = rs.getString("Motivo");
                boolean aprobado = rs.getBoolean("Aprobacion");
                
                movAct = new MovimientoActivo(activo, fechaSolicitud, motivo, aprobado);
                movAct.setFuncionarioSolicitante((Funcionario)funcSolicitante);
                movAct.setTecnicoAprobante(tecDB.getTecFromDB(rs.getString("IDTecnicoAprobante"))); 
                
                /*if(rs.getString("IDTecnicoAprobante") != null){
                    movAct.setTecnicoAprobante((Tecnico)userDB.getUserFromDB(rs.getString("IDTecnicoAprobante")));
                }*/
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return movAct;
    }

    /**
     * Retorna una lista con todos los movimientos de activos en la DB
     *
     * @return
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public ArrayList<MovimientoActivo> getAllMovActs() throws SQLException, SNMPExceptions {
        ArrayList<MovimientoActivo> movActs = new ArrayList<>();
        FuncionarioDB funcDB = new FuncionarioDB();
        TecnicoDB tecDB = new TecnicoDB();
        String sqlSelect = "";
        try {
            sqlSelect = "Select * From Movimiento_Activo";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                Activo activo = activoDB.getActivoFromDB(rs.getString("IDActivo"));
                Usuario funcSolicitante = funcDB.getFuncFromDB(rs.getString("IDSolicitante")); 
                Date fechaSolicitud = rs.getDate("Fecha_Solicitud");
                String motivo = rs.getString("Motivo");
                boolean aprobacion = rs.getBoolean("Aprobacion");
                Usuario tecAprobante = tecDB.getTecFromDB(rs.getString("IDTecnicoAprobante")); 
 
                MovimientoActivo movAct = new MovimientoActivo(activo, fechaSolicitud, motivo, aprobacion);
                movAct.setFuncionarioSolicitante((Funcionario) funcSolicitante);
                movAct.setTecnicoAprobante((Tecnico) tecAprobante);
                movActs.add(movAct);

            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return movActs;
    }
    
    /**
     * Retorna todos los movimientos de activos que no han sido aprobados
     * @return ArrayList<MovimientoActivo>
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<MovimientoActivo> getMovActsNoAprobados() throws SQLException, SNMPExceptions{
        ArrayList<MovimientoActivo> movActs = new ArrayList<>();
        FuncionarioDB funcDB = new FuncionarioDB();
        String sqlSelect = "";
        try{
            sqlSelect = "Select IDActivo, IDSolicitante, Fecha_Solicitud, Motivo, Aprobacion, IDTecnicoAprobante"
                     + "From Movimiento_Activo Where Aprobacion = " + 0;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while(rs.next()){
                Activo activo = activoDB.getActivoFromDB(rs.getString("IDActivo"));
                Usuario funcSolicitante = funcDB.getFuncFromDB(rs.getString("IDSolicitante")); 
                Date requestDate = rs.getDate("Fecha_Solicitud");
                String motivo = rs.getString("Motivo");
                boolean aprobado = rs.getBoolean("Aprobacion");
                
                MovimientoActivo movAct = new MovimientoActivo(activo, requestDate, motivo, aprobado);
                movAct.setFuncionarioSolicitante((Funcionario) funcSolicitante);
                movActs.add(movAct);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return movActs;
    }

    /**
     * Aprueba el movimiento de un activo ya sea un traslado o un prestamo y
     * actualiza la informacion en la DB
     *
     * @param movAct
     * @throws SQLException
     * @throws SNMPExceptions
     * @throws ACTIException
     */
    public void aproveMovimientoAct(MovimientoActivo movAct) throws SQLException, SNMPExceptions, ACTIException {
        //Se obtiene y asigna el tecnico que aprobo
        String profileInUse = userDB.getLogedInProfile();

        //Se tiene que verifica que el usuario sea un tecnico
        if (profileInUse.equals("Tecnico")) {
            movAct.setAprobado(true);
            Tecnico tec = new Tecnico();
            tec.setIdentificacion(userDB.getLogedInUser().getIdentificacion()); 
            movAct.setTecnicoAprobante(tec);
            updateMovimientoAct(movAct);
            
        } else {
            throw new ACTIException("Accion no permitida, solo los Tecnicos pueden aprobar un movimiento de activo");
        }
    }

    /**
     * Solicita un movimiento de Activo y inserta el nuevo movimiento en la DB
     *
     * @param movAct
     * @throws SQLException
     * @throws SNMPExceptions
     * @throws ACTIException
     */
    public void solicitaMovActivo(MovimientoActivo movAct) throws SQLException, SNMPExceptions, ACTIException {
        //Se obtiene y asigna el funcionario que lo solicito
        String profileInUse = userDB.getLogedInProfile();

        //Se tiene que verificar que el usuario sea un funcionario
        if (profileInUse.equals("Funcionario")) {
            Funcionario func = new Funcionario();
            func.setIdentificacion(userDB.getLogedInUser().getIdentificacion()); 
            movAct.setFuncionarioSolicitante(func); 
            this.saveMovimientoAct(movAct);
        } else {
            throw new ACTIException("Accion no permitida, solo los Funcionarios pueden solicitar un movimiento de activo");
        }
    }
}
