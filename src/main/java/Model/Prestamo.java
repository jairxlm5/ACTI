/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author danielp
 */
public class Prestamo extends MovimientoActivo{
    private Date fechaRetorno;

    public Prestamo(Date fechaRetorno, Activo activo, Funcionario funcionarioSolicitante, Date fecha_Solicitud, String motivo,
                         boolean aprobado, Tecnico tecnicoAprobante) {
        super(activo, funcionarioSolicitante, fecha_Solicitud, motivo, aprobado, tecnicoAprobante);
        this.fechaRetorno = fechaRetorno;
    }
    
    public Prestamo(){
        super();
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }
    
    
}
