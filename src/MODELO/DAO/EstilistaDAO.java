package MODELO.DAO;
import MODELO.DAO.ConexionBD;
import MODELO.DTO.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class EstilistaDAO {

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
		
	public static String [] getEstilistas(){

		String estilistas [] = null;
		int totalTuplas = 0;
		String query;
				
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        query = "SELECT Count(*) FROM estilista ;";	
	        tuplas = sentencia.executeQuery(query);
		        
	        tuplas.next();
	        totalTuplas = tuplas.getInt(1);

	        estilistas = new String[totalTuplas];		        			

	        query = "SELECT * FROM estilista" + " ORDER BY id";
		    tuplas = sentencia.executeQuery(query);
		            
		    int pos = 0;
		    while (tuplas.next()) {
		       	estilistas[pos]= tuplas.getString("id");		       
		       	estilistas[pos]= tuplas.getString("nombre");
		        pos++;
		    }
								
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer estilistas " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return estilistas;		
	}
	
       public static String getEstilistas(int posicion){

		String estilistas [] = null;
		String query;
				
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        estilistas = new String[1];		        			

	        query = "SELECT nombre FROM estilista" + " where id =" + (posicion+1);
		    tuplas = sentencia.executeQuery(query);
		            
		    int pos = 0;
                    tuplas.next();
		    estilistas[pos]= tuplas.getString("nombre");
								
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer estilistas " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return estilistas[0];		
	}
	
	
}

