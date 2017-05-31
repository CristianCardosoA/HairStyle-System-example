package MODELO.DAO;
import MODELO.DAO.ConexionBD;
import MODELO.DTO.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ServiciosDAO {

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
		
	public static String [] getServicios(){

		String servicios [] = null;
		int totalTuplas = 0;
		String query;
				
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        query = "SELECT Count(*) FROM servicios ;";	
	        tuplas = sentencia.executeQuery(query);
		        
	        tuplas.next();
	        totalTuplas = tuplas.getInt(1);

	        servicios = new String[totalTuplas];		        			

	        query = "SELECT * FROM servicios" + " ORDER BY id";
		    tuplas = sentencia.executeQuery(query);
		            
		    int pos = 0;
                    Integer dur=0;
		    while (tuplas.next()) {
		       	servicios[pos]= tuplas.getString("id");		       
                        dur= tuplas.getInt("duracion");
                        servicios[pos]=dur.toString();
                        servicios[pos]= tuplas.getString("nombre");
		        pos++;
		    }
								
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer los servicios " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return servicios;		
	}
	
	
}

