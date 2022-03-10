/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author danielp
 */
public enum TipoIdentificacion {
    Cedula_Nacional{
        @Override
        public String toString(){
            return "Cedula de Nacional";
        }
    },
    
    Cedula_Residente{
        @Override
        public String toString(){
            return "Cedula de Residente";
        }
    },
    Numero_Refugiado{
        @Override
        public String toString(){
            return "Numero de Refugiado";
        }
    }
}
