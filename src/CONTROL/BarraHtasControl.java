package CONTROL;
import MODELO.DAO.CitaDAO;
import MODELO.DTO.Cita;
import VISTA.BarraHtasIGU;
import VISTA.MenuPrincipalIGU;
import VISTA.NuevaCitaIGU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class BarraHtasControl implements ActionListener, KeyListener, DocumentListener {

	BarraHtasIGU vista;
	NuevaCitaIGU ventana;
        MenuPrincipalIGU ventanaMenu;
		
	public BarraHtasControl(BarraHtasIGU vista, NuevaCitaIGU ventana){
		this.vista = vista;
		this.ventana = ventana;
	}

        
        public BarraHtasControl(BarraHtasIGU vista, MenuPrincipalIGU ventanaMenu){
		this.vista = vista;
		this.ventanaMenu = ventanaMenu;
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
	
		switch(evento.getActionCommand()){
                    case   "Regresar": {
                        try {
                            regresar();
                            } catch (IOException ex) {
                                Logger.getLogger(BarraHtasControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                            
                    case  "NuevaCita":
                        NuevaCitaIGU nuevaCita = new NuevaCitaIGU();
                        ventanaMenu.dispose();
                        break;
                        
                    case  "Borrar" :
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String fecha = dateFormat.format(MenuPrincipalIGU.getDate());
                            Integer hora = MenuPrincipalIGU.getHora();
                            Integer idEstilista = MenuPrincipalIGU.getEstilista();
                            Cita cita = new Cita(fecha, hora, idEstilista+1);
                            if(CitaDAO.borrar(cita)){
                                JOptionPane.showMessageDialog(ventanaMenu, "Cita borrada con éxito");
                                ventanaMenu.setTabla(MenuPrincipalIGU.getDate());
                            }
                            else{
                                JOptionPane.showMessageDialog(ventanaMenu, "No se borró la cita, intenta en la primer hora de este servicio");                                
                            }
                            break;
                        
                    case "Apagar" :
                
                    try {
                        int respuesta=JOptionPane.showConfirmDialog(null, "Confirmar salir", "¿Realmente deseas salir?", JOptionPane.YES_NO_OPTION);
                        if(respuesta==JOptionPane.YES_OPTION){
                            Runtime.getRuntime().exec("shutdown -s");
                        }
                        
                        
                        //System.out.println(ventanaMenu.getEstilista());
                        //System.out.println(ventanaMenu.getHora());
                    } catch (IOException ex) {
                        Logger.getLogger(BarraHtasControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                        
                            
                           
		}
	}
	
	public void regresar() throws IOException{
            MenuPrincipalIGU menuPrincipal = new MenuPrincipalIGU();
            ventana.dispose();
        }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}