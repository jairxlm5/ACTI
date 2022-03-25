/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.DataAccess;
import DAO.SNMPExceptions;
import Enum.Perfil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author danielp
 */
public class PerfilDB {

    private static final DataAccess dataAccess = new DataAccess();

    public ArrayList<Perfil> getAllPerfiles() throws SNMPExceptions, SQLException {
        ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
        String sqlSelect = "";
        try {
            sqlSelect = "Select ID From Tipo_Perfil";
            ResultSet rs = dataAccess.executeSQLReturnsRS(sqlSelect);
            while (rs.next()) {
                int id = rs.getInt("ID");
                //Verifica si el Enum existe
                if (Perfil.values()[id] != null) {
                    perfiles.add(Perfil.values()[id]);
                }
            }
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return perfiles;
    }
}
