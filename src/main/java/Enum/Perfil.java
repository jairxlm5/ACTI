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
    Administrativo,
    Funcionario,
    Tecnico{
        @Override
        public String toString(){
            return "Técnico";
        }
    }
}
