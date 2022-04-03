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

    public Prestamo(Date fechaRetorno, Activo activo,  Date fecha_Solicitud, String motivo,
                         boolean aprobado) {
        super(activo, fecha_Solicitud, motivo, aprobado);
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
