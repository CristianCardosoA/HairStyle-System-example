/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROL;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Cristianloki
 */

public class CustomRenderer2 extends DefaultTableCellRenderer {
           
            @Override
	public Component getTableCellRendererComponent(	JTable table, Object value,
							boolean isSelected, boolean hasFocus,
							int row, int column)
	{
	    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	                     ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
	    
                c.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 20));    
	    return c;
	}
    }
