/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.calixto.spithash;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author deividson
 */
@ViewScoped
@ManagedBean
public class PrincipalBean implements Serializable{
    private String valorEntrada = "";
    private String valorSaida = "";
    
    @PostConstruct
    public void init(){
        
    }
    
   
    
    public void enviar(){
        try {
            this.valorSaida = MySQLPassword(valorEntrada);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Problemas ao converter, entrada inválida!"));
        }
    }
    
    public static String MySQLPassword(String plainText) throws UnsupportedEncodingException {
        byte[] utf8 = plainText.getBytes("UTF-8");
        byte[] test = DigestUtils.sha(DigestUtils.sha(utf8));
        return "*" + convertToHex(test).toUpperCase();
    }
    
    public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;

            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }

                halfbyte = data[i] & 0x0F;

            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public String getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(String valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(String valorSaida) {
        this.valorSaida = valorSaida;
    }
    
    
}
