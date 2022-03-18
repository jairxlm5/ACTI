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
public enum Perfil {
    PERFIL, //Este es con puros fines ilustrativos a la hora de desplegar la info en los combos
    Administrativo,
    Funcionario,
    Tecnico{
        @Override
        public String toString(){
            return "Técnico";
        }
    }
}
