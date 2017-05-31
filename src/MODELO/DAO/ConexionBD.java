package MODELO.DAO;

import java.sql.*;

import javax.swing.JOptionPane;

public class ConexionBD {

	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL_JDBC = "jdbc:mysql://localhost/citas";
	private static Connection conexion = null;	

	private ConexionBD() {				
	
		try {
			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL_JDBC, "root","divaslife2010");
			conexion.setAutoCommit(false);
			
		}catch (ClassNotFoundException e) {
					
        	JOptionPane.showMessageDialog(null, e.getMessage(), 
        			"No se encontro el controlador", JOptionPane.ERROR_MESSAGE);
         	System.exit(1);			
		}catch (SQLException e){						
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"No se pudo conectar a la Base de Datos", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}		
	}

	public static Connection getConexion() {
		if (conexion == null) {
			new ConexionBD();
		}
		return conexion;
	}				

	public static void cerrarConexion() {

		try {
			if (conexion != null) {
				conexion.close();
				conexion = null;
			}
		}catch (SQLException e){	
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"No se pudo cerrar la Base de Datos", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}