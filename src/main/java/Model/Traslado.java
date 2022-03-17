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
public class Traslado extends MovimientoActivo{
    private Date fechaTraslado;
    private Sede sedeOrigen, sedeDestino;

    public Traslado(Date fechaTraslado, Sede sedeOrigen, Sede sedeDestino, Activo activo, Funcionario funcionarioSolicitante, Date fecha_Solicitud, String motivo,
                       boolean aprobado, Tecnico tecnicoAprobante) {
        super(activo, funcionarioSolicitante, fecha_Solicitud, motivo, aprobado, tecnicoAprobante);
        this.fechaTraslado = fechaTraslado;
        this.sedeOrigen = sedeOrigen;
        this.sedeDestino = sedeDestino;
    }

    public Traslado() {
        super();
    }

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public Sede getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(Sede sedeOrigen) {
        this.sedeOrigen = sedeOrigen;
    }

    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino) {
        this.sedeDestino = sedeDestino;
    }
    
    
}
