/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author danielp
 */
public class Utils {
    
    /**
     * Encripta la clave con un Hash para guardarse de esa manera en la DB
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String getHashedPaswd(String password) throws Exception {
        //Primero obtener los bytes
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        //Luego obtener el hash en codigo hexadecimal y convertirlo a String
        StringBuilder hexString = new StringBuilder(2 * hashedBytes.length);
        for (int i = 0; i < hashedBytes.length; i++) {
            String hex = Integer.toHexString(0xff & hashedBytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    /**
     * Obtiene la fecha actual
     * @return Date
     * @throws ParseException 
     */
    public static Date getCurrentDate() throws ParseException{
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(dateFormat.format(currentDate));
    }
    
    /**
     * Envia el correo con la clave autogenerada y el codigo de seguridad 
     * para el primer login
     * @param user
     * @param generatedPasswd 
     */
    public static void sendLoginInfoEmail(Usuario user, String generatedPasswd){
        //Bro para este metodo tiene que enviar el String que genera el metodo generateFirstPasswd()
        //tiene que pasarlo a este metodo sin encriptar porque el usuario tiene que ver la clave, 
        //y luego de eso entonces ahi si lo tiene que encriptar para guardarlo en la DB
        
        
    }
    
    /**
     * Envia el correo que confirma la habilitacion de la cuenta
     * @param profile 
     */
    public static void sendAccountConfirmationEmail(UsuarioPerfil profile){
        if(profile.isAprobado()){
            //Se envia el correo
        }
    }
}
