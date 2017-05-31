package VISTA;

import CONTROL.BuscarControl;
import CONTROL.CustomRenderer2;
import MODELO.DAO.CitaDAO;
import MODELO.DAO.ClientesDAO;
import MODELO.DAO.EstilistaDAO;
import MODELO.DAO.HorasDAO;
import MODELO.DAO.ServiciosDAO;
import MODELO.DTO.Cita;
import MODELO.DTO.Cliente;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Cristianloki
 */
public class NuevaCitaIGU extends JFrame {
    
    private static NuevaCitaIGU ventana;
    private JLabel lbHora;
    private final Box vertical = Box.createVerticalBox();
    private Integer idEstilista;
    private Integer Servicio;
    private Integer hora;
    private Date date;
    private JTextField txClienteN, txClienteA, txClienteM, txCelular, txBuscar;
    private JComboBox boxEstilista, boxServicio, boxHora;
    private JCalendar calendario;
    private JDateChooser dateChooser;
    private JTable tablaClientes;
    BarraHtasIGU barraHtas;                
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
     public NuevaCitaIGU (int idEstilista, int hora, Date date){
         this.idEstilista = idEstilista;
         this.hora = hora;
         this.date = date;
         this.Servicio = 1;
         setCostructor();
     }
     
     public NuevaCitaIGU (){
        this.idEstilista = null;
         this.hora = null;
         this.Servicio=1;
        setCostructor();
     }
     
     public void setCostructor(){
        setTitle("*** CITAS DIVAS LIFE ***");
        setUndecorated(true); //FULL SCREEN
        vertical.add(Box.createVerticalStrut(5));
        vertical.setBackground(Color.white);
        Dimension dim = this.getToolkit().getScreenSize();
        Rectangle rec = this.getBounds();
        setSize(dim.width,dim.height);
        this.requestFocus();
	setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addPanelSuperior();
        addCampos();
        setControl();
        addBarraHtas();
        add(vertical);
        activarBotonGuardar();
        //boxContinue();
        
     }
     
