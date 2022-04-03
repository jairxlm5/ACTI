/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class TrasladoDB {

    private static final DataAccess dataAccess = new DataAccess();

    private ActivoDB activoDB = new ActivoDB();
    private UsuarioDB userDB = new UsuarioDB();
    private MovimientoActivoDB movActDB = new MovimientoActivoDB();
    private SedeDB sedeDB = new SedeDB();

    /**
     * Guarda la informacion de un traslado de activo en la DB
     *
     * @param traslado
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public void saveTraslado(Traslado traslado) throws SQLException, SNMPExceptions {
        String sqlCommand = "";
        try {
            StringBuilder str = new StringBuilder();
            str.append("Insert Into Traslados Values (");
            str.append("'").append(traslado.getActivo().getIdActivo()).append("', ");
            str.append("'").append(traslado.getFuncionarioSolicitante().getIdentificacion()).append("', ");
            str.append(traslado.getFecha_Solicitud()).append(", ");
            str.append(traslado.getFechaTraslado()).append(", ");
            str.append("'").append(traslado.getSedeOrigen().getCodigo()).append("', ");
            str.append("'").append(traslado.getSedeDestino().getCodigo()).append("' )");

            sqlCommand = str.toString();
            dataAccess.executeSQLCommand(sqlCommand);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    /**
     * Obtiene los datos de un traslado de la DB
     *
     * @param idActivo
     * @param idFunc
     * @param fechaSolicitud
     * @return Traslado
     * @throws SQLException
     * @throws SNMPExceptions
     */
    public Traslado getTrasladoFromDB(String idActivo, String idFunc, Date fechaSolicitud) throws SQLException, SNMPExceptions {
        Traslado traslado = null;
        String sqlSelect = "";
        try {
            sqlSelect = "Select IDActivo, IDSolicitante, Fecha_Solicitud, Fecha_Traslado, Sede_Origen, Sede_Destino"
                    + " From Traslados "
                    + "Where IDActivo Like " + idActivo + " and IDSolicitante Like " + idFunc + " and "
                    + "Fecha_Solicitud = " + fechaSolicitud;
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            if (rs.next()) {
                //Se traen los datos de la tabla Movimiento_Activo correspondientes al traslado
                traslado = (Traslado) movActDB.getMovActFromDB(idActivo, idFunc, fechaSolicitud);
                traslado.setFechaTraslado(rs.getDate("Fecha_Traslado"));
                traslado.setSedeOrigen(sedeDB.getSede(rs.getString("Sede_Origen")));
                traslado.setSedeDestino(sedeDB.getSede(rs.getString("Sede_Destino")));

            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return traslado;
    }

    /**
     * Retorna una lista con todos los traslados registrados
     * @return
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Traslado> getAllTraslados() throws SQLException, SNMPExceptions {
        ArrayList<Traslado> traslados = new ArrayList<>();
        String sqlSelect = "";
        try {
            sqlSelect = "Select * From Traslados";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                Activo activo = activoDB.getActivoFromDB(rs.getString("IDActivo"));
                Usuario funcSolicitante = userDB.getUserFromDB(rs.getString("IDSolicitante"));
                Date requestDate = rs.getDate("Fecha_Solicitud");
                
                Traslado traslado = (Traslado)movActDB.getMovActFromDB(activo.getIdActivo(), 
                                        funcSolicitante.getIdentificacion(), requestDate);
                traslado.setFechaTraslado(rs.getDate("Fecha_Traslado"));
                traslado.setSedeOrigen(sedeDB.getSede(rs.getString("Sede_Origen")));
                traslado.setSedeDestino(sedeDB.getSede(rs.getString("Sede_Destino")));
                traslados.add(traslado);
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());

        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return traslados;
    }
    
    /**
     * Retorna una lista con todos los traslados que no han sido aprobados
     * @return ArrayList<Traslado>
     * @throws SQLException
     * @throws SNMPExceptions 
     */
    public ArrayList<Traslado> getTrasladosNoAprobados() throws SQLException, SNMPExceptions{
        ArrayList<Traslado> traslados = new ArrayList<>();
        //Se tienen que obtener todos los movimientos de activo que no han sido aprobados
        ArrayList<MovimientoActivo> movsNoAprobados = movActDB.getMovActsNoAprobados();
        
        //Se tiene que verificar cuales son traslados para agregarlos a la lista
        for(MovimientoActivo movAct : movsNoAprobados){
            String idActivo = movAct.getActivo().getIdActivo();
            String idFunc = movAct.getFuncionarioSolicitante().getIdentificacion();
            Date fechaSolicitud = movAct.getFecha_Solicitud();
            Traslado traslado = this.getTrasladoFromDB(idActivo, idFunc, fechaSolicitud);
            
            //Si es un traslado se agrega a la lista
            if(traslado != null){
                traslados.add(traslado);
            }
        }
        return traslados;
    }
}
