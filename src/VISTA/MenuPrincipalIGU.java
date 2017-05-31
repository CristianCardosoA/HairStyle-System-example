package VISTA;
import CONTROL.CustomRenderer;
import MODELO.DAO.CitaDAO;
import MODELO.DAO.EstilistaDAO;
import MODELO.DAO.ServiciosDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @Autor: Cristianloki
 * @Compañia: CROSPOL MEXICO
 * @Fecha: Jun - 2015
 */

public class MenuPrincipalIGU extends JFrame{
    
    private static MenuPrincipalIGU ventana;
    private static int estilista;
    private static int hora;
    private static Object [][] horas;
    private JLabel lbHora;
    private final Box vertical = Box.createVerticalBox();
    private JTable tbCitas;
    private BarraHtasIGU barraHtas;

    private static Date date = new Date();
    JLabel lbDia, lbCliente, lbServicio, lbHoraDescripcion, lbEstilista ;
    
    public static Date getDate() {
        return date;
    }
    
    public MenuPrincipalIGU() throws IOException {
        
        setTitle("*** CITAS DIVAS LIFE ***");
        setUndecorated(true); //FULL SCREEN
        vertical.add(Box.createVerticalStrut(3));
        Dimension dim = this.getToolkit().getScreenSize();
        Rectangle rec = this.getBounds();
        setSize(dim.width,dim.height);
        this.requestFocus();
	setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addPanelSuperior();
        addPanelTabla();
        addBarraHtas();
        add(vertical);
    }

        public static int getEstilista() {
        return estilista;
    }

    public static void setEstilista(int estilista) {
        MenuPrincipalIGU.estilista = estilista;
    }

    public static int getHora() {
        return hora;
    }

    public static void setHora(int hora) {
        MenuPrincipalIGU.hora = hora;
    }

    public static Object[][] getHoras() {
        return horas;
    }

    public static void setHoras(Object[][] horas) {
        MenuPrincipalIGU.horas = horas;
    }
        
    
    