     private void addPanelSuperior() {
        JPanel pnSuperior = new JPanel();
        JPanel pnIzquierdo = new JPanel();
        JPanel pnDerecho = new JPanel();
        JLabel lbTitulo = new JLabel("  DIVAS LIFE  ");
        JLabel lbDia = new JLabel();
        lbDia.setForeground(Color.BLACK);
        lbDia.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        lbDia.setText(new String(" " + dateFormat.format(new Date()) + " ").toUpperCase());
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 50));
        Color customColor = new Color(219,0,25);
        lbTitulo.setForeground(customColor);
        lbTitulo.setHorizontalAlignment(JLabel.LEFT);
        pnIzquierdo.add(lbTitulo);
        pnIzquierdo.setBorder(new EmptyBorder(10, 10, 10, 20));
        pnIzquierdo.setBackground(Color.white);
        pnDerecho.add(lbDia);
        pnDerecho.add(new Reloj().regresaReloj());
        JLabel lbMexico = new JLabel();
        lbMexico.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/banderaM.png")));
        pnDerecho.add(lbMexico);
        pnDerecho.setBackground(Color.white);
        pnSuperior.setBackground(Color.white);
        pnSuperior.add(pnIzquierdo);
        pnSuperior.add(pnDerecho);
        vertical.add(pnSuperior);
    }
     
    
     public void setTabla() {
		
                String titulos [] = {"Id","Nombre","Apellido Paterno","Apellido Materno","Telefono"
        };
            final DefaultTableModel modeloTabla = new DefaultTableModel(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {  //tabla no editable
				return false;
			}
		};
                
                Object datos [][] = ClientesDAO.getClientes();
                
                //****************************************
		modeloTabla.setDataVector(datos,titulos);
                //****************************************
		tablaClientes.setModel(modeloTabla);
                tablaClientes.setBackground(Color.WHITE);
                tablaClientes.setSelectionBackground(Color.yellow);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
                for(int i = 1; i < tablaClientes.getColumnCount(); i++){
                   
                    tablaClientes.getColumnModel().getColumn(i).setPreferredWidth(155);
                }
                tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tablaClientes.setDefaultRenderer(Object.class,new CustomRenderer2());
                tablaClientes.setRowHeight(50);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );           
	}
     
     public JPanel getPanelTabla(){

		tablaClientes = new JTable();
		JPanel pnLista = new JPanel();
                pnLista.setLayout(new BoxLayout(pnLista,BoxLayout.Y_AXIS));
                Color customColor = new Color(242,158,62);
                Border borde;
                borde = BorderFactory.createLineBorder(customColor,0);
		pnLista.setBorder(new TitledBorder(borde, "¡ Selecciona un cliente !", 
						TitledBorder.CENTER, TitledBorder.TOP,new Font("Arial", Font.PLAIN, 20), Color.red.darker()));
                
		JScrollPane scrollTabla = new JScrollPane(tablaClientes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);                
                JScrollBar barra = scrollTabla.getHorizontalScrollBar();
                barra.setBackground(Color.gray);
                barra.setUI(new MyScrollbarUI());
                barra.setBlockIncrement(400);
                barra.setPreferredSize(new Dimension(30,30));
                pnLista.add(scrollTabla);
                pnLista.add(barra);
                tablaClientes.getTableHeader().setFont(new Font("Arial",Font.PLAIN, 15));
                tablaClientes.getTableHeader().setForeground(Color.darkGray.brighter());
		setTabla();
                return pnLista;
	}
     
     
     
     public void addCampos(){
         JPanel pnCampos = new JPanel();
         Font letra = new Font("Arial", Font.PLAIN, 25);
         Font letra2 = new Font("Arial", Font.ITALIC, 20);
         Font letra3 = new Font("Arial", Font.BOLD, 40);
         Font letra4 = new Font("Arial", Font.CENTER_BASELINE, 25);
         Box vertical2 = Box.createVerticalBox();
         Box vertical3 = Box.createVerticalBox();
         Box vertical4 = Box.createVerticalBox();
         Border borde;
         borde = BorderFactory.createSoftBevelBorder(5);
         pnCampos.setBorder(new TitledBorder(borde, " NUEVA CITA ", 
                 TitledBorder.CENTER, TitledBorder.ABOVE_TOP,new Font("Arial", Font.PLAIN, 25), Color.darkGray.darker()));
         pnCampos.setBackground(Color.LIGHT_GRAY);
         pnCampos.setLayout(new GridLayout(1,3));
         JLabel lbEstilista = new JLabel();
         JLabel lbHora = new JLabel();
         JLabel lbFecha = new JLabel();
         final JLabel lbClienteN = new JLabel();
         final JLabel lbClienteA = new JLabel();
         final JLabel lbClienteM = new JLabel();
         JLabel lbServicio = new JLabel();
         JLabel lbClienteCel = new JLabel("Celular : ");
         JLabel lbCliente = new JLabel();
         
         //BOTONES 
        JButton btnNuevo = new JButton();
        JButton btnExistente = new JButton();
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/nuevoC.png")));
        btnExistente.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/verContacto.png")));
         //
         
         lbEstilista.setText("Selecciona estilista :");
         lbEstilista.setFont(letra2);
         lbHora.setText("Selecciona hora :");
         lbHora.setFont(letra2);
         lbFecha.setText("Selecciona fecha :");
         lbFecha.setFont(letra2);
         lbClienteN.setText("Nombre :");
         lbClienteN.setFont(letra2);
         lbClienteA.setText("Apellido Paterno :");
         lbClienteA.setFont(letra2);
         lbClienteM.setText("Apellido Materno :");
         lbClienteM.setFont(letra2);
         lbCliente.setText("");
         lbCliente.setFont(letra2);
         lbServicio.setText("Servicio : ");
         lbServicio.setFont(letra2);
         lbClienteCel.setFont(letra2);
         txClienteN = new JTextField(40);
         txClienteA = new JTextField(40);
         txClienteM = new JTextField(40);
         txCelular = new JTextField(40);
         txBuscar = new JTextField(20);
         txClienteN.setFont(letra4);
         txClienteA.setFont(letra4);
         txClienteM.setFont(letra4);
         txCelular.setFont(letra4);
         txClienteN.setPreferredSize(new Dimension(80,45));
         txClienteA.setPreferredSize(new Dimension(80,45));
         txClienteM.setPreferredSize(new Dimension(80,45));
         txCelular.setPreferredSize(new Dimension(80,45));
         txBuscar.setPreferredSize(new Dimension(80,45));
         txBuscar.setText(" Buscar ");
        
         
         //ESTILISTAS
         String estilistas [] = EstilistaDAO.getEstilistas();
         String serviciosDisponibles []= ServiciosDAO.getServicios();
         boxEstilista = new JComboBox(estilistas);
         boxEstilista.setFont(letra);
         boxEstilista.setPreferredSize(new Dimension(250,50));
         boxEstilista.setSelectedIndex(-1);
         boxEstilista.addItemListener(new ItemListener() {

             @Override
             public void itemStateChanged(ItemEvent ie) {
                 
                 idEstilista = boxEstilista.getSelectedIndex() + 1;
                 boxEstilista.setEnabled(false);
                 System.out.println(Servicio);
                 boxServicio.setEnabled(true);
                 
             }
         });
         
         boxServicio = new JComboBox(serviciosDisponibles);
         boxServicio.setFont(letra);
         boxServicio.setPreferredSize(new Dimension(250,50));
         boxServicio.setSelectedIndex(-1);
         boxServicio.setEnabled(false);
         boxServicio.addItemListener(new ItemListener(){
             @Override
             public void itemStateChanged(ItemEvent ie){
                 Servicio = boxServicio.getSelectedIndex()+1;
                 boxServicio.setEnabled(false);
                 //System.out.println(Servicio);
                 if(idEstilista!=null){
                    dateChooser.setEnabled(true);
                 }
             }
         
     });         
         if(date != null){
                  String horasDisponibles [] = HorasDAO.getHorasDisponibles(dateFormat.format(date),idEstilista+1);
                  boxHora = new JComboBox(horasDisponibles);
             
         }else{
             String horasDisponibles [] = {"8:00 a.m.","8:30 a.m.","9:00 a.m.","9:30 a.m.","10:00 a.m.",
                    "10:30 a.m.","11:00 a.m.", "11:30 a.m.","12:00 p.m.","12:30 p.m.","1:00 p.m."
                ,"1:30 p.m.","2:00 p.m.","2:30 p.m.","3:00 p.m.","3:30 p.m.","4:00 p.m.","4:30 p.m."
                ,"5:00 p.m.","5:30 p.m.","6:00 p.m.","6:30 p.m.","7:00 p.m.","7:30 p.m.","8:00 p.m."
                };
                  boxHora = new JComboBox(horasDisponibles);
         }
         
         boxHora.setFont(letra);
         boxHora.setPreferredSize(new Dimension(250,50));
         
         ////////////////////////////////////////////////////
         ((JLabel)boxEstilista.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);         
         ((JLabel)boxServicio.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
         ((JLabel)boxHora.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

         calendario = new JCalendar(date); 
         calendario.setPreferredSize(new Dimension(250,300));
         //calendario.setEnabled(false);
         dateChooser = new JDateChooser();
         dateChooser.setPreferredSize(new Dimension(250,50));
         dateChooser.setFont(letra2);
         dateChooser.getCalendarButton().setPreferredSize(new Dimension(100,250));
         dateChooser.setEnabled(false);
         if(idEstilista != null){
             boxEstilista.setSelectedIndex(idEstilista);
             boxEstilista.setEnabled(false);
             boxHora.setSelectedIndex(hora);
             boxHora.setEnabled(false);
             lbHora.setText("Hora : " + boxHora.getSelectedItem().toString());
             lbFecha.setText("Fecha :" + new String(" " + dateFormat.format(date) + " ").toUpperCase());
             lbEstilista.setText("Estilista : " +  boxEstilista.getSelectedItem().toString());
         }
         if(hora != null){
             calendario.setEnabled(false);
             dateChooser.setVisible(false);
         }else{
             calendario.setVisible(false);
         }
        lbClienteN.setVisible(false);
        lbClienteA.setVisible(false);
        lbClienteM.setVisible(false);
        txClienteN.setVisible(false);
        txClienteA.setVisible(false);
        txClienteM.setVisible(false);
        txBuscar.setVisible(false);
        lbClienteN.setHorizontalAlignment(SwingConstants.CENTER);
        lbClienteA.setHorizontalAlignment(SwingConstants.CENTER);
        lbClienteM.setHorizontalAlignment(SwingConstants.CENTER);
        lbClienteCel.setHorizontalAlignment(SwingConstants.CENTER);
        txClienteN.setHorizontalAlignment(SwingConstants.CENTER);
        txClienteA.setHorizontalAlignment(SwingConstants.CENTER);
        txClienteM.setHorizontalAlignment(SwingConstants.CENTER);
        txCelular.setHorizontalAlignment(SwingConstants.CENTER);
        vertical2.add(new JLabel(" "));
        vertical2.add(new JLabel(" "));
        JLabel lbCitas = new JLabel("");
        lbCitas.setHorizontalAlignment(SwingConstants.CENTER);
        lbCitas.setFont(letra2);
        vertical2.add(lbCitas);
        vertical2.add(new JLabel(" "));
        vertical2.add(lbEstilista);
        vertical2.add(boxEstilista);
        vertical2.add(lbServicio);
        vertical2.add(boxServicio);
        vertical2.add(lbFecha);
        vertical2.add(calendario);
        vertical2.add(dateChooser);
        vertical2.add(lbHora);
        vertical2.add(boxHora);
    
          
        JPanel pnTemp = new JPanel();
        pnTemp.setBackground(Color.white);
        pnTemp.add(lbCliente);
        pnTemp.add(btnNuevo);
        pnTemp.add(btnExistente);
        pnTemp.add(txBuscar);
        
        final JPanel pnTemp2 = new JPanel();
        pnTemp2.setLayout(new GridLayout(6,2));
        pnTemp2.add(lbClienteN);
        pnTemp2.add(txClienteN);
        
        
        pnTemp2.add(lbClienteA);
        pnTemp2.add(txClienteA);
        
        pnTemp2.add(lbClienteM);
        pnTemp2.add(txClienteM);
        
        pnTemp2.add(lbClienteCel);
        pnTemp2.add(txCelular);
        
        pnTemp2.setVisible(false);
        pnTemp2.setBorder(new TitledBorder(borde, " *********** Nuevo Cliente ********* ", 
        TitledBorder.CENTER, TitledBorder.BELOW_TOP,new Font("Arial", Font.PLAIN, 20), Color.darkGray.darker()));
        vertical4.add(pnTemp);
        vertical4.add(pnTemp2);
        final JPanel pnTabla = getPanelTabla();
        pnTabla.setVisible(false);
        vertical4.add(pnTabla);
        
        pnCampos.add(vertical2);
        pnCampos.setBackground(Color.white);
        pnCampos.add(vertical4);
        vertical.add(pnCampos);
        
        btnNuevo.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent ae) {
                  
                   
                    lbClienteN.setVisible(true);
                    lbClienteA.setVisible(true);
                    lbClienteM.setVisible(true);
                    txClienteN.setVisible(true);
                    txClienteA.setVisible(true);
                    txClienteM.setVisible(true);
                    txBuscar.setVisible(false);
                    pnTemp2.setVisible(true);
                    pnTabla.setVisible(false);
                    tablaClientes.clearSelection();
             }
         });
        
        btnExistente.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent ae) {
                  
                    lbClienteN.setVisible(false);
                    lbClienteA.setVisible(false);
                    lbClienteM.setVisible(false);
                    txClienteN.setVisible(false);
                    txClienteA.setVisible(false);
                    txClienteM.setVisible(true);
                    txBuscar.setVisible(true);
                    pnTemp2.setVisible(false);
                    pnTabla.setVisible(true);
                    limpiar();
   
             }
         });
                
        
        txBuscar.addKeyListener(new KeyListener() {

             @Override
             public void keyTyped(KeyEvent ke) {
                txBuscar.setText(txBuscar.getText().toUpperCase());
                 filtrar();
             }

             @Override
             public void keyPressed(KeyEvent ke) {
              txBuscar.setText(txBuscar.getText().toUpperCase());

             }

             @Override
             public void keyReleased(KeyEvent ke) {
                               txBuscar.setText(txBuscar.getText().toUpperCase());
             }
         });
          
    dateChooser.getDateEditor().addPropertyChangeListener(
    new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            if ("date".equals(e.getPropertyName())) {
                 date = (Date)e.getNewValue();
                 //JOptionPane.showMessageDialog(ventana, dateFormat.format(date));
                 idEstilista = boxEstilista.getSelectedIndex()+1;
                 String horasDisponibles [] = HorasDAO.getHorasDisponibles(dateFormat.format(date),idEstilista,Servicio);
                 boxHora.setModel(new JComboBox<>(horasDisponibles).getModel());
                 barraHtas.getBtGuardar().setEnabled(true);
            }
        }
    });
       
     }

     public void setControl(){
                 txBuscar.addMouseListener(new BuscarControl(txBuscar));
     }
     
       public void filtrar() {
		int columnas [] = {0,1,2,3,4};
                
		TableRowSorter<TableModel>sorter = new TableRowSorter<>(tablaClientes.getModel());
		RowFilter<TableModel, Object> modeloFiltro = null;

        try {
        	modeloFiltro = RowFilter.regexFilter(txBuscar.getText(),columnas);
        } catch (java.util.regex.PatternSyntaxException e) {
        	return;
        }
        
        tablaClientes.setRowSorter(sorter);
        sorter.setRowFilter(modeloFiltro);
    }
    
    /*public void boxContinue(){
        boxHora.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getItem().toString().equals("No disponible")){
                    JOptionPane.showMessageDialog(ventana,"Selecciona una cita valida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }*/
       
    public void activarBotonGuardar(){
        barraHtas.getBtGuardar().setEnabled(false);
        barraHtas.getBtGuardar().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                idEstilista = boxEstilista.getSelectedIndex() + 1;
                hora = boxHora.getSelectedIndex();
                String fecha = null;
                Integer idCliente = null;
                
                int servicio = boxServicio.getSelectedIndex() + 1;
                //SI EL CALENDARIO ES JCALENDAR Ó ES DATECHOOSER
                if(calendario.isVisible()){
                    fecha = dateFormat.format(calendario.getDate());
                }else {
                    fecha = dateFormat.format(dateChooser.getDate());
                }
                ////////////////////////////////////////////
                //SI LA SELECCION DE CLIENTES ES
                //APARTIR DE LA TABLA O EL INGRESO DE UN NUEVO CLIENTE
                if((txClienteN.getText().equals("")) && (txClienteA.getText().equals("")) && (txClienteM.getText().equals(""))
                        && (txCelular.getText().equals(""))){
                    
                    idCliente = Integer.parseInt((String)tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0));
                    //System.out.println(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0));
                    //System.out.println("El id del cliente:"+ idCliente);
                }else{
                    if("".equals(txClienteA.getText())){txClienteA.setText(" ");}
                    if("".equals(txClienteM.getText())){txClienteM.setText(" ");}    
                    Cliente cliente = new Cliente (txClienteN.getText(),txClienteA.getText(),txClienteM.getText(),txCelular.getText());
                
                    
                    ClientesDAO.insertar(cliente);
                    idCliente = ClientesDAO.getId(cliente);
                }
                
                SimpleDateFormat dateFormatt = new SimpleDateFormat("hh:mm:ss");
                String horaActual = dateFormatt.format(new Date());
                /******/////////////////////////////////////////////////////////////////////////////////////////////
                Cita cita = new Cita(fecha,hora,idEstilista,servicio,idCliente,horaActual);
                System.out.println(cita.getFecha());
                System.out.println(cita.getHora());
                System.out.println(cita.getIdEstilista());
                System.out.println(cita.getIdServicio());
                System.out.println(cita.getIdCliente());
                
                if(!boxHora.getSelectedItem().equals("No disponible") && CitaDAO.insertar(cita)){
                        JOptionPane.showMessageDialog(ventana," Cita guardada !","Mensaje",JOptionPane.INFORMATION_MESSAGE);
                    try {
                        //System.out.println("ventana null? " + (ventana == null));
                        //ventana.dispose();
                        //ventana.setVisible(false);
                        MenuPrincipalIGU regresar = new MenuPrincipalIGU();

                    } catch (IOException ex) {
                        Logger.getLogger(NuevaCitaIGU.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                }else{
                        JOptionPane.showMessageDialog(ventana," Error al agendar cita ","Mensaje",JOptionPane.ERROR_MESSAGE);
                }
                /*******/////////////////////////////////////////////////////////////////////////////////////////////
            }
        });

    }
       
    
    public void addBarraHtas() {
        
		barraHtas = new BarraHtasIGU(this);
		vertical.add(barraHtas);
	}
    
    public void limpiar(){
        
                txClienteN.setText("");
                txClienteA.setText("");
                txClienteM.setText("");
                txCelular.setText("");
    }
}
