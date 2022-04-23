/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.Usuario;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
     *
     * @return Date
     * @throws ParseException
     */
    public static Date getCurrentDate() throws ParseException {
        Date currentDate = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateFormat.format(currentDate));
    }

    /**
     * Envia el correo con la clave autogenerada y el codigo de seguridad para
     * el primer login
     *
     * @param user
     * @param generatedPasswd
     */
    public static void sendLoginInfoEmail(Usuario user, String generatedPasswd) throws MessagingException {
        String reciever = user.getCorreo();
        String sender = "actiPruebas@gmail.com";
        String password = "Progra123";
        String host = "smtp.gmail.com";
        int port = 587;
        
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.clave", password);
        /*
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", String.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        */
        
        Session session = Session.getInstance(props);
        //session.setDebug(true);
        
        String msgContent = "<html>\n"+
                           "<h1>Hola " + user.getNombre() + "</h1>\n" +
                             "<br>\n" + 
                            "<p>Su codigo de seguridad es " + user.getCodSeguridad() + "</p>\n" +
                            "<p>Su contraseña es: " + generatedPasswd + "</p>\n" + 
                            "</html>";

        try {
            MimeMessage msg = new MimeMessage(session);
            //msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
            msg.setSubject("ACTI: Sus credenciales para inicio de sesion");
            msg.setContent(msgContent, "text/html");
            
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, sender, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception mesgException) {
            throw new MessagingException(mesgException.getMessage());
        }
    }

    /**
     * Envia el correo que confirma la habilitacion de la cuenta
     *
     * @param profile
     */
    public static void sendAccountConfirmationEmail(Usuario user) throws MessagingException{
        if (user.isAprobado()) {
            //Se envia el correo
            String reciever = user.getCorreo();
            String sender = "actiPruebas@gmail.com";
            String password = "Progra123";
            String host = "smtp.gmail.com";
            int port = 587;
            
            Properties props = System.getProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", host);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.user", sender);
            props.put("mail.smtp.clave", password);

            Session session = Session.getInstance(props);
            //session.setDebug(true);
            
            String msgContent = "<html>\n"+
                                  "<h1>Hola " + user.getNombre() + "</h1>\n" +
                                   "<br>\n" + 
                                   "<p>Su cuenta en ACTI ha sido habilitada, ya puede ingresar</p>\n" +
                                "</html>";

            try {
                MimeMessage msg = new MimeMessage(session);
                //msg.setFrom(new InternetAddress(sender));
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
                msg.setSubject("ACTI: Cuenta Habilitada");
                msg.setContent(msgContent, "text/html");

                Transport transport = session.getTransport("smtp");
                transport.connect(host, port, sender, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
            } catch (Exception mesgException) {
                throw new MessagingException(mesgException.getMessage());
            }
        }
    }
}
