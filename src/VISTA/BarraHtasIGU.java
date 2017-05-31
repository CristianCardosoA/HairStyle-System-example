package VISTA;
import CONTROL.BarraHtasControl;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class BarraHtasIGU extends JPanel{

	private static final long serialVersionUID = 1L;

	private JButton btNuevo, btEditar, btBorrar, btBuscar, btRegresar, btGuardar;
	private JTextField txBuscarNombre;
	private BarraHtasControl controlador;

	public BarraHtasIGU(MenuPrincipalIGU ventana){		

		controlador = new BarraHtasControl(this, ventana);
		setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
				
		JToolBar barraHtas = new JToolBar();
		add(barraHtas);
		barraHtas.setFloatable(false);
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		barraHtas.add(separator);
		
		btNuevo = new JButton("");
		btNuevo.setActionCommand("NuevaCita");
		btNuevo.setToolTipText("Nueva Cita");
		btNuevo.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/nuevo.png")));
		barraHtas.add(btNuevo);
		
		btBorrar = new JButton("");
		btBorrar.setActionCommand("Borrar");
		btBorrar.setToolTipText("Borrar un articulo");
		btBorrar.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/borrar.png")));
		btBorrar.setEnabled(false);
                barraHtas.add(btBorrar);
		
		btEditar = new JButton("");
		btEditar.setActionCommand("Editar");
		btEditar.setToolTipText("Modificar el articulo");
		btEditar.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/editar.png")));
                btEditar.setEnabled(false);
                barraHtas.add(btEditar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		barraHtas.add(separator_1);
		
		btBuscar = new JButton("");
		btBuscar.setActionCommand("Apagar");
		btBuscar.setToolTipText("Apagar el equipo");
		btBuscar.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/apagar.png")));
		barraHtas.add(btBuscar);
		setSize(300, 200);
		setVisible(true);
		btNuevo.addActionListener(controlador);
                btBorrar.addActionListener(controlador);
		btEditar.addActionListener(controlador);
                btBuscar.addActionListener(controlador);
	}
        
        public BarraHtasIGU(NuevaCitaIGU ventana){		
                
		controlador = new BarraHtasControl(this, ventana);
		setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		//setLayout();
				
		JToolBar barraHtas = new JToolBar();
                barraHtas.setMargin(new Insets(10,30,10,10));
		add(barraHtas);
		barraHtas.setFloatable(false);
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		barraHtas.add(separator);
		
		btGuardar = new JButton(new ImageIcon(getClass().getResource("/IMAGENES/guardar.png")));
		btGuardar.setActionCommand("Guardar");
                btGuardar.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 40));
		btGuardar.setToolTipText("Guardar Cita");
                btGuardar.setEnabled(false);
		btRegresar = new JButton(new ImageIcon(getClass().getResource("/IMAGENES/regresar.png")));
		btRegresar.setActionCommand("Regresar");
                btRegresar.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 40));
		btRegresar.setToolTipText("Regresar al menu principal");
		setSize(300, 200);
		setVisible(true);
                barraHtas.add(btRegresar);
		barraHtas.add(btGuardar);
		btRegresar.addActionListener(controlador);
		btGuardar.addActionListener(controlador);
	}
        
        public void bloquear(){
            
        }
	//getters

        public JTextField getTxBuscarNombre() {
            return txBuscarNombre;
        }

        public void setTxBuscarNombre(JTextField txBuscarNombre) {
            this.txBuscarNombre = txBuscarNombre;
        }
        
	public JButton getBtNuevo() {
		return btNuevo;
	}
	public JButton getBtEditar() {
		return btEditar;
	}
	public JButton getBtBorrar() {
		return btBorrar;
	}
	public JButton getBtBuscar() {
		return btBuscar;
	}

        public JButton getBtRegresar() {
            return btRegresar;
        }

        public void setBtRegresar(JButton btRegresar) {
            this.btRegresar = btRegresar;
        }

        public JButton getBtGuardar() {
            return btGuardar;
        }

        public void setBtGuardar(JButton btGuardar) {
            this.btGuardar = btGuardar;
        }

        public BarraHtasControl getControlador() {
            return controlador;
        }

        public void setControlador(BarraHtasControl controlador) {
            this.controlador = controlador;
        }
}