    private void addPanelSuperior() {
        JPanel pnSuperior = new JPanel();
        JPanel pnIzquierdo = new JPanel();
        JPanel pnDerecho = new JPanel();
        JButton btnMasDia = new JButton();
        JButton btnMenosDia = new JButton();
        btnMasDia.setBackground(Color.white);
        btnMenosDia.setBackground(Color.white);
        btnMenosDia.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/indicator.png")));
        btnMasDia.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/arrow-right.png")));
        JLabel lbTitulo = new JLabel("  DIVAS LIFE  ");
        lbDia = new JLabel();
        lbDia.setForeground(Color.BLACK);
        lbDia.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 22));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM-d-yyyy");
        lbDia.setText(new String(" " + dateFormat.format(date) + " ").toUpperCase());
        lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 40));
        Color customColor = new Color(219,0,25);
        lbTitulo.setForeground(customColor);
        lbTitulo.setHorizontalAlignment(JLabel.LEFT);
        pnIzquierdo.add(lbTitulo);
        pnIzquierdo.setBorder(new EmptyBorder(10, 10, 10, 20));
        pnIzquierdo.setBackground(Color.white);
        pnDerecho.add(btnMenosDia);
        pnDerecho.add(lbDia);
        pnDerecho.add(btnMasDia);
        pnDerecho.add(new Reloj().regresaReloj());
        JLabel lbMexico = new JLabel();
        lbMexico.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/banderaM.png")));
        pnDerecho.add(lbMexico);
        pnDerecho.setBackground(Color.white);
        pnSuperior.setBackground(Color.white);
        pnSuperior.add(pnIzquierdo);
        pnSuperior.add(pnDerecho);
        vertical.add(pnSuperior);
        
        btnMenosDia.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                date = restarFechasDias(date,1);
                setTabla(date);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM-d-yyyy");
                lbDia.setText(new String( dateFormat.format(date)).toUpperCase());
            }
        });
        
        btnMasDia.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                date = sumarFechasDias(date,1);
                setTabla(date);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM-d-yyyy");
                lbDia.setText(new String(dateFormat.format(date)).toUpperCase());
            }
        });    
    }
   
    public void addPanelTabla() throws IOException {

		tbCitas = new JTable();
		JPanel pnLista = new JPanel();
                pnLista.setLayout(new BoxLayout(pnLista,BoxLayout.Y_AXIS));
                Color customColor = new Color(242,158,62);
                Border borde;
                borde = BorderFactory.createLineBorder(customColor,0);
		pnLista.setBorder(new TitledBorder(borde, "SISTEMA DE CITAS - DIVAS LIFE S.A. DE C.V.", 
						TitledBorder.CENTER, TitledBorder.TOP,new Font("Arial", Font.PLAIN, 25), Color.darkGray.darker()));
                
		JScrollPane scrollTabla = new JScrollPane(tbCitas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);                
                scrollTabla.setWheelScrollingEnabled(true);
                JScrollBar barra = scrollTabla.getHorizontalScrollBar();
                barra.setBackground(Color.gray);
                barra.setUI(new MyScrollbarUI());
                barra.setBlockIncrement(400);
                barra.setPreferredSize(new Dimension(30,30));
                pnLista.add(scrollTabla);
                pnLista.add(barra);
                tbCitas.getTableHeader().setFont(new Font("Arial",Font.PLAIN, 20));
                tbCitas.getTableHeader().setForeground(Color.darkGray.brighter());
		setTabla(date);
		vertical.add(pnLista);
	}
    
  
    public static MenuPrincipalIGU getVentana() throws IOException{
		if(ventana == null){
			ventana = new MenuPrincipalIGU();
		} 
		return ventana;
	}
      
    public void setTabla(final Date date) {
		
                String horas [] = {"8:00 a.m.","8:30 a.m.","9:00 a.m.","9:30 a.m.","10:00 a.m.",
                    "10:30 a.m.","11:00 a.m.", "11:30 a.m.","12:00 p.m.","12:30 p.m.","1:00 p.m."
                ,"1:30 p.m.","2:00 p.m.","2:30 p.m.","3:00 p.m.","3:30 p.m.","4:00 p.m.","4:30 p.m."
                ,"5:00 p.m.","5:30 p.m.","6:00 p.m.","6:30 p.m.","7:00 p.m.","7:30 p.m.","8:00 p.m."
        };
            final DefaultTableModel modeloTabla = new DefaultTableModel(){
			
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {  //tabla no editable
				return false;
			}
		};
                
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Object datos [][] = CitaDAO.getCitas(dateFormat.format(date));
                //System.out.println(datos[0][7]);
                
                //****************************************
		modeloTabla.setDataVector(datos,horas);
                //****************************************
        
		tbCitas.setModel(modeloTabla);
                
                //////////////Insertar datos en ocupados //////////////////
                String Contenido;
                Integer [] duracion;
                String [] servicios;
                String [] cliente;
                for(int i=0; i<25; i++){
                    if(datos[0][i].equals("Ocupado")){
                        Contenido = "<html>";
                        hora = i;
                        estilista = 0;
                        servicios=CitaDAO.getServicioCita(hora, estilista, dateFormat.format(date));
                        //System.out.println(servicios[0]);
                        //System.out.println(servicios[1]);    
                        duracion =  CitaDAO.separarNumeros(servicios [1].length(), servicios[1]);
                        cliente=CitaDAO.getClienteCita(hora, estilista, dateFormat.format(date));
                        Contenido +=  cliente [0] + "<br>"; 
                        if(cliente[1]!=null){
                            Contenido +=  cliente [1] +"<br>";
                        }
                        Contenido +=  CitaDAO.getServicioCita(hora, estilista, dateFormat.format(date))[0]+ "<br>";
                        Contenido +=  "<br> <font size=\"4\"> <center> Axel</center> </font>   </html>";
                        for(int j=0; j<servicios[1].length();j++){
                            //System.out.println(duracion[j]);
                            tbCitas.setValueAt(Contenido, 0, i+duracion[j]-1);
                            datos[0][i+duracion[j]-1]="CitaLarga";
                        }
                            
                        
                        System.out.println(Contenido);
                        System.out.println(datos[1][i]);
                    }
                    if(datos[1][i].equals("Ocupado")){
                        Contenido = "<html>";
                        hora = i;
                        estilista = 1;
                        servicios=CitaDAO.getServicioCita(hora, estilista, dateFormat.format(date));
                        //System.out.println("Servicios");
                        //System.out.println(servicios[0]);
                        //System.out.println(servicios[1]);    
                        duracion =  CitaDAO.separarNumeros(servicios [1].length(), servicios[1]);
                        cliente = CitaDAO.getClienteCita(hora, estilista, dateFormat.format(date));
                        Contenido +=  cliente [0] + "<br>"; 
                        if(cliente[1]!=null){
                            Contenido +=  cliente [1] +"<br>";
                        }
                        Contenido +=  CitaDAO.getServicioCita(hora, estilista, dateFormat.format(date))[0]+ "<br>";
                        Contenido +=  "<br><font size=\"4\"> <center> Elvis </center> </font> </html>";
                        for(int j=0; j<servicios[1].length();j++){
                            tbCitas.setValueAt(Contenido, 1, i+duracion[j]-1);
                            datos[1][i+duracion[j]-1]="CitaLarga";
                            System.out.println(Contenido);
                            
                        }
                        MenuPrincipalIGU.setHoras(datos);
                        //System.out.println(Contenido);
                    }
                }
                
                
                ///////////////////////////////////////////////////////////
                
                tbCitas.setBackground(Color.GREEN);
		//tbCitas.changeSelection(0, 0, false, false);
		tbCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                for(int i = 0; i < tbCitas.getColumnCount(); i++){
                   
                    tbCitas.getColumnModel().getColumn(i).setPreferredWidth(180);
                }
                tbCitas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tbCitas.setRowHeight(200);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                for(int x=0;x<tbCitas.getColumnCount();x++){
                    //tbCitas.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
                }
                tbCitas.setDefaultRenderer(Object.class,new CustomRenderer());

                tbCitas.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent evnt) {
                    /*if (evnt.getClickCount() == 2) {
                        int idEstilista = tbCitas.getSelectedRow();
                        int hora = tbCitas.getSelectedColumn();
                        if(tbCitas.getValueAt(idEstilista, hora).equals("Disponible")){
                            dispose();
                            NuevaCitaIGU nuevaCita = new NuevaCitaIGU(idEstilista,hora, date);
                        }else{
                            //Informacion detalles
                            JOptionPane.showMessageDialog(ventana,"Seleccione una cita v\u00e1lida","ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    }*/
                    
                    if (evnt.getClickCount() == 1) {
                        int estilista = tbCitas.getSelectedRow();
                        int hora = tbCitas.getSelectedColumn();
                        ventana.setEstilista(estilista);
                        ventana.setHora(hora);
                        if(!tbCitas.getValueAt(estilista, hora).equals("Disponible")){
                            barraHtas.getBtBorrar().setEnabled(true);
                            barraHtas.getBtEditar().setEnabled(true);
                            barraHtas.getBtNuevo().setEnabled(false);
                            /*lbCliente.setText(CitaDAO.getClienteCita(hora, estilista, dateFormat.format(date)));
                            lbServicio.setText(CitaDAO.getServicioCita(hora, estilista, dateFormat.format(date)));
                            lbEstilista.setText(EstilistaDAO.getEstilistas(estilista));
                            lbHoraDescripcion.setText(tbCitas.getColumnName(hora));*/
                            
                        }else{
                            barraHtas.getBtEditar().setEnabled(false);
                            barraHtas.getBtBorrar().setEnabled(false);
                            barraHtas.getBtNuevo().setEnabled(true);
                            /*lbHoraDescripcion.setText("");
                            lbEstilista.setText("");
                            lbServicio.setText("");
                            lbCliente.setText("");*/
                        }
                    }
                    
                }
           });
                        
	}
    
    public JTable getTbCitas() {
        return tbCitas;
    }

    public void setTbCitas(JTable tbCitas) {
        this.tbCitas = tbCitas;
    }
            
    public void addBarraHtas() {
                Font letra = new Font("Arial", Font.PLAIN, 25);
		barraHtas = new BarraHtasIGU(this);
                JPanel pnDetalle = new JPanel();/*
                pnDetalle.setLayout(new GridLayout(4,2));
                pnDetalle.add(new JLabel("Cliente : "));
                lbCliente = new JLabel();
                pnDetalle.add(lbCliente);
                pnDetalle.add(new JLabel("Hora: "));
                lbHoraDescripcion = new JLabel();
                pnDetalle.add(lbHoraDescripcion);
                pnDetalle.add(new JLabel("Estilista: "));
                lbEstilista = new JLabel();
                pnDetalle.add(lbEstilista);
                pnDetalle.add(new JLabel("Servicio: "));
                lbServicio = new JLabel();
                pnDetalle.add(lbServicio);*/
                barraHtas.add(pnDetalle);
		vertical.add(barraHtas);
	}
	
    public static synchronized Date restarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new Date(cal.getTimeInMillis());
    }
      
    public static Date sumarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new Date(cal.getTimeInMillis());
    }
    
    


}
