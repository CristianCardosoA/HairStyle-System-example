/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VISTA;

import java.awt.Font;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Cristianloki
 */
public class Reloj extends JFrame{
        
        JLabel lbHora;
    
    public JLabel regresaReloj(){
        
        lbHora = new JLabel();
        lbHora.setFont(new Font("Monospaced", Font.BOLD, 25));
        lbHora.setHorizontalAlignment(JLabel.RIGHT);
        final Calendar calendario = new java.util.GregorianCalendar(); 
        int segundos = calendario.get(Calendar.SECOND); 
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() { 
        @ Override 
        public void actionPerformed(java.awt.event.ActionEvent ae) { 
        java.util.Date actual = new java.util.Date(); 
        calendario.setTime(actual); 
        int dia = calendario.get(Calendar.DAY_OF_MONTH); 
        int mes = (calendario.get(Calendar.MONTH) + 1); 
        int año = calendario.get(Calendar.YEAR); 
        int hora = calendario.get(Calendar.HOUR_OF_DAY); 
        int minutos = calendario.get(Calendar.MINUTE); 
        int segundos = calendario.get(Calendar.SECOND); 
        String hour = String.format("%02d:%02d:%02d", hora, minutos, segundos); 
        lbHora.setText("<html><center> &nbsp;&nbsp;" + hour ); 
           } 
        }); 
        timer.start(); 
        return lbHora;
    } 

    public JLabel getLbHora() {
        return lbHora;
    }

    public void setLbHora(JLabel lbHora) {
        this.lbHora = lbHora;
    }
    
}
