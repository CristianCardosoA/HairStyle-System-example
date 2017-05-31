/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROL;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;

/**
 *
 * @author Cristianloki
 */
public class BuscarControl implements MouseListener {
    
    JTextField txBuscar;

    public BuscarControl(JTextField txBuscar){
        this.txBuscar = txBuscar;
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        txBuscar.setText("");
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}
